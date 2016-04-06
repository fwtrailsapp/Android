package seniordesign.ipfw.fw_trails_app;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/*
  The account details fragment contains the view for all of the account information. The user can
  change user data in this view as well.

  It simply grabs the account information from the server using the AccountDetailsFragmentController
  and displays it on the view. When the user clicks submit, we run a comparison on previously loaded
  information and the existing information to see if we need to commit any changes.
 */
public class AccountDetailsFragment extends Fragment {
   private final String fragmentTitle = "Account Details";
   private View view;
   AccountDetailsModel accountDetailsModel;

   // fields to cotnain possibly new values.
   private String username;
   private String password;
   private String confirmPassword;
   private int weight;
   private int height;
   private int birthYear;
   private GenderOptions gender;


   // controls for the account details fragment view
   private EditText usernameEditText;
   private EditText passwordEditText;
   private EditText passwordConfirmEditText;
   private EditText heightEditText;
   private EditText weightEditText;
   private EditText birthYearEditText;
   private Spinner genderSpinner;
   private Button submitButton;
   private Button cancelButton;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      view = inflater.inflate(R.layout.fragment_account_details, container, false);

      setEditTextControls();
      setButtonListeners();



      return view; // We must return the loaded Layout
   }

   // Sets the buttons to their respective listeners
   private void setButtonListeners(){
      submitButton = (Button) view.findViewById(R.id.accountDetailsAcceptButton);
      cancelButton = (Button) view.findViewById(R.id.accountDetailsCancelButton);

      submitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (validateEntries()) {
               // Kick off the api call to submit the new account info changes
               AccountDetailsUpdateController accountDetailsController = new AccountDetailsUpdateController();
               accountDetailsController.execute();
            }

         }
      });
   }

   // Assigns the controls of this view to the controls of this class
   private void setEditTextControls(){
      usernameEditText = (EditText) view.findViewById(R.id.accountDetailsUsernameEditText);
      passwordEditText = (EditText) view.findViewById(R.id.accountDetailsPasswordEditText);
      passwordConfirmEditText = (EditText) view.findViewById(R.id.accountDetailsConfirmPasswordEditText);
      heightEditText = (EditText) view.findViewById(R.id.accountDetailsHeight_editText);
      weightEditText = (EditText) view.findViewById(R.id.accountDetailsWeightEditText);
      birthYearEditText = (EditText) view.findViewById(R.id.accountDetailsBirthYearEditText);
      genderSpinner = (Spinner) view.findViewById(R.id.accountDetails_gender_Spinner);
   }

   // Returns the title of this fragment
   public String getTitle(){
      return fragmentTitle;
   }

   // Validates the fields on the view.
   private boolean validateEntries(){
      boolean result = true;

      assignValues();

   return result;
   }


   // Assign the current entries to the fields in order to compare them with the original data.
   private void assignValues(){

   }

   // This class handles the connection between the server and the mobile app in regards to the
   // account details and updates to the account info. It grabs the details from the server and provides
   // a process in which a user can update their provided information.
   private class AccountDetailsUpdateController extends AsyncTask<Void, Void, Void> {



      // Attempts to post the new
      @Override
      protected Void doInBackground(Void... params) {

         HttpClientUtil.post(HttpClientUtil.BASE_URL_ACCOUNT_DETAILS, null, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

            @Override
            public void onStart() {

            }

            // When we succeed at getting a response back
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
               // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

         });

         return null;
      }

      // theItems parameter is the return value of the doInBackground method. This contains the
      // list of objects to be displayed in the ListView
      @Override
      protected void onPostExecute(Void param) {

      }
   }

   // This class handles the connection between the server and the mobile app in regards to the
   // account details and updates to the account info. It grabs the details from the server and provides
   // a process in which a user can update their provided information.
   private class AccountDetailsGetController extends AsyncTask<Void, Void, Void> {



      // Attempts to post the new
      @Override
      protected Void doInBackground(Void... params) {

         HttpClientUtil.get(HttpClientUtil.BASE_URL_ACCOUNT_DETAILS, null, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

            @Override
            public void onStart() {

            }

            // When we succeed at getting a response back
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
               // Create the accountDetailsModel object.
               // parse the returned json and use it to create the accountDetailsModel
               /*accountDetailsModel = new AccountDetailsModel(HttpClientUtil.getInstance().getAccountUsername(),
                       HttpClientUtil.getInstance().getAccountPassword());*/
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
               // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

         });

         return null;
      }

      // theItems parameter is the return value of the doInBackground method. This contains the
      // list of objects to be displayed in the ListView
      @Override
      protected void onPostExecute(Void param) {

      }
   }
}
