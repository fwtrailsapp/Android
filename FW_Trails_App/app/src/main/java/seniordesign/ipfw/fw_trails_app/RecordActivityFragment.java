//Comment
package seniordesign.ipfw.fw_trails_app;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.kml.KmlLayer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RecordActivityFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final String fragmentTitle = "Record Activity";


    private GoogleMap mMap;
    private Polyline line;
    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;
    private LocationListener locationListener;
    protected LocationRequest mLocationRequest;

    private boolean recording = false;
    private boolean firstCoordinate = false;
    private ArrayList<LatLng> coordinates = new ArrayList<>();
    private LatLng lastLocation;

    private double metersPerMile = 1609.34;
    private double secondsPerHour = 3600.0;
    private long lastLocationTime;
    private long durationSinceLastLocation;
    private long startMillis;
    private long finishMillis;
    private double currentSpeed;
    NumberFormat distanceFormat = new DecimalFormat("#0.00");
    NumberFormat calorieFormat = new DecimalFormat("#0.0");
    //    NumberFormat secondFormat = new IntegerFormat("00");
    NumberFormat speedFormat = new DecimalFormat("#0.00");
    private final String isoDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final String UTC = "UTC";
    double tempDistance;
    double totalDistance = 0.0;
    private activityTypes activityType;


    private static View view;

    private TextView speed;
    private TextView distance;
    private TextView duration;
    private TextView calories;
    private int caloriesInt = 0;
    private int seconds = 0;
    Button startButton;
    Button pauseButton;
    Button resumeButton;
    Button finishButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Check if we have created the view already, if we haven't create it.
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.fragment_record_activity, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }

        startButton = (Button) view.findViewById(R.id.startButton);
        pauseButton = (Button) view.findViewById(R.id.pauseButton);
        resumeButton = (Button) view.findViewById(R.id.resumeButton);
        finishButton = (Button) view.findViewById(R.id.finishButton);

        buildGoogleApiClient();

        SupportMapFragment mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(this);

        startButton.setOnClickListener(startButtonListener);
        pauseButton.setOnClickListener(pauseButtonListener);
        resumeButton.setOnClickListener(resumeButtonListener);
        finishButton.setOnClickListener(finishButtonListener);
        Log.i("Development", "onCreateView");
        return view; // We must return the loaded Layout
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getChildFragmentManager().findFragmentById(R.id.map).getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        createLocationRequest();
        Log.i("Development", "buildGoogleApiClient");
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(100);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Log.i("Development", "createLocationRequest");
    }

    public void onConnected(Bundle connectionHint) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mLastLocation != null) {
        }
        startLocationUpdates();
        Log.i("Development", "onConnected");
    }

    protected void startLocationUpdates() {
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (recording) {
                    updateLocation(location);
                } else {
                    startMillis += 1000;
                }
            }
        };
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
        }
        Log.i("Development", "startLocationUpdates");
    }

    public void onConnectionSuspended(int n) {
        Log.i("Development", "onConnectionSuspended");
    }

    public void onConnectionFailed(ConnectionResult cr) {
        Log.i("Development", "onConnectionFailed");
    }

    public String getTitle() {
        Log.i("Development", "getTitle");
        return fragmentTitle;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        speed = (TextView) getView().findViewById(R.id.speed);
        distance = (TextView) getView().findViewById(R.id.distance);
        duration = (TextView) getView().findViewById(R.id.duration);
        calories = (TextView) getView().findViewById(R.id.calories);

        LatLng fortWayne = new LatLng(41.0856087, -85.1397336);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(fortWayne, 10));

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setIndoorEnabled(false);

        addKMLLayerToMap();
        addPolylineToMap();

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //Gets called when a new location is found by the network location provider.
                updateLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        Log.i("Development", "onMapReady");
    }

    private void addPolylineToMap() {
        line = mMap.addPolyline(new PolylineOptions()
                .width(10)
                .color(Color.BLUE));
        Log.i("Development", "addPolylineToMap");
    }

    private void addKMLLayerToMap() {
        InputStream inputStream = getResources().openRawResource(R.raw.doc);
        try {
            KmlLayer layer = new KmlLayer(mMap, inputStream, getContext());
            layer.addLayerToMap();
        } catch (org.xmlpull.v1.XmlPullParserException e) {
        } catch (java.io.IOException e) {
        }
        Log.i("Development", "addKMLLayerToMap");
    }

    private void updateLocation(Location location) {
        LatLng updatedLocation = new LatLng(location.getLatitude(), location.getLongitude());
//        Log.i("Development", Double.toString(updatedLocation.latitude) + " " + Double.toString(updatedLocation.longitude));
        if (firstCoordinate) {
            captureFirstCoordinate(updatedLocation);
        } else {
            captureLaterCoordinate(updatedLocation);

            lastLocation = updatedLocation;
        }
        coordinates.add(updatedLocation);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(updatedLocation));
        line.setPoints(coordinates);
        firstCoordinate = false;
        Log.i("Development", "updateLocation");
    }

    private void captureLaterCoordinate(LatLng updatedLocation) {
        caloriesInt++;
        seconds++;
        durationSinceLastLocation = System.currentTimeMillis() - lastLocationTime;
        lastLocationTime = System.currentTimeMillis();
        tempDistance = SphericalUtil.computeDistanceBetween(lastLocation, updatedLocation) / metersPerMile;
        totalDistance += tempDistance;
        distance.setText("Distance: " + String.valueOf(distanceFormat.format(totalDistance)) + " mi");
//            Log.i("Development", String.valueOf(distanceFormat.format(totalDistance)) + " miles");
        calories.setText("Calories: " + Integer.toString(caloriesInt) + ".00");
        Long secondsLong = ((lastLocationTime - startMillis) / 1000) % 60;
        String seconds = "";
        if (secondsLong < 10) {
            seconds += "0";
        }
        seconds += secondsLong;
        duration.setText("Duration: " + Long.toString((lastLocationTime - startMillis) / 1000 / 60) + ":" + seconds);
//        Log.i("Development", seconds + " " + Long.toString(lastLocationTime-startMillis));
        currentSpeed = tempDistance / metersPerMile / durationSinceLastLocation * 1000 * secondsPerHour;
        speed.setText("Speed: " + speedFormat.format(currentSpeed) + " mph");
//            Log.i("Development", speedFormat.format(currentSpeed) + " mph");
    }

    private void captureFirstCoordinate(LatLng updatedLocation) {
        mMap.addMarker(new MarkerOptions().position(updatedLocation).title("Start Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        lastLocationTime = System.currentTimeMillis();
        startMillis = lastLocationTime;
        lastLocation = updatedLocation;
    }

    private View.OnClickListener startButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            selectActivityType();
//            startRecording();
        }
    };

    public void selectActivityType() {
        CharSequence colors[] = new CharSequence[]{"Bike", "Run", "Walk", "Other"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getChildFragmentManager().findFragmentById(R.id.map).getContext());
        builder.setTitle("Select Exercise Type");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startRecording(activityTypes.Bike);
                        break;
                    case 1:
                        startRecording(activityTypes.Run);
                        break;
                    case 2:
                        startRecording(activityTypes.Walk);
                        break;
                    default:
                        startRecording(activityTypes.Other);
                        break;
                }
            }
        });
        builder.show();
    }

    public void startRecording(activityTypes activityType) {
        this.activityType = activityType;
        recording = true;
        firstCoordinate = true;
        startButton = (Button) getView().findViewById(R.id.startButton);
        pauseButton = (Button) getView().findViewById(R.id.pauseButton);
        startButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        Log.i("Development", "startRecording");
    }

    private View.OnClickListener pauseButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            pauseRecording();
        }
    };

    public void pauseRecording() {
        recording = false;
        pauseButton = (Button) getView().findViewById(R.id.pauseButton);
        resumeButton = (Button) getView().findViewById(R.id.resumeButton);
        finishButton = (Button) getView().findViewById(R.id.finishButton);
        pauseButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.VISIBLE);
        Log.i("Development", "pauseRecording");
    }

    private View.OnClickListener resumeButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            resumeRecording();
        }
    };

    public void resumeRecording() {
        recording = true;
        pauseButton = (Button) getView().findViewById(R.id.pauseButton);
        resumeButton = (Button) getView().findViewById(R.id.resumeButton);
        finishButton = (Button) getView().findViewById(R.id.finishButton);
        pauseButton.setVisibility(View.VISIBLE);
        resumeButton.setVisibility(View.GONE);
        finishButton.setVisibility(View.GONE);
        Log.i("Development", "resumeRecording");
    }

    private View.OnClickListener finishButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            finishRecording();
        }
    };

    public void finishRecording() {
        recording = false;
        finishMillis = System.currentTimeMillis();
        mMap.addMarker(new MarkerOptions().position(coordinates.get(coordinates.size() - 1)).title("Stop Location"));
        resumeButton = (Button) getView().findViewById(R.id.resumeButton);
        finishButton = (Button) getView().findViewById(R.id.finishButton);
        resumeButton.setVisibility(View.GONE);
        finishButton.setVisibility(View.GONE);

        // Attempt to send to the server
        //RecordActivityController recordActivityTask = new RecordActivityController();
        //recordActivityTask.execute();

        // if it fails, write to file
        // You could write the file writing logic in the onFailure method in the controller.
        String filename = sendToFile();
        File file = getFile(filename);
        if(file == null){
            Log.i("Development", "Returned file is null");
        }
        else{
            openFile(file);
        }
        Log.i("Development", "finishRecording");

        String message = "";
        message += "Exercise Type: " + activityType;
        Long secondsLong = ((lastLocationTime - startMillis) / 1000) % 60;
        String seconds = "";
        if (secondsLong < 10) {
            seconds += "0";
        }
        seconds += secondsLong;
        message += "\nDuration: " + Long.toString((lastLocationTime - startMillis) / 1000 / 60) + ":" + seconds;
        message += "\nDistance: " + String.valueOf(distanceFormat.format(totalDistance)) + " mi";
        message += "\nCalories: " + caloriesInt;
        message += "\nTop Speed: " + "15 mph";
        message += "\nAverage Speed: " + "10 mph";
        displayStatistics(message);
    }

    public void displayStatistics(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Activity Summary");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private String sendToFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        String FILENAME = "RA " + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        String output = year + "\n" + month + "\n" + day + "\n" + hour + "\n" + minute + "\n" + second;
        output += "\n" + activityType;
        output += "\n" + totalDistance;
        output += "\n" + caloriesInt;
        output += "\n" + duration;
        for (int i = 0; i < coordinates.size(); i++) {
            output += "\n" + coordinates.get(i).toString();
        }

        try {
            FileOutputStream fos = getContext().openFileOutput(FILENAME, getContext().MODE_PRIVATE);
            fos.write(output.getBytes());
            fos.close();
        } catch (Exception e) {

        }
        Log.i("Development", "sendToFile");

        //Print list of all app files
        String[] fileList = getContext().fileList();
        for (int i = 0; i < fileList.length; i++) {
            Log.i("Development", "FILENAME: " + fileList[i]);
        }

        return FILENAME;
    }

    public File getFile(String filename) {
        Log.i("Development", "getFile");
        try{
            File file = new File(getContext().getFilesDir(), filename);
            return file;
        } catch (Exception e){
            Log.e("Development", "Failed to open file");
            return null;
        }
    }

    public void openFile(File file){

//        String eol = System.getProperty("line.separator");
//        BufferedReader input = null;
//        try {
//            input = new BufferedReader(new InputStreamReader(openFileInput(file)));
//            String line;
//            StringBuffer buffer = new StringBuffer();
//            while ((line = input.readLine()) != null) {
//                buffer.append(line + eol);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);
        }catch (Exception e) {
            e.printStackTrace();
            Log.i("Development", "1NNNNOOOOOOOOOPPPPPPPPEEEEEEEEE");
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String temp;
        int lineCounter = 0;
        try{
            while((temp = br.readLine()) != null){
                lineCounter++;
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.i("Development", "2NNNNOOOOOOOOOPPPPPPPPEEEEEEEEE");
        }


        try
        {
            fis.getChannel().position(0);
            Log.i("Development", "Reset");
        }
        catch (Exception e) {e.printStackTrace();}


        String[] output = new String[lineCounter];
        String line;
        int counter = 0;
        try{
            Log.i("Development", "Try printing-----------------------------------------------------------");
            for (int i = 0; i < lineCounter; i++){
//                String line2 = br.readLine();
//                output[i] = line2;
                output[i] = br.readLine();
                Log.i("Development", output[i]);
            }
//            while((line = br.readLine()) != null){
//                output[counter] = line;
//                Log.i("Development", "Line: " + line);
//                counter++;
//            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("Development", "3NNNNOOOOOOOOOPPPPPPPPEEEEEEEEE");
        }

        String printMe = "";
        for(int i = 0; i < output.length; i++){
//            Log.i("Development", "Here");
//            Log.i("Development", Integer.toString(output.length));
            printMe += output[i];
        }
        Log.i("Develop", printMe);

        Log.i("Development", "openfile");
    }
    /*
  The RecordActivityController class.

  This class extends the AsyncTask to spawn off a new thread that grabs the activity history results
  from the webserver. The HttpClientUtil class is the class that actually sends off the request using
  an Synchronous Http Response handler.

  We then parse the returned items in the onSuccess of the AsyncHttpResponseHandler and add the items
  to the items ArrayList in the activity history fragment class.

  The onPostExecute method is what updates the actual ListViewAdapter
   */
    private class RecordActivityController extends AsyncTask<Void, Void, Void> {


        private final String defaultPostURL = "http://68.39.46.187:50000/GreenwayCap/DataRelay.svc/Activity";
        private final String utf8CharSet = "UTF-8";
        private final String contentType = "application/json";

        //TODO: This is where the dialog "Are you sure you wish to complete this activity" goes.
        // See http://stackoverflow.com/questions/6039158/android-cancel-async-task
        @Override
        protected void onPreExecute() {

            // Create a dialog to determine if the user wants to post their activity
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Confirm");
            alertDialog.setCancelable(false); // Might make it modal, idk check to be sure.
            alertDialog.setMessage(getString(R.string.completeActivityPrompt));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {

                        // Cancel the doInBackground task
                        public void onClick(DialogInterface dialog, int which) {
                            cancel(true);
                        }
                    });

            alertDialog.show();

        }
        // Send off the activity data to the server.
        /* Example POST in JSON
        {
             "username":"szook",
             "time_started":"2016-03-07T20:08:54",
             "duration":"01:20:34",
             "mileage":14.5765489456,
             "calories_burned":250,
             "exercise_type":"bike",
             "path":"0 0,1 1,2 2,3 3,4 4,5 5"
            }
         */
        @Override
        protected Void doInBackground(Void... params) {

            // Build the parameters for the activity via RequestParams
            // If we create a RecordActivityModel, we can use Gson to generate a JSON Object directly
            // from the RecordActivityModel object that contains the data for the Activity. We can then
            // manually add the username property to the Gson object and be done.
            // Currently we do it the old fashioned way since we dont have a model for record actiivty
            JSONObject recordActivityJSON = null;
            StringEntity jsonString = null;
            try{
                //TODO: need to figure out a way to store your activity and make it to json.
                // Convert the Activity to JSON then to parameters for the post activity.
                recordActivityJSON = getTestJSONObject();
                jsonString = new StringEntity(recordActivityJSON.toString());
            }
            catch(Exception ex){
                Log.i("JSON/Encode Exception:", ex.getMessage());
            }



            // Currently hardcoded the URL (using postByUrl). We will eventually be to the point where we just post
            // username/Activity and the util class will have the long base url name.
            HttpClientUtil.postByUrl(getContext(), defaultPostURL, jsonString, contentType,
                    new AsyncHttpResponseHandler(Looper.getMainLooper()) {

                        // Before the actual post happens.
                        @Override
                        public void onStart() {

                        }

                        // Here you received http 200, do whatever you want, it worked.
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {


                        }

                        // If it fails to post, you can issue some sort of alert dialog saying the error
                        // and writing the activity to file.
                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        }

                    });

            return null;
        }


        // This method gets executed after the doInBackground process finishes.
        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);


        }

        }

        // Possibly change this to a function that takes in a RecordActivityModel or data and
        // returns a json object representing it if we don't want to use Gson.
        private JSONObject getTestJSONObject() throws JSONException{
            JSONObject testObject = new JSONObject();
            testObject.put("username","szook");

            // Create dynamic timestamp
            TimeZone tz = TimeZone.getTimeZone(UTC);
            java.text.DateFormat df = new SimpleDateFormat(isoDateFormat);
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());
            testObject.put("time_started",nowAsISO);

            // Be sure to use Duration Objects when using Duration instead of just hardcoded string types
            // We can add utils to the duration class when needed and such.
            testObject.put("duration",new Duration("00:12:51").toString());

            // Use the primitive types Wrapper class when creating the JSON Object (it might be required)
            testObject.put("mileage", Double.valueOf(2.1));

            testObject.put("calories_burned", Integer.valueOf(241));

            testObject.put("exercise_type", "run");

            testObject.put("path","0 0, 1 2, 3 4, 5 6, 7 8, 9, 0");

            return testObject;
    }
}
