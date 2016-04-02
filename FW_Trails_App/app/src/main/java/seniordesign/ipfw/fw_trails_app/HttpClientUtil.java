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

   public static final String BASE_URL_HISTORY = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/Activity/ggrimm";
   public static final String BASE_URL_STATISTICS = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/Statistics/ggrimm";
   public static final String BASE_URL_ACTIVITY = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/Activity";
   public static final String BASE_URL_LOGIN = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/Login";
   private String authKeycode;

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

   // Returns the authcode that is used to be appended in the headers of the posts and gets.
   public String getAuthCode(){
      return authKeycode;
   }


   // Concatenates the relative URL with the base url of our API.
   private static String getAbsoluteUrl(String relativeUrl){
      return relativeUrl;
   }


}
