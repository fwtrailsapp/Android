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

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      View view = inflater.inflate(R.layout.fragment_achievements, container, false);

      ListView listView = (ListView) view.findViewById(R.id.achievements_listView);


      String[] items=getResources().getStringArray(R.array.achievement_test_list_items);
      AchievementsListViewAdapter adapter = new AchievementsListViewAdapter(getContext(),R.layout.achievement_row,R.id.dateEarnedText,items);

      // Bind data to the ListView
      listView.setAdapter(adapter);

      return view; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }
}
