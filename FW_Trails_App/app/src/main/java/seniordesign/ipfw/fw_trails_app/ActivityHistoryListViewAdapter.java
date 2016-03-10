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
