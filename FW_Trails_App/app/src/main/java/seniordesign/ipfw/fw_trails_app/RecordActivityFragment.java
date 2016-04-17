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

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.kml.KmlLayer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RecordActivityFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final String fragmentTitle = "Record Activity";

    RecordActivityModel recordActivityModel;

    //Map utilities
    private GoogleMap mMap;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationListener locationListener;
    protected LocationRequest mLocationRequest;
    Marker startMarker;
    Marker finishMarker;
    ArrayList<Polyline> lines = new ArrayList<>();
    Polyline line;

    //Recoding utilities
    private boolean recording = false;
    private boolean firstCoordinate = false;
    private LatLng lastLocation;

    //Calculation utilities
    private double metersPerMile = 1609.34;
    private int secondsPerHour = 3600;
    private long lastLocationTime;
    private long durationSinceLastLocation;
    private double currentSpeed;
    NumberFormat distanceFormat = new DecimalFormat("#0.00");
    NumberFormat speedFormat = new DecimalFormat("#0.00");
    NumberFormat calorieFormat = new DecimalFormat("##0");
    double tempDistance;

    GenderOptions gender = GenderOptions.Male;//
    int weight = 82;//in kilograms
    int height = 183;//in centemeters
    int age = 21;//
    double BMR = 13.75*weight + 5*height - 6.76*age + 66;//

    //View utilities
    private static View view;
    private TextView speed;
    private TextView distance;
    private TextView duration;
    private TextView calories;
    private int caloriesInt = 0;
    Button startButton;
    Button pauseButton;
    Button resumeButton;
    Button finishButton;
    Button clearButton;

    //Timer
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int secs = 0;
    int mins = 0;
    int hours = 0;
    int milliseconds = 0;
    Handler handler = new Handler();

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
        clearButton = (Button) view.findViewById(R.id.clearButton);

        buildGoogleApiClient();

        SupportMapFragment mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(this);

        startButton.setOnClickListener(startButtonListener);
        pauseButton.setOnClickListener(pauseButtonListener);
        resumeButton.setOnClickListener(resumeButtonListener);
        finishButton.setOnClickListener(finishButtonListener);
        clearButton.setOnClickListener(clearButtonListener);

