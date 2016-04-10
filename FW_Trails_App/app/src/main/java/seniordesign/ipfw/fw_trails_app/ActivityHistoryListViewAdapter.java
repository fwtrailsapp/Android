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
import android.widget.ArrayAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jaron on 2/13/2016.
 */

public class ActivityHistoryListViewAdapter extends ArrayAdapter<ActivityHistoryModel> {
   int groupid;
   ArrayList<ActivityHistoryModel> item_list;
   private final DecimalFormat distanceFormatter = new DecimalFormat("#.#");

   Context context;
   public ActivityHistoryListViewAdapter(Context context, int vg, int id, ArrayList<ActivityHistoryModel> item_list){
      super(context,vg, id, item_list);
      this.context=context;
      groupid=vg;
      this.item_list=item_list;

   }

   // Hold views of the ListView to improve its scrolling performance
   static class ViewHolder {
      public ImageView activityImage;
      public TextView caloriesText;
      public TextView durationText;
      public TextView distanceText;
      public TextView dateText;

   }

   public View getView(int position, View convertView, ViewGroup parent) {

      View rowView = convertView;
      // Inflate the activity_history_row.xml file if convertView is null
      if(rowView==null){
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         rowView= inflater.inflate(groupid, parent, false);
         ViewHolder viewHolder = new ViewHolder();
         viewHolder.caloriesText = (TextView) rowView.findViewById(R.id.caloriesText);
         viewHolder.durationText = (TextView) rowView.findViewById(R.id.durationText);
         viewHolder.dateText = (TextView) rowView.findViewById(R.id.dateText);
         viewHolder.distanceText = (TextView) rowView.findViewById(R.id.distanceText);
         viewHolder.activityImage = (ImageView) rowView.findViewById(R.id.activityHistoryImage);
         rowView.setTag(viewHolder);

      }

      // Set text to each TextView of ListView item
      ActivityHistoryModel items = item_list.get(position);
      String distanceText = distanceFormatter.format(items.getMileage())+" mi";
      ViewHolder holder = (ViewHolder) rowView.getTag();

      holder.durationText.setText(items.getDuration());
      holder.distanceText.setText(distanceText);
      holder.caloriesText.setText(String.valueOf(items.getCalsBurned()));
      holder.dateText.setText(items.getDate());
      holder.activityImage.setImageResource(items.getExerciseTypeIconID());

      return rowView;
   }

}
