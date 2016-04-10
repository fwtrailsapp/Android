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

import com.google.ads.AdRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class AccountCreateFragment extends Fragment {

   // constants used in this fragment
   private final String fragmentTitle = "Create Account";
   private final int MAX_USERNAME_LENGTH = 16;
   private final int MAX_PASSWORD_LENGTH = 16;
   private final int NOT_ENTERED = -1;

   // controls
   private AccountCreateModel accountCreateModel;
   private EditText usernameEditText;
   private EditText passwordEditText;
   private EditText confirmPasswordEditText;
   private EditText heightEditText;
   private EditText weightEditText;
   private EditText birthYearEditText;
   private Spinner genderSpinner;
   private Button cancelBtn;
   private Button createBtn;
   private View view;

   // variables used in the process of account creation
   private boolean accountCreationSuccessful = false;
   private String username;
   private String password;
   private String confirmPassword;
   private int height = NOT_ENTERED;
   private int weight = NOT_ENTERED;
   private int birthYear = NOT_ENTERED;
   private GenderOptions gender;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_account_create, container, false);

      setCancelButtonListener();
      setCreateAccountButtonListener();
      setEditTextControls();

      return view; // We must return the view
   }

   public String getTitle(){
      return fragmentTitle;
   }

   // Assigns the controls on the view to the objects in this class.
   private void setEditTextControls(){
      usernameEditText = (EditText) view.findViewById(R.id.accountCreate_usernameEditText);
      passwordEditText = (EditText) view.findViewById(R.id.accountCreate_passwordEditText);
      confirmPasswordEditText = (EditText) view.findViewById(R.id.accountCreate_confirmPasswordEditText);
      heightEditText = (EditText) view.findViewById(R.id.accountCreate_heightEditText);
      weightEditText = (EditText) view.findViewById(R.id.accountCreate_weightEditText);
      birthYearEditText = (EditText) view.findViewById(R.id.accountCreate_birthYearEditText);
      genderSpinner = (Spinner) view.findViewById(R.id.accountCreate_genderSpinner);
   }


   // Sets a listener for the cancel button which should return the view to the login screen.
   private void setCancelButtonListener(){

      cancelBtn = (Button) view.findViewById(R.id.cancelCreateAccountButton);
      cancelBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            getActivity().onBackPressed();
         }
      });

   }

   // Displays a dialog describing the error passed in.
   private void displayError(String errorText) {

      AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
      alertDialog.setTitle(fragmentTitle+ " Error");

      // Modal settings are set, user must click ok before the dialog can be dismissed
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
   private void displaySuccessDialog() {

      AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
      alertDialog.setTitle(fragmentTitle+ " Success");

      // Modal settings are set, user must click ok before the dialog can be dismissed
      alertDialog.setCancelable(false);
      alertDialog.setMessage("Please login using your credentials");
      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
              new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                    getFragmentManager().popBackStack();
                    dialog.dismiss();
                 }
              });

      alertDialog.show();
   }

   // Sets a listener for the create account button which should attempt to create the account
   // and on success, attempt to login.
   private void setCreateAccountButtonListener(){
      createBtn = (Button) view.findViewById(R.id.createAccountButton);
      createBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            grabUserInput();
            // Verify the required options are entered
            if (verifyRequiredFields()) {

               // Attempt to create the account model
               if (createAccount()) {
                  // Execute the account create controller to send the post account
                  AccountCreateController accountCreateController = new AccountCreateController();
                  accountCreateController.execute();
               }
               // They entered illogical data for the optional fields
               else {
                  // do nothing since the error is thrown within createAccount()
               }
            } else {

               // Tell the user they need to enter the username, password, and password confirm
               displayError(getString(R.string.create_account_required_params_needed));
            }

         }
      });
   }

   // Assigns the user input to field variables to minimize redundant lookups later on
   // when validating the input
   private void grabUserInput() {
      username = usernameEditText.getText().toString();
      password = passwordEditText.getText().toString();
      confirmPassword = confirmPasswordEditText.getText().toString();

      // only assign it if a user has entered data.
      if(!heightEditText.getText().toString().equals("")){
         height = Integer.valueOf(heightEditText.getText().toString());
      }

      if(!weightEditText.getText().toString().equals("")){
         weight = Integer.valueOf(weightEditText.getText().toString());
      }

      if(!birthYearEditText.getText().toString().equals("")){
         birthYear = Integer.valueOf(birthYearEditText.getText().toString());
      }

      // Gender item 0 is selected by default.(Male)
      gender = getSelectedGender();
   }

   // Gets the selected gender option
   // 0 == male, 1 == female, 2 == n/a
   private GenderOptions getSelectedGender() {
      GenderOptions theGender;
      int index = genderSpinner.getSelectedItemPosition();

      if(index == 0){
         theGender = GenderOptions.Male;
      }
      else if(index == 1){
         theGender = GenderOptions.Female;
      }
      else{
         theGender = GenderOptions.PreferNotToDisclose;
      }

      return theGender;
   }

   // Ensures that the username and password fields contain a value.
   private boolean verifyRequiredFields(){
      return !username.equals("") &&
              !password.equals("") &&
              !confirmPassword.equals("");
   }

   // This method instantiates an account create model if the values are legitimate
   private boolean createAccount(){
      boolean result = true;

      // Check the username length to ensure it is smaller than 256 chars
      if(!validateUsername()) {
         result = false;
         displayError(getString(R.string.create_account_max_username_length_exceeded));
      }

      // validate the password length
      else if(!validatePasswordsLength()){
         result = false;
         displayError(getString(R.string.create_account_max_password_length_exceeded));
      }

      // ensure both password entries are a match
      else if(!passwordsMatch()){
         result = false;
         displayError(getString(R.string.create_account_password_mismatch));
      }

      // Ensure the height is valid
      else if(!validateHeight() && height != NOT_ENTERED){
         result = false;
         displayError(getString(R.string.create_account_invalid_height));
      }

      // Ensure the weight is valid
      else if(!validateWeight() && weight != NOT_ENTERED){
         result = false;
         displayError(getString(R.string.create_account_invalid_weight));
      }

      else if (!validateBirthYear() && birthYear != NOT_ENTERED) {
         result = false;
         displayError(getString(R.string.create_account_invalid_birth_year));
      }

      // All entered fields are validated, create the account.
      else{
         accountCreateModel = new AccountCreateModel(username,password);
         assignOptionalFields();
      }

      return result;
   }

   // Assigns the optional fields that were provided by the user from the kindness of his or her
   // heart
   private void assignOptionalFields(){

      // If the user edited the field, add it to the model.
      if(height != NOT_ENTERED){
         accountCreateModel.setHeight(height);
      }

      if(weight != NOT_ENTERED){
         accountCreateModel.setWeight(weight);
      }

      if(birthYear != NOT_ENTERED){
         accountCreateModel.setBirthYear(birthYear);
      }

      // A gender is always selected
      accountCreateModel.setGender(gender);
   }

   // checks that the birth year is valid
   private boolean validateBirthYear(){
      return birthYear > 0 && birthYear < Integer.MAX_VALUE;
   }

   // checks that the weight is valid
   private boolean  validateWeight(){
      return weight > 0 && weight < Integer.MAX_VALUE;
   }

   // Only requirement for usernames is that they need to be less than or equal to 10 chars in length
   private boolean validateUsername(){
      return username.length() <= MAX_USERNAME_LENGTH;
   }

   // Checks that both password text fields are less than the max length.
   private boolean validatePasswordsLength(){
      return password.length() <= MAX_PASSWORD_LENGTH &&
              confirmPassword.length() <= MAX_PASSWORD_LENGTH;
   }

   // Checks that both passwords match
   private boolean passwordsMatch(){
      return password.equals(confirmPassword);
   }

   // check that the height is not negative or 0
   private boolean validateHeight(){
      return height  > 0 && height < Integer.MAX_VALUE;
   }

   /*
The AccountCreateController class.

This class extends the AsyncTask to spawn off a new thread that posts new account creations to
 the web server. The HttpClientUtil class is the class that actually sends off the request using
a Synchronous Http handler.

doInBackground:
We send the account credentials and info to the server to create the new account. If they are valid, the
server should return onSuccess and from there we can instantiate the record activity view.

The onPFailure tells the user there was an incorrect username and password combo.
 */
   private class AccountCreateController extends AsyncTask<Void, Void, Void> {

      private final int ACCOUNT_ALREADY_EXISTS_ERROR_CODE = 409;


      @Override
      protected void onPreExecute() {

      }



      // Send off the activity data to the server.
      @Override
      protected Void doInBackground(Void... params) {

         // Build the parameters for the created account via JSON
         JSONObject loginJSON;
         StringEntity jsonString = null;
         try{

            // Convert Account Create info to JSON then to parameters for the post activity.
            loginJSON = createAccountCreateJSONObject();
            jsonString = new StringEntity(loginJSON.toString());
         }
         catch(Exception ex){
            Log.i("JSON/Encode Exception:", ex.getMessage());
         }



         // Currently hardcoded the URL (using postByUrl). We will eventually be to the point where we just post
         // username/Activity and the util class will have the long base url name.
         HttpClientUtil.postByUrl(getContext(), HttpClientUtil.BASE_URL_CREATE_ACCOUNT, jsonString, HttpClientUtil.CONTENT_TYPE,
                 new AsyncHttpResponseHandler(Looper.getMainLooper()) {

                    // Before the actual post happens.
                    @Override
                    public void onStart() {

                    }

                    // Here you received http 200, Save off the auth token to the HttpUtils class to
                    // use in subsequent api calls and start record activity.
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                       accountCreationSuccessful = true;
                    }
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    // If it fails to post, you can issue some sort of alert dialog saying the error
                    // and the account couldn't be created for some reason.
                    @Override
                    public void onFailure(final int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

                       accountCreationSuccessful = false;
                       // Run on UI thread due to AsyncTask
                       getActivity().runOnUiThread(new Runnable() {

                          public void run() {
                             // Create a dialog to determine if the user wants to post their activity
                             String errorText;
                             if(statusCode == ACCOUNT_ALREADY_EXISTS_ERROR_CODE) {
                                errorText = getString(R.string.create_account_already_exists_error);
                             }
                             else{
                                errorText = getString(R.string.create_account_error);
                             }

                             displayError(errorText);
                          }
                       });
                    }
                 });

         return null;
      }


      // This method gets executed after the doInBackground process finishes.
      @Override
      protected void onPostExecute(Void params) {
         super.onPostExecute(params);

         if(accountCreationSuccessful){
            // tell the user to login since we created the account successfully
            displaySuccessDialog();
         }


      }

      /* Example AccountCreate POST in JSON
        {
             "username":"ggrimm",
             "password":"wh0cares",
             "dob":1991,
             "weight":150,
             "sex":"male",
             "height":70
        }
      */
      // Possibly change this to a function that takes in a RecordActivityModel or data and
      // returns a json object representing it if we don't want to use Gson.
      private JSONObject createAccountCreateJSONObject() throws JSONException {
         JSONObject accountCreateJSONObject = new JSONObject();

         // add the username and password to the object.
         accountCreateJSONObject.put("username", accountCreateModel.getUsername());
         accountCreateJSONObject.put("password", accountCreateModel.getPassword());
         accountCreateJSONObject.put("birthyear", accountCreateModel.getBirthYear());
         accountCreateJSONObject.put("weight", accountCreateModel.getWeight());
         accountCreateJSONObject.put("sex",accountCreateModel.getGender());
         accountCreateJSONObject.put("height",accountCreateModel.getHeight());

         return accountCreateJSONObject;
      }

   }



}
