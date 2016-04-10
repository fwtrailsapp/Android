/**
 Copyright (C) 2016 Jared Perry, Jaron Somers, Warren Barnes, Scott Weidenkopf, and Grant Grimm
 Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 and associated documentation files (the "Software"), to deal in the Software without restriction,
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies\n
 or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package seniordesign.ipfw.fw_trails_app;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;


public class ActivityHistoryFragment extends Fragment {
   private final String fragmentTitle = "Activity History";
   ArrayList<ActivityHistoryModel> items;
   private ListView listView;
   private ActivityHistoryListViewAdapter adapter;
   private View view;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();

      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      view = inflater.inflate(R.layout.fragment_activity_history, container, false);

      listView = (ListView) view.findViewById(R.id.activity_history_listView);

      // TODO:REST GET request for past activities goes here

      items = new ArrayList<ActivityHistoryModel>();

      // Kick off the new thread to load the activity history models from the server.
      ActivityHistoryController task = new ActivityHistoryController();
      task.execute();

      return view; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }

   // Displays a dialog describing the error passed in.
   private void displayError(String errorText) {

      AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
      alertDialog.setTitle(fragmentTitle+ " Error");

      // Modal settings are set, user must click ok before the dialog can be dismissed
      alertDialog.setCancelable(false);
      alertDialog.setMessage(errorText);
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
              new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                 }
              });

      alertDialog.show();
   }

   /*
   The ActivityHistoryController class.

   This class extends the AsyncTask to spawn off a new thread that grabs the activity history results
   from the webserver. The HttpClientUtil class is the class that actually sends off the request using
   an Synchronous Http handler.

   We then parse the returned items in the onSuccess of the AsyncHttpResponseHandler and add the items
   to the items ArrayList in the activity history fragment class.

   The onPostExecute method is what updates the actual ListViewAdapter
    */
   private class ActivityHistoryController extends AsyncTask<Void, Void, ArrayList<ActivityHistoryModel>> {

      @Override
      protected ArrayList<ActivityHistoryModel> doInBackground(Void... params) {

         HttpClientUtil.get(HttpClientUtil.BASE_URL_ACTIVITY, null, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

            @Override
            public void onStart() {

            }

            // For when there is a single JSON Object.
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

               try {

                  JSONObject activity;
                  String exerciseType;
                  Duration theDuration;
                  ExerciseType exercise;

                  // Convert the single response to a JSON Object
                  JSONArray activityHistoryJSON = new JSONArray(new String(response));

                  // Convert the single JSON Object to an array


                  // Loop through all the returned activities and create models based on them
                  for (int i = 0; i < activityHistoryJSON.length(); i++) {

                     // Get the single JSON Object aka 1 activity history model
                     activity = activityHistoryJSON.getJSONObject(i);
                     exerciseType = activity.getString("exercise_type");
                     theDuration = new Duration(activity.getString("duration"));

                     // Determine the exercise type and instantiate it
                     exercise = determineExerciseType(exerciseType, theDuration);

                     // Add to the list of activity history model objects to be displayed
                     items.add(new ActivityHistoryModel(activity.getString("time_started"), exercise.getDuration(),
                             activity.getDouble("mileage"), activity.getInt("calories_burned"), exercise));
                  }

               } catch (Exception ex) {
                  Log.i("Networking Exception", ex.getMessage());
               }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, final byte[] errorResponse, Throwable e) {
               // called when response HTTP status is "4XX" (eg. 401, 403, 404)
               // Run on UI thread due to AsyncTask

               getActivity().runOnUiThread(new Runnable() {

                  public void run() {
                     String errorText = "There was a problem getting your activity history.";
                     displayError(errorText);
                  }
               });
            }

         });

         return items;
      }

      // theItems parameter is the return value of the doInBackground method. This contains the
      // list of objects to be displayed in the ListView
      @Override
      protected void onPostExecute(ArrayList<ActivityHistoryModel> theItems) {
         super.onPostExecute(theItems);

         // Bind data to the custom ListViewAdapter
         if(theItems != null){
            adapter = new ActivityHistoryListViewAdapter(getContext(),R.layout.activity_history_row,R.id.durationText,theItems);

            listView.setAdapter(adapter);
         }
      }

      // Determines the exercise type based on a string received from the server's
      // json exercise_type field
      private ExerciseType determineExerciseType(String type, Duration theDuration) {
         ExerciseType exercise = null;

         try{
            switch (type){
               case "bike":
                  exercise = new BikeExerciseType(theDuration);
                  break;

               case "run":
                  exercise = new RunExerciseType(theDuration);
                  break;

               case "walk":
                  exercise = new WalkExerciseType(theDuration);
                  break;
               default:
                  throw new Exception("Incorrect exercise type received from server");
            }
         }
         catch(Exception ex){
            Log.i("Networking Development", ex.getMessage());
         }
         return exercise;
      }
   }
}
