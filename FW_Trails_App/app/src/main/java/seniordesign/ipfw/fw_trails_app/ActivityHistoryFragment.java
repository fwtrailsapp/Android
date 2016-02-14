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


public class ActivityHistoryFragment extends Fragment {
   private final String fragmentTitle = "Activity History";

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      View view = inflater.inflate(R.layout.fragment_activity_history, container, false);

      ListView listView = (ListView) view.findViewById(R.id.activity_history_listView);

      View headerView = inflater.inflate(R.layout.activity_history_header,listView,false);

      // Add header view to liist view
      listView.addHeaderView(headerView);

      String[] items=getResources().getStringArray(R.array.test_list_items);
      ListViewAdapter adapter=new ListViewAdapter(getContext(),R.layout.activity_history_row,R.id.durationText,items);
      // Bind data to the ListView
      listView.setAdapter(adapter);
      return view; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }
}
