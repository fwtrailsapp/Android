package seniordesign.ipfw.fw_trails_app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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


public class RecordActivityFragment extends Fragment implements OnMapReadyCallback {
   private final String fragmentTitle = "Record Activity";
   private GoogleMap mMap;
   private Polyline line;
   private boolean recording = false;
   Button startStopButton;
   Button finishButton;
   private LocationListener locationListener;

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      FragmentActivity faActivity = (FragmentActivity) super.getActivity();
      // Replace LinearLayout by the type of the root element of the layout you're trying to load
      RelativeLayout loadedRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_record_activity, container, false);

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

   public String getTitle() {
      return fragmentTitle;
   }

   /**
    *
    */
   public void startStopRecording(View view) {
      startStopButton = (Button) getActivity().findViewById(R.id.startStopButton);
      finishButton = (Button) getActivity().findViewById(R.id.finishButton);
      if (recording == false) {
         recording = true;
         startStopButton.setText("Pause");
         finishButton.setVisibility(View.GONE);
         Log.i("Development", "Started recording");
      } else {
         recording = false;
         startStopButton.setText("Resume");
         finishButton.setVisibility(View.VISIBLE);
         Log.i("Development", "Paused recording");
      }
   }

   /**
    * @param view
    */
   public void finishRecording(View view) {

      Log.i("Development", "Stopped recording");
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
//        if(firstCoordinate) {
//            mMap.addMarker(new MarkerOptions().position(updatedLocation).title("Start Location")
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//            lastLocationTime = System.currentTimeMillis();
//            lastLocation = updatedLocation;
//        }
//        else{
//            durationSinceLastLocation = System.currentTimeMillis()-lastLocationTime;
//            lastLocationTime = System.currentTimeMillis();
//            tempDistance = SphericalUtil.computeDistanceBetween(lastLocation, updatedLocation)/metersPerMile;
//            totalDistance += tempDistance;
//            distance.setText(String.valueOf(distanceFormat.format(totalDistance)) + " miles");
//            currentSpeed = tempDistance/metersPerMile/durationSinceLastLocation*1000*secondsPerHour;
//            speed.setText(speedFormat.format(currentSpeed) + " mph");
//            lastLocation = updatedLocation;
//        }
//        coordinates.add(updatedLocation);
      mMap.animateCamera(CameraUpdateFactory.newLatLng(updatedLocation));
//        line.setPoints(coordinates);
//        firstCoordinate = false;
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
}
