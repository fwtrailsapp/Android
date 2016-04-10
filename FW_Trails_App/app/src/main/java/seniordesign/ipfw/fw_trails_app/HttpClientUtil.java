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
import android.os.AsyncTask;
import android.os.Looper;
import android.preference.PreferenceActivity;
import android.util.Log;

import org.json.*;
import com.loopj.android.http.*;

import java.util.ArrayList;
import java.util.IllegalFormatException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;


public class HttpClientUtil {

   public static final String BASE_URL_STATISTICS = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Statistics";
   public static final String BASE_URL_ACTIVITY = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Activity";
   public static final String BASE_URL_ACCOUNT_DETAILS = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Account";
   public static final String BASE_URL_ACCOUNT_DETAILS_UPDATE = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Account/edit";
   public static final String BASE_URL_LOGIN = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Login";
   public static final String BASE_URL_CREATE_ACCOUNT = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Account/Create";
   public static final String CONTENT_TYPE = "application/json";

   private String authKeycode;
   private String username;
   private String password;
   private boolean loginSuccessful;

   private static HttpClientUtil httpClientUtil = new HttpClientUtil();
   private static SyncHttpClient client = new SyncHttpClient();

   private HttpClientUtil(){

   }

   public static HttpClientUtil getInstance(){
      return httpClientUtil;
   }

   public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.get(url, params, responseHandler);
   }

   public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.post(getAbsoluteUrl(url), params, responseHandler);
   }


   public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.get(url, params, responseHandler);
   }

   public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.post(url, params, responseHandler);
   }

   public static void postByUrl(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
      client.post(context, url, entity, contentType, responseHandler);
   }

   // Sets the authentication keycode that is used in all api calls after login.
   public void setAuthKeycode(String theKey){
      authKeycode = theKey;
      client.addHeader("Trails-Api-Key", authKeycode);
   }

   // Save the account login info in the case the server loses the existing auth token for
   // the current instance of the application. We can use this data to login again, under the hood,
   // to get a new auth token that is recognized.
   public void setAccountInfo(String username, String password){
      this.username = username;
      this.password = password;
   }

   // Returns the authcode that is used to be appended in the headers of the posts and gets.
   public String getAuthCode(){
      return authKeycode;
   }

   // Returns the username of the current account.
   public String getAccountUsername(){
      return username;
   }

   // Returns the password of the current account.
   public String getAccountPassword(){
      return password;
   }

   public void reobtainAuthToken(){
      HttpClientUtilController httpClientUtilController = new HttpClientUtilController();
      httpClientUtilController.execute();
   }

   // Concatenates the relative URL with the base url of our API.
   private static String getAbsoluteUrl(String relativeUrl){
      return relativeUrl;
   }

   /*
The HttpClientUtilController class.

doInBackground:
We send the login credentials to the server to check if they are valid. If they are valid, the
server should return onSuccess and from there we can  reassign the updated auth token.

The onPFailure currently does nothing
 */
   private class HttpClientUtilController extends AsyncTask<Void, Void, Void> {


      private final String contentType = "application/json";
      private final String token = "token";


      @Override
      protected void onPreExecute() {

         // Reset the login attempt to failing so we know if it actually passed or not.
         loginSuccessful = false;
      }



      // Send off the activity data to the server.
      @Override
      protected Void doInBackground(Void... params) {

         JSONObject loginJSON = null;
         StringEntity jsonString = null;
         try{

            // Convert Login info to JSON then to parameters for the post activity.
            loginJSON = createLoginJSONObject();
            jsonString = new StringEntity(loginJSON.toString());
         }
         catch(Exception ex){
            Log.i("JSON/Encode Exception:", ex.getMessage());
         }

         // Post the login attempt to the server with the given username and password.
         postByUrl(null, HttpClientUtil.BASE_URL_LOGIN, jsonString, contentType,
                 new AsyncHttpResponseHandler(Looper.getMainLooper()) {

                    // Before the actual post happens.
                    @Override
                    public void onStart() {

                    }

                    // Here you received http 200, Save off the auth token to the HttpUtils class to
                    // use in subsequent api calls and start record activity.
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                       try {

                          // parse the response to save the auth code for future api calls.
                          JSONObject jsonResponse = new JSONObject(new String(response));
                          httpClientUtil.setAuthKeycode(jsonResponse.getString(token));
                          loginSuccessful = true;

                       } catch (JSONException ex) {
                          Log.i("Networking Exception", ex.getMessage());
                          Log.i("Response: ", new String(response));
                       }


                    }

                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    // If it fails to post, you can issue some sort of alert dialog saying the error
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

                    }
                 });

         return null;
      }


      // This method gets executed after the doInBackground process finishes.
      @Override
      protected void onPostExecute(Void params) {
         super.onPostExecute(params);


      }

      /* Example Login POST in JSON
        {
             "username":"jsomers",
             "password":"somePW"
        }
      */
      // Creates a Login JSON Object the server can use to verify a user's identity.
      private JSONObject createLoginJSONObject() throws JSONException {
         JSONObject loginJSONObject = new JSONObject();

         // add the username and password to the object.
         loginJSONObject.put("username", username);
         loginJSONObject.put("password", password);

         return loginJSONObject;
      }

   }


}
