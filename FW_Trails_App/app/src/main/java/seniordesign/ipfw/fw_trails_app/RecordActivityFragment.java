package seniordesign.ipfw.fw_trails_app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

import java.io.InputStream;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.kml.KmlLayer;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class RecordActivityFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {
   private final String fragmentTitle = "Record Activity";
   private GoogleMap mMap;
   private Polyline line;
   Button startButton;
   Button pauseButton;
   Button resumeButton;
   Button finishButton;
   private LocationListener locationListener;
   private GoogleApiClient mGoogleApiClient;
   protected LocationRequest mLocationRequest;
   private Location mLastLocation;
   private boolean recording = false;
   private boolean firstCoordinate = false;
   private long lastLocationTime;
   private LatLng lastLocation;
   private long durationSinceLastLocation;
   private double currentSpeed;
   NumberFormat speedFormat = new DecimalFormat("##0.0");
   NumberFormat distanceFormat = new DecimalFormat("##0.000");
   double tempDistance;
   double totalDistance = 0.0;
   private TextView speed;
   private TextView distance;
   private double metersPerMile = 1609.34;
   private double secondsPerHour = 3600.0;
   private ArrayList<LatLng> coordinates = new ArrayList<>();

   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      RelativeLayout loadedRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_record_activity, container, false);
      startButton = (Button) loadedRelativeLayout.findViewById(R.id.startButton);
      pauseButton = (Button) loadedRelativeLayout.findViewById(R.id.pauseButton);
      resumeButton = (Button) loadedRelativeLayout.findViewById(R.id.resumeButton);
      finishButton = (Button) loadedRelativeLayout.findViewById(R.id.finishButton);
      buildGoogleApiClient();

      // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
      SupportMapFragment mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
      mSupportMapFragment.getMapAsync(this);

      startButton.setOnClickListener(startButtonListener);
      pauseButton.setOnClickListener(pauseButtonListener);
      resumeButton.setOnClickListener(resumeButtonListener);
      finishButton.setOnClickListener(finishButtonListener);
      // Of course you will want to faActivity and llLayout in the class and not this method to access them in the rest of
      // the class, just initialize them here

      // Content of previous onCreate() here
      // ...
      // Don't use this method, it's handled by inflater.inflate() above :
      // setContentView(R.layout.activity_layout);

      // The FragmentActivity doesn't contain the layout directly so we must use our instance of     LinearLayout :
      // loadedRelativeLayout.findViewById(R.id.someGuiElement);
      // Instead of :
      // findViewById(R.id.someGuiElement);
      return loadedRelativeLayout; // We must return the loaded Layout
   }

   protected synchronized void buildGoogleApiClient() {
      mGoogleApiClient = new GoogleApiClient.Builder(getContext())
              .addConnectionCallbacks(this)
              .addOnConnectionFailedListener(this)
              .addApi(LocationServices.API)
              .build();
      createLocationRequest();
   }

   protected void createLocationRequest() {
      mLocationRequest = new LocationRequest();
      mLocationRequest.setInterval(1000);
      mLocationRequest.setFastestInterval(100);
      mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
   }

   public void onConnected(Bundle connectionHint) {
      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
         mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
      }
      if (mLastLocation != null) {
      }
      startLocationUpdates();
   }

   protected void startLocationUpdates() {
      locationListener = new LocationListener() {
         public void onLocationChanged(Location location) {
            if(recording) {
               updateLocation(location);
            }
         }
      };
      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
         LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
      }
   }

   public void onConnectionSuspended(int n){

   }

   public void onConnectionFailed(ConnectionResult cr){

   }

   public String getTitle() {
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

   }

   private void updateLocation(Location location) {
      LatLng updatedLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if(firstCoordinate) {
            mMap.addMarker(new MarkerOptions().position(updatedLocation).title("Start Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            lastLocationTime = System.currentTimeMillis();
            lastLocation = updatedLocation;
        }
        else{
            durationSinceLastLocation = System.currentTimeMillis()-lastLocationTime;
            lastLocationTime = System.currentTimeMillis();
            tempDistance = SphericalUtil.computeDistanceBetween(lastLocation, updatedLocation)/metersPerMile;
            totalDistance += tempDistance;
            distance.setText(String.valueOf(distanceFormat.format(totalDistance)) + " miles");
            currentSpeed = tempDistance/metersPerMile/durationSinceLastLocation*1000*secondsPerHour;
            speed.setText(speedFormat.format(currentSpeed) + " mph");
            lastLocation = updatedLocation;
        }
        coordinates.add(updatedLocation);
      mMap.animateCamera(CameraUpdateFactory.newLatLng(updatedLocation));
        line.setPoints(coordinates);
        firstCoordinate = false;
   }

   /**
    *
    */
   private void addPolylineToMap() {
      line = mMap.addPolyline(new PolylineOptions()
              .width(10)
              .color(Color.BLUE));
   }

   /**
    *
    */
   private void addKMLLayerToMap() {
      InputStream is = getResources().openRawResource(R.raw.doc);
      try {
         KmlLayer layer = new KmlLayer(mMap, is, getContext());
         layer.addLayerToMap();
      } catch (org.xmlpull.v1.XmlPullParserException e) {
      } catch (java.io.IOException e) {
      }
   }

   private View.OnClickListener startButtonListener = new View.OnClickListener() {

      @Override
      public void onClick(View v) {
         startRecording();
      }
   };

   public void startRecording(){
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

   public void pauseRecording(){
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

   public void resumeRecording(){
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

   public void finishRecording(){
      resumeButton = (Button) getView().findViewById(R.id.resumeButton);
      finishButton = (Button) getView().findViewById(R.id.finishButton);
      resumeButton.setVisibility(View.GONE);
      finishButton.setVisibility(View.GONE);
      Log.i("Development", "finishRecording");
   }
}