//        uploadLocalActivities();

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
        mLocationRequest.setFastestInterval(900);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        speed = (TextView) getView().findViewById(R.id.speed);
        distance = (TextView) getView().findViewById(R.id.distance);
        duration = (TextView) getView().findViewById(R.id.duration);
        calories = (TextView) getView().findViewById(R.id.calories);

        LatLng fortWayne = new LatLng(41.0856087, -85.1397336);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(fortWayne, 10.5f), 500, null);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setIndoorEnabled(false);

        addKMLLayerToMap();
        addPolylineToMap();
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

    public Runnable updateTimer = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;

            updatedtime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedtime / 1000);
            hours = secs / secondsPerHour;
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);
            duration.setText("Duration: " + hours + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs));
            handler.postDelayed(this, 0);
        }

    };

    private void updateLocation(Location location) {
        LatLng updatedLocation = new LatLng(location.getLatitude(), location.getLongitude());
        recordActivityModel.addLatLng(updatedLocation);
        if (firstCoordinate) {
            captureFirstCoordinate(updatedLocation);
            firstCoordinate = false;
        } else {
            captureLaterCoordinate(updatedLocation);
            lastLocation = updatedLocation;
        }
        Log.i("Development", "updateLocation");
    }

    private void captureFirstCoordinate(LatLng updatedLocation) {
        startMarker = mMap.addMarker(new MarkerOptions().position(updatedLocation).title("Start Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(updatedLocation, 18.0f), 500, null);
        lastLocationTime = System.currentTimeMillis();
        lastLocation = updatedLocation;
    }

    private void captureLaterCoordinate(LatLng updatedLocation) {
        line.setPoints(recordActivityModel.getCurrentLatLngs());
        mMap.animateCamera(CameraUpdateFactory.newLatLng(updatedLocation));
        Double caloriesBurned = BMR * recordActivityModel.getExerciseType().getMETValue() * recordActivityModel.getDurationInSeconds() / secondsPerHour / 25;
        caloriesInt = caloriesBurned.intValue();
        recordActivityModel.getDuration().tickInt();
        durationSinceLastLocation = System.currentTimeMillis() - lastLocationTime;
        lastLocationTime = System.currentTimeMillis();
        tempDistance = SphericalUtil.computeDistanceBetween(lastLocation, updatedLocation) / metersPerMile;
        recordActivityModel.addDistance(tempDistance);

        distance.setText("Distance: " + String.valueOf(distanceFormat.format(recordActivityModel.getTotalDistance())) + " mi");
        calories.setText("Calories: " + String.valueOf(calorieFormat.format(caloriesBurned)));
        // Duration is updated by runnable
        currentSpeed = tempDistance / durationSinceLastLocation * 1000 * secondsPerHour;
        speed.setText("Speed: " + speedFormat.format(currentSpeed) + " mph");
    }

    public void selectActivityType() {
        CharSequence[] exerciseTypes = new ExerciseTypes().getExerciseTypes();
        AlertDialog.Builder builder = new AlertDialog.Builder(getChildFragmentManager().findFragmentById(R.id.map).getContext());
        builder.setTitle("Select Exercise Type");
        builder.setItems(exerciseTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        recordActivityModel = new RecordActivityModel(new BikeExerciseType());
                        startRecording();
                        break;
                    case 1:
                        recordActivityModel = new RecordActivityModel(new RunExerciseType());
                        startRecording();
                        break;
                    case 2:
                        recordActivityModel = new RecordActivityModel(new WalkExerciseType());
                        startRecording();
                        break;
                    default:
                        recordActivityModel = new RecordActivityModel(new WalkExerciseType());
                        startRecording();
                        break;
                }
            }
        });
        builder.show();
    }

    private void askForGPS() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("GPS");
        alertDialog.setMessage("Please turn GPS on for accurate results.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private View.OnClickListener startButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            clearActivity();
            LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE );
            boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!statusOfGPS) {
                askForGPS();
            }
            else {
                selectActivityType();
            }
        }
    };

    public void startRecording() {
        recording = true;
        firstCoordinate = true;

        starttime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimer, 0);

        startButton = (Button) view.findViewById(R.id.startButton);
        pauseButton = (Button) view.findViewById(R.id.pauseButton);
        startButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        Log.i("Development", "startRecording");
    }

    private View.OnClickListener pauseButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            pauseRecording();

            timeSwapBuff += timeInMilliseconds;
            handler.removeCallbacks(updateTimer);
        }
    };

    public void pauseRecording() {
        lines.add(line);
        recordActivityModel.flushCurrentPath();
        recording = false;
        pauseButton = (Button) view.findViewById(R.id.pauseButton);
        resumeButton = (Button) view.findViewById(R.id.resumeButton);
        finishButton = (Button) view.findViewById(R.id.finishButton);
        pauseButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.VISIBLE);
        Log.i("Development", "pauseRecording");
    }

    private View.OnClickListener resumeButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            resumeRecording();

            LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE );
            boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!statusOfGPS) {
                askForGPS();
            }

            starttime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
        }
    };

    public void resumeRecording() {
        addPolylineToMap();

        recording = true;
        pauseButton = (Button) view.findViewById(R.id.pauseButton);
        resumeButton = (Button) view.findViewById(R.id.resumeButton);
        finishButton = (Button) view.findViewById(R.id.finishButton);
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
        finishMarker = mMap.addMarker(new MarkerOptions().position(lastLocation).title("Stop Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Log.i("Development", "finishRecording");

        sendDataToServerDialog();
    }

    private View.OnClickListener clearButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            clearRecording();
        }
    };

    public void clearRecording() {
        clearActivity();
        startButton = (Button) view.findViewById(R.id.startButton);
        clearButton = (Button) view.findViewById(R.id.clearButton);
        startButton.setVisibility(View.VISIBLE);
        clearButton.setVisibility(View.GONE);
        Log.i("Development", "clearRecording");
    }

    public void displaySummary(){
        String message = "";
        message += "Exercise Type: " + recordActivityModel.getExerciseType().getExerciseType();
        message += "\nDuration: " + recordActivityModel.getDuration().toString();
        message += "\nDistance: " + String.valueOf(distanceFormat.format(recordActivityModel.getTotalDistance())) + " mi";
        message += "\nCalories: " + caloriesInt;

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Activity Summary");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        resumeButton = (Button) view.findViewById(R.id.resumeButton);
                        finishButton = (Button) view.findViewById(R.id.finishButton);
                        clearButton = (Button) view.findViewById(R.id.clearButton);
                        resumeButton.setVisibility(View.GONE);
                        finishButton.setVisibility(View.GONE);
                        clearButton.setVisibility(View.VISIBLE);
                    }
                });
        alertDialog.show();
    }

    private void clearActivity() {
        startButton = (Button) view.findViewById(R.id.startButton);
        startButton.setVisibility(View.VISIBLE);

        if(startMarker != null){
            startMarker.remove();
        }
        if(finishMarker != null) {
            finishMarker.remove();
        }
        for(int i = 0; i < lines.size(); i++){
            lines.get(i).remove();
        }
        addPolylineToMap();

        starttime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedtime = 0L;
        secs = 0;
        mins = 0;
        milliseconds = 0;
        handler.removeCallbacks(updateTimer);
        duration.setText("0:00:00");

        distance.setText("Distance: 0.00 mi");
        calories.setText("Calories: 0");
        duration.setText("Duration: 0:00:00");
        speed.setText("Speed: 0.00 mph");
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
        String FILENAME = "RecordedActivity " + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

        StringBuilder output = new StringBuilder();
        //Username
        output.append("username");
        output.append("\nggrimm");
        //Start timestamp
        output.append("\ntime_started");
        output.append("\n" + recordActivityModel.getStartTimestamp());
        //Duration
        output.append("\nduration");
        output.append("\n" + recordActivityModel.getDuration());
        //Distance
        output.append("\nmileage");
        output.append("\n" + Double.valueOf(recordActivityModel.getTotalDistance()));
        //Calories burned
        output.append("\ncalories_burned");
        output.append("\n" + Integer.valueOf(caloriesInt));
        //Exercise type
        output.append("\nexercise_type");
        output.append("\n" + recordActivityModel.getExerciseType().getExerciseType());
        //Path
        output.append("\npath");
        output.append("\n" + getCoordinates());

        try {
            FileOutputStream fos = getContext().openFileOutput(FILENAME, getContext().MODE_PRIVATE);
            fos.write(output.toString().getBytes());
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

        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);
        }catch (Exception e) {
            e.printStackTrace();
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
        }

        try
        {
            fis.getChannel().position(0);
        }
        catch (Exception e) {e.printStackTrace();}

        String[] output = new String[lineCounter];
        String line;
        int counter = 0;
        try{
            for (int i = 0; i < lineCounter; i++){
                output[i] = br.readLine();
                Log.i("Development", i + " " + output[i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String printMe = "";
        for(int i = 0; i < output.length; i++){
            printMe += output[i];
        }

        Log.i("Development", "openfile");
    }

    private void sendDataToServerDialog() {
        // Create a dialog to determine if the user wants to post their activity
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Confirm");
        // If modal doesn't work, garbage collect the activity.
        alertDialog.setCancelable(false); // Might make it modal, idk check to be sure.
        alertDialog.setMessage(getString(R.string.completeActivityPrompt));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Attempt to send to the server
                        RecordActivityController recordActivityTask = new RecordActivityController();
                        recordActivityTask.execute();

                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Discard",
                new DialogInterface.OnClickListener() {

                    // Cancel the doInBackground task
                    public void onClick(DialogInterface dialog, int which) {
                        displaySummary();
                    }
                });

        alertDialog.show();
    }

    private void cannotSendActivtyDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Network Failure");

        // Modal settings are set, user must click ok before the dialog can be dismissed
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Cannot connect to internet. Your activity will be saved later.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
        Log.i("Development", "cannotSendActivityDialog");
    }

    private void activitySavedDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Save Success");

        // Modal settings are set, user must click ok before the dialog can be dismissed
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Your activity was saved to your account.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
        Log.i("Development", "cannotSendActivityDialog");
    }

    /*
  The RecordActivityController class.

  This class extends the AsyncTask to spawn off a new thread that posts activities to
   the web server. The HttpClientUtil class is the class that actually sends off the request using
  a Synchronous Http handler.

  doInBackground:
  We get the finished activity, transform it to JSON, and send it to the server in the background.

  The onPostExecute method is what updates the actual ListViewAdapter
   */
    private class RecordActivityController extends AsyncTask<Void, Void, Void> {


        private final String contentType = "application/json";

        //TODO: This is where the dialog "Are you sure you wish to complete this activity" goes.
        // See http://stackoverflow.com/questions/6039158/android-cancel-async-task
        @Override
        protected void onPreExecute() {
            displaySummary();
        }



        // Send off the activity data to the server.
        @Override
        protected Void doInBackground(Void... params) {

            // Build the parameters for the activity via JSON
            // If we create a RecordActivityModel, we can use Gson to generate a JSON Object directly
            // from the RecordActivityModel object that contains the data for the Activity. We can then
            // manually add the username property to the Gson object and be done.
            // Currently we do it the old fashioned way since we dont have a model for record actiivty
            JSONObject recordActivityJSON = null;
            StringEntity jsonString = null;

            try{
                //TODO: need to figure out a way to store your activity and make it to json.
                // Convert the Activity to JSON then to parameters for the post activity.
                recordActivityJSON = createActivityJSONObject();
                jsonString = new StringEntity(recordActivityJSON.toString());


                //Upload activities that were recorded offline and saved to a file
//                previousActivityJSON = uploadLocalActivities();

            }
            catch(Exception ex){
                Log.i("JSON/Encode Exception:", ex.getMessage());
            }



            // Currently hardcoded the URL (using postByUrl). We will eventually be to the point where we just post
            // username/Activity and the util class will have the long base url name.
            HttpClientUtil.postByUrl(getContext(), HttpClientUtil.BASE_URL_ACTIVITY, jsonString, contentType,
                    new AsyncHttpResponseHandler(Looper.getMainLooper()) {

                        // Before the actual post happens.
                        @Override
                        public void onStart() {

                        }

                        // Here you received http 200, do whatever you want, it worked.
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    activitySavedDialog();
                                }
                            });
                        }

                        // If it fails to post, you can issue some sort of alert dialog saying the error
                        // and writing the activity to file.
                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    cannotSendActivtyDialog();
                                }
                            });

                            String filename = sendToFile();
                            File file = getFile(filename);
                            if(file == null){
                                Log.i("Development", "Returned file is null");
                            }
                            else{
                                openFile(file);
                            }
                        }

                    });

            //Upload old activities
            boolean uploadingLocalActivities = true;
            while(uploadingLocalActivities){
                uploadingLocalActivities = false;

                String[] fileList = getContext().fileList();
                for(int i = 0; i < fileList.length; i++){
                    if(fileList[i].contains("RecordedActivity")){
                        uploadingLocalActivities = true;

                        String filename = fileList[i];
                        final File file = getFile(filename);

                        //Read everything in from the file
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);

                        String temp;
                        int lineCounter = 0;
                        try {
                            while ((temp = br.readLine()) != null) {
                                lineCounter++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            fis.getChannel().position(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String[] output = new String[lineCounter];
                        String line;
                        int counter = 0;
                        try {
                            for (int k = 0; k < lineCounter; k++) {
                                output[k] = br.readLine();
                                Log.i("Development", output[k]);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Put all of the file contents into a JSONObject
                        try {
                            JSONObject activityJSONObject = new JSONObject();
                            activityJSONObject.put(output[0], output[1]);

                            //Get start timestamp
                            activityJSONObject.put(output[2], output[3]);

                            // Be sure to use Duration Objects when using Duration instead of just hardcoded string types
                            // We can add utils to the duration class when needed and such.
                            activityJSONObject.put(output[4], output[5]);

                            // Use the primitive types Wrapper class when creating the JSON Object (it might be required)
                            activityJSONObject.put(output[6], output[7]);

                            activityJSONObject.put(output[8], output[9]);

                            activityJSONObject.put(output[10], output[11]);

                            activityJSONObject.put(output[12], output[13]);

                            StringEntity jsonStringLocal = null;
                            try{
                                jsonStringLocal = new StringEntity(activityJSONObject.toString());
                            }
                            catch(Exception e){

                            }

                            HttpClientUtil.postByUrl(getContext(), HttpClientUtil.BASE_URL_ACTIVITY, jsonStringLocal, contentType,
                                    new AsyncHttpResponseHandler(Looper.getMainLooper()) {

                                        // Before the actual post happens.
                                        @Override
                                        public void onStart() {

                                        }

                                        // Here you received http 200, do whatever you want, it worked.
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                            Log.i("Development", "OFFLINE ACTIVITY UPLOADED!!!!!!!!!!");
                                            file.delete();
                                        }

                                        // If it fails to post, you can issue some sort of alert dialog saying the error
                                        // and writing the activity to file.
                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                                            Log.i("Development", "OFFLINE ACTIVITY FAILED><><><><><><><");
                                        }
                                    });

                        } catch (JSONException e) {
                            Log.e("Error", "JSONObject error");
                        }
                    }
                }
            }

            return null;
        }


        // This method gets executed after the doInBackground process finishes.
        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);


        }

        }

        /* Example Activity POST in JSON
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
        // Possibly change this to a function that takes in a RecordActivityModel or data and
        // returns a json object representing it if we don't want to use Gson.
        private JSONObject createActivityJSONObject() throws JSONException{
            JSONObject activityJSONObject = new JSONObject();
            activityJSONObject.put("username","ggrimm");

            //Get start timestamp
            activityJSONObject.put("time_started",recordActivityModel.getStartTimestamp());

            // Be sure to use Duration Objects when using Duration instead of just hardcoded string types
            // We can add utils to the duration class when needed and such.
            activityJSONObject.put("duration",recordActivityModel.getDuration());

            // Use the primitive types Wrapper class when creating the JSON Object (it might be required)
            activityJSONObject.put("mileage", Double.valueOf(recordActivityModel.getTotalDistance()));

            activityJSONObject.put("calories_burned", Integer.valueOf(caloriesInt));

            activityJSONObject.put("exercise_type", recordActivityModel.getExerciseType().getExerciseType());

            activityJSONObject.put("path",getCoordinates());

            return activityJSONObject;
    }

    // Gets the latitude and longitude coordinates and puts it in the form of
    // "Lat Long, Lat Long, Lat Long"
    private String getCoordinates() {
        StringBuilder output = new StringBuilder();

        ArrayList<LatLng> allCoordinates = recordActivityModel.getAllLatLngs();
        for(int i = 0; i < allCoordinates.size(); i++){

            output.append(allCoordinates.get(i).latitude);
            output.append(" ");
            output.append(allCoordinates.get(i).longitude);

            // Don't append comma for the last coordinate points
            if(i+1 < allCoordinates.size()){
                output.append(", ");
            }
        }

        return output.toString();
    }
}