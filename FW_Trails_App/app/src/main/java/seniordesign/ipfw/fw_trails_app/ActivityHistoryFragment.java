package seniordesign.ipfw.fw_trails_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class ActivityHistoryFragment extends Fragment {
   private final String fragmentTitle = "Activity History";
   ArrayList<ActivityHistoryModel> items;
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      View view = inflater.inflate(R.layout.fragment_activity_history, container, false);

      ListView listView = (ListView) view.findViewById(R.id.activity_history_listView);

      // TODO:REST GET request for past activities goes here
      // use test array for now of dummy ActivityHistoryModel resources.
      loadActivityHistoryModels();
      ActivityHistoryListViewAdapter adapter = new ActivityHistoryListViewAdapter(getContext(),R.layout.activity_history_row,R.id.durationText,items);

      // Bind data to the ListView
      listView.setAdapter(adapter);
      return view; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }

   // Load fake data that represents the API GET Response data.
   private void loadActivityHistoryModels(){
      if(items == null)
      {
         String durations[] = {"00:12:34", "00:04:38", "01:04:53"};
         items = new ArrayList<ActivityHistoryModel>(3);
         String nowAsISO;
         Duration theDuration;
         double theMileage;
         int theCalsBurned;
         ExerciseType theExerciseType;
         for(int i = 0; i < 3; i++)
         { TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df.setTimeZone(tz);
            nowAsISO = df.format(new Date());

            //Set up the duration
            theDuration = new Duration(durations[i]);

            //Set up the mileage
            theMileage = 11.5+i;

            //Set up the calories burned
            theCalsBurned = 884+i;

            // Alternate between different activities
            if(i%3 == 0) {
               theExerciseType = new WalkExerciseType(theDuration);
            }
            else if(i%3 == 1)
            {
               theExerciseType = new RunExerciseType(theDuration);
            }
            else
            {
               theExerciseType = new BikeExerciseType(theDuration);
            }

            items.add(new ActivityHistoryModel(nowAsISO,theDuration,
                    theMileage, theCalsBurned, theExerciseType));
         }
      }
   }
}
