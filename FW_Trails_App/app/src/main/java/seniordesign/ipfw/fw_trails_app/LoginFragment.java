package seniordesign.ipfw.fw_trails_app;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrailMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrailMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

   private final String fragmentTitle = "Login";
   private Button createAccountBtn;
   private Button  loginBtn;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      RelativeLayout loadedRelativeLayout    = (RelativeLayout)    inflater.inflate(R.layout.fragment_login, container, false);

      // Get access to the button and set a listener to it.
      setCreateAccountListener(loadedRelativeLayout);
      setLoginListener(loadedRelativeLayout);

      return loadedRelativeLayout; // We must return the loaded Layout
   }

   public String getTitle(){
      return fragmentTitle;
   }


   // Sets up the listener for when the user tries to create an account.
   private void setCreateAccountListener(View view){
      createAccountBtn = (Button) view.findViewById(R.id.button_CreateAccount);

      createAccountBtn.setOnClickListener(new View.OnClickListener() {

         // Swap the fragment using the MainActivity
         @Override
         public void onClick(View v) {
            ((MainActivity)getActivity()).displayView(R.layout.fragment_account_create);
         }
      });
   }

   // Sets the Login Listener
   // Currently bypasses any authentication.
   // TODO: get authentication working
   private void setLoginListener(View view)
   {
      loginBtn = (Button) view.findViewById(R.id.button_Login);

      loginBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            ((MainActivity)getActivity()).displayView(R.id.nav_recordActivity);
         }
      });
   }
}
