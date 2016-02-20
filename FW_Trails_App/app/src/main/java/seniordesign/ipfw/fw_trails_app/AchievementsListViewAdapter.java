package seniordesign.ipfw.fw_trails_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jaron on 2/13/2016.
 */

public class AchievementsListViewAdapter extends ArrayAdapter<String> {
   int groupid;
   String[] item_list;
   ArrayList<String> desc;
   Context context;
   public AchievementsListViewAdapter(Context context, int vg, int id, String[] item_list){
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
      String[] items = item_list[position].split("__");
      ViewHolder holder = (ViewHolder) rowView.getTag();
      holder.dateEarnedText.setText(items[0]);
      holder.nameText.setText(items[1]);
      holder.descriptionText.setText(items[2]);
      return rowView;
   }

}
