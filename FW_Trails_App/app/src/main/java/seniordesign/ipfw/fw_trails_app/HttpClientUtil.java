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

   public static final String BASE_URL = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/Activity/szook";

   public HttpClientUtil(){

   }

   private static SyncHttpClient client = new SyncHttpClient();

   public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.get(BASE_URL, params, responseHandler);
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


   // Concatenates the relative URL with the base url of our API.
   private static String getAbsoluteUrl(String relativeUrl){
      return BASE_URL + relativeUrl;
   }


}
