package seniordesign.ipfw.fw_trails_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class AccountStatisticsFragment extends Fragment {

   private final String fragmentTitle = "Your Statistics";
   private final String distanceUnit = " miles";
   private final DecimalFormat distanceFormatter = new DecimalFormat("#.#");

   private TextView overallAccountActive;
   private TextView overallDuration;
   private TextView overallDistance;
   private TextView overallCalories;
   private TextView overallAchievements;
   private TextView activitySpecificDuration;
   private TextView activitySpecificDistance;
   private TextView activitySpecificCalories;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity    faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      RelativeLayout loadedRelativeLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_account_statistics, container, false);

      // Find the textviews likely to change during the lifetime of the fragment once so we don't
      // have to keep finding them again when they need to be changed.
      assignTextViewControls();

      //TODO: Make API call to return the statistics for this account. (Asynchronously)
         // Somehow know the account username
         // Parse the returned values
         // Create the AccountStatisticsModel to hold the data
         // Call a method to update the fields
      //TODO: Create a listener of some sort to listen for when the user changes the spinner and
      //TODO: reflect the spinner choice's data on the view.

      return loadedRelativeLayout; // We must return the loaded Layout
   }

   // Sets the text view controls to their appropriate field.
   private void assignTextViewControls() {
      View view = getView();
      overallAccountActive     = (TextView) view.findViewById(R.id.accountActiveAmountTextView);
      overallDuration          = (TextView) view.findViewById(R.id.overallTotalTimeAmountTextView);
      overallDistance          = (TextView) view.findViewById(R.id.overallTotalDistanceAmountTextView);
      overallCalories          = (TextView) view.findViewById(R.id.overallNumberOfAchievementsAmountTextView);
      overallAchievements      = (TextView) view.findViewById(R.id.overallNumberOfAchievementsAmountTextView);
      activitySpecificDuration = (TextView) view.findViewById(R.id.activitySpecificTotalTimeAmountTextView);
      activitySpecificDistance = (TextView) view.findViewById(R.id.activitySpecificTotalDistanceAmountTextView);
      activitySpecificCalories = (TextView) view.findViewById(R.id.activitySpecificCaloriesExpendedAmountTextView);
   }

   public String getTitle(){
      return fragmentTitle;
   }

   // Sets the activity specific fields to the values passed in.
   // Formats: HH:mm:ss, double ##.# mi, int kcal
   // Should be called when a user changes the spinner item
   private void setActivitySpecificStats(String duration, double distance, int calories){
      activitySpecificDuration.setText(duration);
      activitySpecificCalories.setText(calories);
      activitySpecificDistance.setText(distanceFormatter.format(distance) + distanceUnit);
   }

   // Sets the overall account statistics. Called once in onCreateView
   private void setOverallAccountStatistics(String accountActive, int calories, double distance,
                                            int achievements, String duration){
      overallAccountActive.setText(accountActive);
      overallCalories.setText(calories);
      overallDistance.setText(distanceFormatter.format(distance));
      overallDuration.setText(duration);
      overallAchievements.setText(achievements);
   }
}
