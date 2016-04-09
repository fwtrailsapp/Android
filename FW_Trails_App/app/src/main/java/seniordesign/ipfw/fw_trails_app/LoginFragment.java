/**
 * This is the Login Fragment of the FW Trails App
 *
 * It contains two paths. Login to login into the application to perform activites or
 * to create an account to use the application.
 *
 * The authentication process for this application is custom.  As of April 2016, it
 * is outlined by the process below:
 * http://i.imgur.com/AqQj9if.png is a diagram of the process.
 *
 * User enters Username or Password and clicks login.
 * Applicatin sends off a POST with the username and password in plain text.
 * The server validates the arguments against existing username and password combinations.
 *
 * If the username and pw combo is correct, the login fragment should kick off the record activity
 * fragment while setting the HttpUtils apiKey field to the auth token sent back from the server.
 * This auth token is then used in the rest of the calls to the server.
 */
package seniordesign.ipfw.fw_trails_app;


import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class LoginFragment extends Fragment {

   private final String fragmentTitle = "Login";
   private EditText usernameTxt;
   private EditText passwordTxt;
   private Button createAccountBtn;
   private Button  loginBtn;
   private View theView;
   private String theUsername;
   private String thePassword;
   private boolean loginSuccessful = false;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      theView    = inflater.inflate(R.layout.fragment_login, container, false);

      // Assign the Username and Password controls
      usernameTxt = (EditText) theView.findViewById(R.id.usernameEditText);
      passwordTxt = (EditText) theView.findViewById(R.id.passwordEditText);


      // Get access to the button and set a listener to it.
      setCreateAccountListener(theView);
      setLoginListener(theView);

      return theView; // We must return the loaded Layout
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
   private void setLoginListener(View view)
   {
      loginBtn = (Button) view.findViewById(R.id.button_Login);

      loginBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            validateLogin();
         }
      });
   }

   // This method uses the provided username and password to send it to the server and check if an
   // account exists.
   private void validateLogin(){
      // Ensure the username and password text boxes aren't empty.

      if(getUserInput()){
         LoginController loginController = new LoginController();
         loginController.execute();
      }
      else{
         //display popup telling user to enter a username and password
         displayLoginError(getString(R.string.login_error_no_credentials));
      }
   }

   // Returns true if the user has given information for the username and password fields and
   // assigns the user input if valid.
   private boolean getUserInput(){
      boolean result = true;

      // Check to make sure they entered information.
      if(usernameTxt.getText().toString().equals("") || passwordTxt.getText().toString().equals("")){
         result = false;
      }
      else{
         // Assign the username and password to be used in the login attempt.
         theUsername = usernameTxt.getText().toString();
         thePassword = passwordTxt.getText().toString();
      }

      return result;
   }


   // This is a function that displays an error dialog to tell the user that something bad
   // happened.
   private void displayLoginError(String errorText) {
      // Create a dialog to determine if the user wants to post their activity
      AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
      alertDialog.setTitle(fragmentTitle+ " Error");

      // If modal doesn't work, garbage collect the activity.
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

   /*
 The LoginController class.

 This class extends the AsyncTask to spawn off a new thread that posts login attempts to
  the web server. The HttpClientUtil class is the class that actually sends off the request using
 a Synchronous Http handler.

 doInBackground:
 We send the login credentials to the server to check if they are valid. If they are valid, the
 server should return onSuccess and from there we can instantiate the record activity view.

 The onPFailure tells the user there was an incorrect username and password combo.
  */
   private class LoginController extends AsyncTask<Void, Void, Void> {


      private final String contentType = "application/json";
      private final String token = "token";


      @Override
      protected void onPreExecute() {
      }

      // Send off the activity data to the server.
      @Override
      protected Void doInBackground(Void... params) {

         // Build the parameters for the Login JSON
         // This json will contain the username and password that is verified against an existing
         // account. If the account exists, we should receive an API token that can be used to
         // make further calls to the API
         JSONObject loginJSON = null;
         StringEntity jsonString = null;
         try{

            // Convert Login info to JSON then to parameters for the login request.
            loginJSON = createLoginJSONObject();
            jsonString = new StringEntity(loginJSON.toString());
         }
         catch(Exception ex){
            Log.i("JSON/Encode Exception:", ex.getMessage());
         }

         // Post the login attempt to the server with the given username and password.
         HttpClientUtil.postByUrl(getContext(), HttpClientUtil.BASE_URL_LOGIN, jsonString, contentType,
                 new AsyncHttpResponseHandler(Looper.getMainLooper()) {

                    // Before the actual post happens.
                    @Override
                    public void onStart() {

                    }

                    // Here you received http 200, Save off the auth token to the HttpUtils class to
                    // use in subsequent api calls and start record activity.
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                       try{

                          // parse the response to save the auth code for future api calls and
                          // save the password and username in case we need to login again.
                          JSONObject jsonResponse = new JSONObject(new String(response));
                          HttpClientUtil.getInstance().setAuthKeycode(jsonResponse.getString(token));
                          HttpClientUtil.getInstance().setAccountInfo(theUsername, thePassword);
                          loginSuccessful = true;

                       } catch(JSONException ex){
                          Log.i("Networking Exception", ex.getMessage());
                          Log.i("Response: ", new String(response));
                       }


                    }

                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    // If it fails to post, you can issue some sort of alert dialog saying the login
                    // error
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

                       // Run on UI thread due to AsyncTask
                       getActivity().runOnUiThread(new Runnable() {

                          public void run() {
                             // Create an error dialog saying the user had incorrect input
                             displayLoginError(getString(R.string.login_error_incorrect_credentials));
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

         // if the login was successful, swap views with the recordActivity.
         if(loginSuccessful){
            ((MainActivity) getActivity()).displayView(R.id.nav_recordActivity);
         }


      }

      /* Example Login POST in JSON
        {
             "username":"szook",
             "password":"somePW"
        }
      */
      // Creates a Login JSON Object the server can use to verify a user's identity.
      private JSONObject createLoginJSONObject() throws JSONException {
         JSONObject loginJSONObject = new JSONObject();

         // add the username and password to the object.
         loginJSONObject.put("username", theUsername);
         loginJSONObject.put("password", thePassword);

         return loginJSONObject;
      }

   }

}
