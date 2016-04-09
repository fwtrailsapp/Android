package seniordesign.ipfw.fw_trails_app;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;

public class AccountStatisticsFragment extends Fragment {

   private final String fragmentTitle = "Your Statistics";
   private final String distanceUnit = " miles";
   private final int BIKE  = 0;
   private final int RUN  = 1;
   private final int WALK = 2;
   private AccountStatisticsModel statModel;

   private TextView overallAccountActive;
   private TextView overallDuration;
   private TextView overallDistance;
   private TextView overallCalories;
   private TextView overallAchievements;
   private TextView activitySpecificDuration;
   private TextView activitySpecificDistance;
   private TextView activitySpecificCalories;
   private Spinner specificActivitySpinner;
   private RelativeLayout loadedRelativeLayout;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity    faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      loadedRelativeLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_account_statistics, container, false);

      // Find the textviews likely to change during the lifetime of the fragment once so we don't
      // have to keep finding them again when they need to be changed.
      assignTextViewControls(loadedRelativeLayout);



      AccountStatisticsController statTask = new AccountStatisticsController();
      statTask.execute();


      return loadedRelativeLayout; // We must return the loaded Layout
   }

   // Sets the text view controls to their appropriate field.
   private void assignTextViewControls(RelativeLayout relativeLayout) {
      overallAccountActive     = (TextView) relativeLayout.findViewById(R.id.accountActiveAmountTextView);
      overallDuration          = (TextView) relativeLayout.findViewById(R.id.overallTotalTimeAmountTextView);
      overallDistance          = (TextView) relativeLayout.findViewById(R.id.overallTotalDistanceAmountTextView);
      overallCalories          = (TextView) relativeLayout.findViewById(R.id.totalCaloriesExpendedAmountTextView);
      overallAchievements      = (TextView) relativeLayout.findViewById(R.id.overallNumberOfAchievementsAmountTextView);
      activitySpecificDuration = (TextView) relativeLayout.findViewById(R.id.activitySpecificTotalTimeAmountTextView);
      activitySpecificDistance = (TextView) relativeLayout.findViewById(R.id.activitySpecificTotalDistanceAmountTextView);
      activitySpecificCalories = (TextView) relativeLayout.findViewById(R.id.activitySpecificCaloriesExpendedAmountTextView);
   }

   // Formats the numbers to either zero decimal places if it has none, or up to 1 dcecimal place if
   // it has decimals
   private static String format(Number n) {
      NumberFormat format = DecimalFormat.getInstance();
      format.setRoundingMode(RoundingMode.FLOOR);
      format.setMinimumFractionDigits(0);
      format.setMaximumFractionDigits(1);
      return format.format(n);
   }

   // Listen for when the user selects a new activity stat to view.
   private void loadSpinnerListener(){
      specificActivitySpinner = (Spinner) loadedRelativeLayout.findViewById(R.id.activity_Spinner);

      specificActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            // Don't try to operate on a null model.
            if (statModel != null) {

               // Position in the spinner: Biking (0), Running (1), Walking (2)
               switch (position) {

                  case BIKE:
                     setActivitySpecificStats(statModel.getBikeDuration(), statModel.getBikeDistance(),
                             statModel.getBikeCalories());
                     break;

                  case RUN:
                     setActivitySpecificStats(statModel.getRunDuration(), statModel.getRunDistance(),
                             statModel.getRunCalories());
                     break;

                  case WALK:
                     setActivitySpecificStats(statModel.getWalkDuration(), statModel.getWalkDistance(),
                             statModel.getWalkCalories());
                     break;

                  default:
                     setActivitySpecificStats("00:00:00", 0.0, 0);
                     break;
               }
            } else {
               setActivitySpecificStats("00:00:00", 0.0, 0);
            }


         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
      });
   }

   public String getTitle(){
      return fragmentTitle;
   }

   // Displays a dialog describing the error passed in.
   private void displayError(String errorText) {

      AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
      alertDialog.setTitle(fragmentTitle+ " Error");

      // Modal settings are set
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

   // Sets the activity specific fields to the values passed in.
   // Formats: HH:mm:ss, double ##.# mi, int kcal
   // Should be called when a user changes the spinner item
   private void setActivitySpecificStats(String duration, double distance, int calories){
      String distanceString = format(distance) + distanceUnit;
      String caloriesString = format(calories);

      activitySpecificDuration.setText(duration);
      activitySpecificCalories.setText(caloriesString);
      activitySpecificDistance.setText(distanceString);
   }

   // Sets the overall account statistics. Called once in onCreateView
   private void setOverallAccountStatistics(String accountActive, int calories, double distance,
                                            int achievements, String duration){
      String distanceString = format(distance) + distanceUnit;
      String achievementsString = format(achievements);
      String caloriesString = format(calories);

      overallAccountActive.setText(accountActive);
      overallCalories.setText(caloriesString);
      overallDistance.setText(distanceString);
      overallDuration.setText(duration);
      overallAchievements.setText(achievementsString);
   }

   // This class handles the connection between the server and the mobile app in regards to the
   // account statistics. It grabs the overall statistics for the user and the per activity type
   // statistics for the user.
   private class AccountStatisticsController extends AsyncTask<Void, Void, Void> {

      // Constants used to parse the JSON Array response we get back.
      private final int OVERALL = 0;
      private final int BIKE = 1;
      private final int RUN = 2;
      private final int WALK = 3;
      private final int INVALID_AUTH_TOKEN_STATUS_CODE = 419;
      private final String TOTAL_DURATION = "total_duration";
      private final String TOTAL_CALORIES = "total_calories";
      private final String TOTAL_DISTANCE = "total_distance";


      // Attempts to call the server's Statistics/user to get all the generic stats of the account
      @Override
      protected Void doInBackground(Void... params) {

         HttpClientUtil.get(HttpClientUtil.BASE_URL_STATISTICS, null, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

            @Override
            public void onStart() {

            }

            // When we succeed at getting a response back
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

               try {

                  // Convert the single JSON Object to an array
                  JSONArray allStatistics = new JSONArray(new String(response));

                  // Create the statistics model to hold the data from the server.
                  loadStatistics(allStatistics);

               } catch (Exception ex) {
                  Log.i("Networking Exception", ex.getMessage());
               }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
               // called when response HTTP status is "4XX" (eg. 401, 403, 404)

               // If we get 419 from the server, we need to reobtain the auth token.
               if (checkForInvalidAuthToken(statusCode)) {
                  HttpClientUtil.getInstance().reobtainAuthToken();
               } else {
                  getActivity().runOnUiThread(new Runnable() {

                     public void run() {
                        // Display an error loading account stats.
                        displayError(getString(R.string.accountStatisticsLoadError));
                     }
                  });
               }
            }

         });

         return null;
      }

      // theItems parameter is the return value of the doInBackground method. This contains the
      // list of objects to be displayed in the ListView
      @Override
      protected void onPostExecute(Void param) {

         // Initiate the listener for the spinner object
         loadSpinnerListener();

         // only set the controls if the data is available.
         if(statModel != null){
            // Set overall and activity Specific text
            setOverallAccountStatistics(statModel.getAccountActive(), statModel.getOverallCalories(),
                    statModel.getOverallDistance(), statModel.getAchievementsEarned(),
                    statModel.getOverallDuration());
            setActivitySpecificStats(statModel.getBikeDuration(), statModel.getBikeDistance(),
                    statModel.getBikeCalories());
         }


      }

      // Checks for a status code 419 which indicates that the auth token is no longer valid and
      // needs to be reobtained.
      private boolean checkForInvalidAuthToken(int statusCode){
         return statusCode == INVALID_AUTH_TOKEN_STATUS_CODE;
      }

      // Loads the statistics into the model from the json object passed in
      private void loadStatistics(JSONArray allStats){
         Duration overallDuration, bikeDuration, walkDuration, runDuration;
         double overallDistance, bikeDistance, walkDistance, runDistance;
         int overallCalories, bikeCalories, walkCalories, runCalories;

         // These two parts are not yet implemented server side.
         int achievementsEarned = 0;
         String acctActive = "< 1 year";


         try{
            JSONObject overall = allStats.getJSONObject(OVERALL);
            JSONObject bike = allStats.getJSONObject(BIKE);
            JSONObject run = allStats.getJSONObject(RUN);
            JSONObject walk = allStats.getJSONObject(WALK);

            overallDuration = new Duration(overall.getString(TOTAL_DURATION));
            overallCalories = Integer.valueOf(overall.getString(TOTAL_CALORIES));
            overallDistance = Double.valueOf(overall.getString(TOTAL_DISTANCE));

            bikeDuration = new Duration(bike.getString(TOTAL_DURATION));
            bikeCalories = Integer.valueOf(bike.getString(TOTAL_CALORIES));
            bikeDistance = Double.valueOf(bike.getString(TOTAL_DISTANCE));

            runDuration = new Duration(run.getString(TOTAL_DURATION));
            runCalories = Integer.valueOf(run.getString(TOTAL_CALORIES));
            runDistance = Double.valueOf(run.getString(TOTAL_DISTANCE));

            walkDuration = new Duration(walk.getString(TOTAL_DURATION));
            walkCalories = Integer.valueOf(walk.getString(TOTAL_CALORIES));
            walkDistance = Double.valueOf(walk.getString(TOTAL_DISTANCE));

            statModel = new AccountStatisticsModel(overallDuration,overallDistance,overallCalories,
                    achievementsEarned,bikeDuration,bikeDistance,bikeCalories,runDuration,
                    runDistance,runCalories,walkDuration,walkDistance,walkCalories,acctActive);
         }
         catch(Exception ex){
            Log.i("JSON Exception", ex.getMessage());
         }

      }

   }
}
