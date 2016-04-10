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


public class AchievementsFragment extends Fragment {

   private final String fragmentTitle = "Achievements";
   ArrayList<AchievementModel> items;
   private View view;
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_achievements, container, false);

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
