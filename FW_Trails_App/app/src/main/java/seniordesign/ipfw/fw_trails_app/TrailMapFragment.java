package seniordesign.ipfw.fw_trails_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;


public class TrailMapFragment extends Fragment {

   private final String fragmentTitle = "Trail Map";
   // These constants map to the string array trailMapViewArray
   private final int FULL = 0;
   private final int FULL_MAP_AREAS = 1;
   private final int NORTH = 2;
   private final int SOUTH = 3;
   private final int EAST  = 4;
   private final int WEST  = 5;
   private Spinner trailMapSpinner;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      final RelativeLayout loadedRelativeLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_trail_map, container, false);

      trailMapSpinner = (Spinner) loadedRelativeLayout.findViewById(R.id.trailMapSpinner);
      trailMapSpinner.setSelection(FULL);
      trailMapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

         @Override
         public void onItemSelected(AdapterView<?> arg0, View view,
                                    int arg2, long arg3) {

            setMapImage(trailMapSpinner.getSelectedItemPosition(), loadedRelativeLayout);
         }

         @Override
         public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

         }

      });
      //setMapImage(FULL_OVERLAY);
      return loadedRelativeLayout; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }

   // Sets the image view's source to the drawable per the image parameter
   private void setMapImage(int image, View view){

      ImageView mapImage = (ImageView) view.findViewById(R.id.trailMapImageView);
      //Drawable drawable = ResourcesCompat.getDrawable(getResources(), page.getImageId(), null);


      switch(image){
         case FULL:
            mapImage.setImageResource(R.drawable.full);
            break;
         case FULL_MAP_AREAS:
            mapImage.setImageResource(R.drawable.full_overlay);
            break;
         case NORTH:
               //mapImage.setImageDrawable();
            mapImage.setImageResource(R.drawable.north);
            break;
         case SOUTH:
            mapImage.setImageResource(R.drawable.south);
            break;

         case EAST:
            mapImage.setImageResource(R.drawable.east);
            break;

         case WEST:
            mapImage.setImageResource(R.drawable.west);
            break;

         default:
            mapImage.setImageResource(R.drawable.full_overlay);
            break;

      }

   }
}
