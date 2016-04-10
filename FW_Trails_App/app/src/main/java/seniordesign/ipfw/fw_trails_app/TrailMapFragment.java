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


      // Display the image the user selected from the spinner
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
