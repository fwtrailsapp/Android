package seniordesign.ipfw.fw_trails_app;

import android.content.Context;
import android.os.Looper;
import android.preference.PreferenceActivity;
import android.util.Log;

import org.json.*;
import com.loopj.android.http.*;

import java.util.ArrayList;
import java.util.IllegalFormatException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Jaron on 3/9/2016.
 */
public class HttpClientUtil {

   public static final String BASE_URL_STATISTICS = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/Statistics";
   public static final String BASE_URL_ACTIVITY = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Activity";
   public static final String BASE_URL_ACCOUNT_DETAILS = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Account";
   public static final String BASE_URL_ACCOUNT_DETAILS_UPDATE = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Account/edit";
   public static final String BASE_URL_LOGIN = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Login";
   public static final String BASE_URL_CREATE_ACCOUNT = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/trails/api/1/Account/Create";
   public static final String CONTENT_TYPE = "application/json";

   private String authKeycode;
   private String username;
   private String password;

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

   // Concatenates the relative URL with the base url of our API.
   private static String getAbsoluteUrl(String relativeUrl){
      return relativeUrl;
   }


}
