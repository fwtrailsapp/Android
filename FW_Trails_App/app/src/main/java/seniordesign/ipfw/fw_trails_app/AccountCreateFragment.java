package seniordesign.ipfw.fw_trails_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountCreateFragment extends Fragment {

   private final String fragmentTitle = "Create Account";
   private Button cancelBtn;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      RelativeLayout loadedRelativeLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_account_create, container, false);



      setCancelButtonListener(loadedRelativeLayout);
      return loadedRelativeLayout; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }


   private void setCancelButtonListener(View view){

      cancelBtn = (Button) view.findViewById(R.id.cancelCreateAccountButton);
      cancelBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            ((MainActivity) getActivity()).onBackPressed();
         }
      });

   }

}
