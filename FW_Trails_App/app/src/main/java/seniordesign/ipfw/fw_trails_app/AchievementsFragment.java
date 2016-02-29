package seniordesign.ipfw.fw_trails_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AchievementsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AchievementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AchievementsFragment extends Fragment {

   private final String fragmentTitle = "Achievements";
   ArrayList<AchievementModel> items;
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      View view = inflater.inflate(R.layout.fragment_achievements, container, false);

      ListView listView = (ListView) view.findViewById(R.id.achievements_listView);


      //TODO: API GET Call goes here to grab achievements from server.
      loadTestAchievements();

      AchievementsListViewAdapter adapter = new AchievementsListViewAdapter(getContext(),R.layout.achievement_row,R.id.dateEarnedText,items);

      // Bind data to the ListView
      listView.setAdapter(adapter);

      return view; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }

   private void loadTestAchievements(){
      if(items == null) {
         items = new ArrayList<>(2);
         final String achievName = "Daily Dose";
         final String achievDate = "02/22/2016";
         final String achievDesc = "Bike for 24 hours";
         final String notEarnedAchievName = "Joker";
         final String notEarnedAchievDesc = "Kill the Batman";

         AchievementModel achievEarned = new AchievementModel(achievName, achievDate, achievDesc);
         AchievementModel notEarnedAchiev = new AchievementModel(notEarnedAchievName, null,
                 notEarnedAchievDesc);
         items.add(achievEarned);
         items.add(notEarnedAchiev);
      }
   }
}
