package seniordesign.ipfw.fw_trails_app;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/*
  The account details fragment contains the view for all of the account information. The user can
  change user data in this view as well.

  It simply grabs the account information from the server using the AccountDetailsFragmentController
  and displays it on the view. When the user clicks submit, we run a comparison on previously loaded
  information and the existing information to see if we need to commit any changes.
 */
public class AccountDetailsFragment extends Fragment {
   private final int NOT_PROVIDED = -1;
   private final String NOT_PROVIDED_TEXT = "Not provided";
   private final String fragmentTitle = "Account Details";
   private final String numberFormatErrorText = "Please enter numeric values only for birth year, height, and weight.";
   private View view;
   AccountDetailsModel accountDetailsModel;

   // fields to contain possibly new values.
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

      // instantiate the controls for this view.
      setEditTextControls();
      setButtonListeners();

      // populate the view with the data from the server
      AccountDetailsGetController accountDetailsGetController = new AccountDetailsGetController();
      accountDetailsGetController.execute();

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

      cancelButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            // reset the user changes if accountDetailsModel has been instantiated already.
            if (accountDetailsModel != null) {
               assignAccountDetailsModelInfo();
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

   // Displays a dialog describing the error passed in.
   private void displayError(String errorText) {

      AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
      alertDialog.setTitle(fragmentTitle+ " Error");

      // Modal settings are set
      alertDialog.setCancelable(false);
      alertDialog.setMessage(errorText);
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
              new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                 }
              });

      alertDialog.show();
   }

   // Displays a dialog describing the error passed in.
   private void displaySuccess(String successText) {

      AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
      alertDialog.setTitle(fragmentTitle+ " Success");

      // Modal settings are set
      alertDialog.setCancelable(false);
      alertDialog.setMessage(successText);
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
              new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                 }
              });

      alertDialog.show();
   }

   // Validates the fields on the view.
   // Assign the current entries at the time of submission to the fields in order to compare
   // them with the original data.
   private boolean validateEntries(){
      boolean result = false;

      try{

         result = true;
         // Check to see if the username changed
         username = usernameEditText.getText().toString();

         // If the username did change, change it in the accountDetailsModel object.
         // the original is still saved in the HttpClientUtils instance in case there is an error
         // updating the original username (ie username already exists...)
         if(!username.equals(accountDetailsModel.getUsername())){
            // if it is a valid new username, change it.
            if(isValidString(username)){
               accountDetailsModel.changeUsername(username);
            }
            else {
               result = false;
               displayError(getString(R.string.error_invalid_username));
            }
         }


         // Check to see if the password changed
         password = passwordEditText.getText().toString();

         // if the password doesnt match the original, check if it is valid. If it is valid and
         // the new password matches the confirm box, then change the model password.
         if(!password.equals(accountDetailsModel.getPassword()) && isValidString(password)){
            if(isValidString(password)){
               // password changed, see if the confirm also matches the new password. If they match,
               // change it in the model
               if(password.equals(passwordConfirmEditText.getText().toString())){
                  accountDetailsModel.changePassword(password);
               }
               else{
                  result = false;
                  displayError(getString(R.string.accountDetailsPWMismatchError));
               }
            }
            else {
               result = false;
               displayError(getString(R.string.error_invalid_password));
            }

         }

         // Check to see if the height changed.
         // Replace the units with nothing so we have a clean conversion.
         height = Integer.valueOf(heightEditText.getText().toString().replaceAll("\\D+",""));

         // If the height changed, validate the new height value and if it is valid, change it in the
         // model.
         if(height != accountDetailsModel.getHeight()){
            // if it is valid, change it
            if(isValidInt(height)){
               accountDetailsModel.changeHeight(height);
            }
            else{
               result = false;
               displayError(getString(R.string.create_account_invalid_height));
            }
         }

         // Check to see if the weight changed.
         weight = Integer.valueOf(weightEditText.getText().toString().replaceAll("\\D+", ""));

         // If the weight changed, validate the new weight value and if it is valid, change it in the
         // model.
         if(weight != accountDetailsModel.getWeight()){
            // if it is valid, change it
            if(isValidInt(weight)){
               accountDetailsModel.changeWeight(weight);
            }
            else{
               result = false;
               displayError(getString(R.string.create_account_invalid_weight));
            }
         }

         // check if the birth year changed
         birthYear = Integer.valueOf(birthYearEditText.getText().toString().replaceAll("\\D+", ""));

         // if the birth year changed, ensure it is valid and change it in the model.
         if(birthYear != accountDetailsModel.getBirthYear()){
            if(isValidInt(birthYear)){
               accountDetailsModel.changeBirthYear(birthYear);
            }
            else{
               result = false;
               displayError(getString(R.string.create_account_invalid_birth_year));
            }
         }

         // check if gender changed
         if(genderSpinner.getSelectedItemPosition() == GenderOptions.MALE){
            gender = GenderOptions.Male;
         }
         else if(genderSpinner.getSelectedItemPosition() == GenderOptions.FEMALE){
            gender = GenderOptions.Female;
         }
         else{
            gender = GenderOptions.PreferNotToDisclose;
         }

         // if they aren't equal, then it changed and assign it the new value.
         if(gender != accountDetailsModel.getGender()){
            accountDetailsModel.changeGender(gender);
         }
      }catch(NumberFormatException ex){
         displayError(numberFormatErrorText);
      }

   return result;
   }

   // rough validation to ensure the user doesn't set garbage data
   private boolean isValidInt(int theInt){
      return theInt > 0 && theInt < Integer.MAX_VALUE;
   }

   // rough validation to ensure the user doesn't set garbage data
   private boolean isValidString(String theString){
      return (!theString.equals("")) && theString.length() <= 16;
   }

   // Assign the original values from the server to the view so the user can see their info.
   private void assignAccountDetailsModelInfo(){
      usernameEditText.setText(accountDetailsModel.getUsername());

      passwordEditText.setText(accountDetailsModel.getPassword());
      passwordConfirmEditText.setText(accountDetailsModel.getPassword());


      // Set the height with in as the unit
      if(accountDetailsModel.getHeight() != NOT_PROVIDED){
         String heightText = accountDetailsModel.getHeight() + " in";
         heightEditText.setText(heightText);
      }
      else{
         heightEditText.setText(NOT_PROVIDED_TEXT);
      }


      // Set the weight with lbs as the unit
      // if not provided, state it in the edit text
      if(accountDetailsModel.getWeight() != NOT_PROVIDED){
         String  weightText = accountDetailsModel.getWeight() + " lbs";
         weightEditText.setText(weightText);
      }
      else{
         weightEditText.setText(NOT_PROVIDED_TEXT);
      }

      if(accountDetailsModel.getBirthYear() != NOT_PROVIDED){
         birthYearEditText.setText(String.valueOf(accountDetailsModel.getBirthYear()));
      }
      else{
         birthYearEditText.setText(NOT_PROVIDED_TEXT);
      }

      // if they selected a gender, check if it is male or female, otherwise select n/a
      if(accountDetailsModel.getGender() != GenderOptions.PreferNotToDisclose){
         if(accountDetailsModel.getGender() == GenderOptions.Male){
            // set to male
            genderSpinner.setSelection(GenderOptions.MALE);
         }
         else{
            // Set to female as the last option.
            genderSpinner.setSelection(GenderOptions.FEMALE);
         }
      }
      else{
         // Set to prefer not to disclose
         genderSpinner.setSelection(GenderOptions.PREFER_NOT_TO_DISCLOSE);
      }

   }


   // This class handles the connection between the server and the mobile app in regards to the
   // account details and updates to the account info. It grabs the details from the server and provides
   // a process in which a user can update their provided information.
   private class AccountDetailsUpdateController extends AsyncTask<Void, Void, Void> {

      // Send off the updated information to the server
      @Override
      protected Void doInBackground(Void... params) {

         // Build the parameters for the created account via JSON
         final JSONObject accountUpdatesJSON;
         StringEntity jsonString = null;
         try{

            // Convert Account Create info to JSON then to parameters for the post activity.
            accountUpdatesJSON = createAccountDetailsJSON();
            jsonString = new StringEntity(accountUpdatesJSON.toString());
         }
         catch(Exception ex){
            Log.i("JSON/Encode Exception:", ex.getMessage());
         }



         // Currently hardcoded the URL (using postByUrl). We will eventually be to the point where we just post
         // username/Activity and the util class will have the long base url name.
         HttpClientUtil.postByUrl(getContext(), HttpClientUtil.BASE_URL_ACCOUNT_DETAILS_UPDATE, jsonString, HttpClientUtil.CONTENT_TYPE,
                 new AsyncHttpResponseHandler(Looper.getMainLooper()) {

                    // Before the actual post happens.
                    @Override
                    public void onStart() {

                    }

                    // Here you received http 200, tell the user their info was updated.
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                       // save off any new password/username incase we need to relogin while the app
                       // is running.
                       HttpClientUtil.getInstance().setAccountInfo(accountDetailsModel.getUsername(),
                               accountDetailsModel.getPassword());
                       
                       getActivity().runOnUiThread(new Runnable() {

                          public void run() {

                             // Tell the user their info was successfully updated
                             displaySuccess(getString(R.string.accountDetailsUpdateSuccess));

                          }
                       });

                    }

                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    // If it fails to post, tell the user that there was an error updating the
                    // account info
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                       // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                       String error = new String(errorResponse);
                       int statuscode = statusCode;

                       getActivity().runOnUiThread(new Runnable() {

                          public void run() {
                             // Create a dialog to determine if the user wants to post their activity
                             displayError(getString(R.string.accountDetailsUpdateGeneralError));
                          }
                       });
                    }
                 });

         return null;
      }

      // theItems parameter is the return value of the doInBackground method. This contains the
      // list of objects to be displayed in the ListView
      @Override
      protected void onPostExecute(Void param) {

      }

      // Creates the JSON object representing an account update. It is essentially another whole entire
      // account creation, the server just overwrites the values in the database to whatever we send it
      // here.
      // Example:
      //    "username" - "test"
      //    "password" - "apples"
      //    "birthyear" - 1996
      //    "weight" - 125
      //    "sex"- "female"
      //    "height" - 65
      private JSONObject createAccountDetailsJSON(){
         JSONObject updatedAccountInfo = new JSONObject();
         try{

            // Create the json key and value pairs.
            updatedAccountInfo.put("username", accountDetailsModel.getUsername());
            updatedAccountInfo.put("password", accountDetailsModel.getPassword());
            updatedAccountInfo.put("birthyear", accountDetailsModel.getBirthYear());
            updatedAccountInfo.put("weight", accountDetailsModel.getWeight());

            // The server accepts only null if the user is not male or female.
            if(String.valueOf(accountDetailsModel.getGender()).toLowerCase().equals("prefernottodisclose")){
               updatedAccountInfo.put("sex", "null");
            }
            else{
               updatedAccountInfo.put("sex", String.valueOf(accountDetailsModel.getGender()).toLowerCase());
            }

            updatedAccountInfo.put("height", accountDetailsModel.getHeight());

         } catch(JSONException ex){
            Log.i("Network/JSON Exception", ex.getMessage());
         }

         return updatedAccountInfo;
      }
   }

   // This class handles the connection between the server and the mobile app in regards to the
   // account details. It grabs the details from the server and displays it on the screen.
   private class AccountDetailsGetController extends AsyncTask<Void, Void, Void> {
      // fields to contain the original data sent from the server
      private int dob;
      private int weight;
      private GenderOptions gender;
      private int height;

      @Override
      protected void onPreExecute() {

      }


      // Attempts to grab the user details from the server
      @Override
      protected Void doInBackground(Void... params) {

         HttpClientUtil.get(HttpClientUtil.BASE_URL_ACCOUNT_DETAILS, null, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

            @Override
            public void onStart() {

            }

            // When we succeed at getting a response back, parse the response and display the info
            // to the screen
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
               try {
                  // parse the json

                  JSONObject jsonResponse = new JSONObject(new String(response));

                  // parse the returned json and use it to create the accountDetailsModel
                  assignJsonToFields(jsonResponse);

                  // Create the accountDetailsModel object.
                  accountDetailsModel = new AccountDetailsModel(HttpClientUtil.getInstance().getAccountUsername(),
                       HttpClientUtil.getInstance().getAccountPassword(), height,weight,dob, gender);

               }
               catch(JSONException ex){
                  Log.i("Network/JSON Exception", ex.getMessage());
               }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
               // called when response HTTP status is "4XX" (eg. 401, 403, 404)
               getActivity().runOnUiThread(new Runnable() {

                  public void run() {
                     // Create a dialog to determine if the user wants to post their activity
                     displayError(getString(R.string.accountDetailsAccountDetailsGetError));
                  }
               });
            }

         });

         return null;
      }

      // Assign the fields to the values sent back from the server
      private void assignJsonToFields(JSONObject source){
         try{
            String birthYear = String.valueOf(source.get("birthyear"));
            String theWeight = String.valueOf(source.get("weight"));
            String theHeight = String.valueOf(source.get("height"));
            String sex    = String.valueOf(source.get("sex"));

            // if the user did not previously provide details, just make it a blank edit text.
            if(birthYear.equalsIgnoreCase("null")){
               dob = NOT_PROVIDED;
            }
            else {
               dob = Integer.valueOf(birthYear);
            }

            if(theWeight.equalsIgnoreCase("null")){
               weight = NOT_PROVIDED;
            }
            else{
               weight = Integer.valueOf(theWeight);
            }

            if(theHeight.equalsIgnoreCase("null")){
               height = NOT_PROVIDED;
            }
            else{
               height = Integer.valueOf(theHeight);
            }

            // If they do not specify a gender, they choose not to disclose.
            if(sex.equalsIgnoreCase("null")){
               gender = GenderOptions.PreferNotToDisclose;
            }
            else if(sex.equalsIgnoreCase("male")){
               gender = GenderOptions.Male;
            }
            else{
               gender = GenderOptions.Female;
            }
         }
         catch(JSONException ex){
            Log.i("JSON Get Exception", ex.getMessage());
         }

      }


      // last method that is called before the controller completes. This assigns the edit text's values
      // to the server values.
      @Override
      protected void onPostExecute(Void param) {
         // only assign the values in the view if accountDetails has been instantiated.
         if(accountDetailsModel != null){
            assignAccountDetailsModelInfo();
         }

      }
   }
}
