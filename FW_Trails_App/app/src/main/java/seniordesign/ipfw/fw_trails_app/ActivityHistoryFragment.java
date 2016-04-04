package seniordesign.ipfw.fw_trails_app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();

      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      View view = inflater.inflate(R.layout.fragment_activity_history, container, false);

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
                  JSONObject jsonResponse = new JSONObject(new String(response));

                  // Convert the single JSON Object to an array
                  JSONArray activityHistory = jsonResponse.getJSONArray("GetActivitiesForUserResult");

                  // Loop through all the returned activities and create models based on them
                  for (int i = 0; i < activityHistory.length(); i++) {

                     // Get the single JSON Object aka 1 activity history model
                     activity = activityHistory.getJSONObject(i);
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
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
               // called when response HTTP status is "4XX" (eg. 401, 403, 404)
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
         adapter = new ActivityHistoryListViewAdapter(getContext(),R.layout.activity_history_row,R.id.durationText,theItems);

         listView.setAdapter(adapter);
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
