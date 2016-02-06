package seniordesign.ipfw.fw_trails_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

   private final String achievementsTitle = "Achievements";

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      RelativeLayout loadedRelativeLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_achievements, container, false);
      // Of course you will want to faActivity and llLayout in the class and not this method to access them in the rest of
      // the class, just initialize them here

      // Content of previous onCreate() here
      // ...
      // Don't use this method, it's handled by inflater.inflate() above :
      // setContentView(R.layout.activity_layout);

      // The FragmentActivity doesn't contain the layout directly so we must use our instance of     LinearLayout :
      // loadedRelativeLayout.findViewById(R.id.someGuiElement);
      // Instead of :
      // findViewById(R.id.someGuiElement);
      return loadedRelativeLayout; // We must return the loaded Layout
   }

   public String getTitle(){
      return achievementsTitle;
   }
}
