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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AchievementsListViewAdapter extends ArrayAdapter<AchievementModel> {
   int groupid;
   ArrayList<AchievementModel> item_list;
   Context context;
   public AchievementsListViewAdapter(Context context, int vg, int id,
                                      ArrayList<AchievementModel> item_list){
      super(context,vg, id, item_list);
      this.context=context;
      groupid=vg;
      this.item_list=item_list;

   }
   // Hold views of the ListView to improve its scrolling performance
   static class ViewHolder {
      public TextView dateEarnedText;
      public TextView nameText;
      public TextView descriptionText;

   }

   public View getView(int position, View convertView, ViewGroup parent) {

      View rowView = convertView;
      // Inflate the activity_history_row.xml file if convertView is null
      if(rowView==null){
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         rowView= inflater.inflate(groupid, parent, false);
         ViewHolder viewHolder = new ViewHolder();
         viewHolder.dateEarnedText = (TextView) rowView.findViewById(R.id.dateEarnedText);
         viewHolder.nameText = (TextView) rowView.findViewById(R.id.nameText);
         viewHolder.descriptionText = (TextView) rowView.findViewById(R.id.descriptionText);

         rowView.setTag(viewHolder);

      }

      // Set text to each TextView of ListView item
      AchievementModel item = item_list.get(position);
      ViewHolder holder = (ViewHolder) rowView.getTag();
      holder.dateEarnedText.setText(item.getDate());
      holder.nameText.setText(item.getName());
      holder.descriptionText.setText(item.getDescription());
      return rowView;
   }

}
