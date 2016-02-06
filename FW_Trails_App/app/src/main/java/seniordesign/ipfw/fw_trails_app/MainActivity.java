package seniordesign.ipfw.fw_trails_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.kml.KmlLayer;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback {

   private final String mainActivityTitle = "Record Activity";
   private GoogleMap mMap;
   private Polyline line;
   private boolean recording = false;
   Button startStopButton;
   Button finishButton;
   private LocationListener locationListener;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
              .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      getSupportActionBar().setTitle(mainActivityTitle);
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.setDrawerListener(toggle);
      toggle.syncState();

      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);
   }

   @Override
   public void onBackPressed() {
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      if (drawer.isDrawerOpen(GravityCompat.START)) {
         drawer.closeDrawer(GravityCompat.START);
      } else {
         super.onBackPressed();
      }
   }


   @SuppressWarnings("StatementWithEmptyBody")
   @Override
   public boolean onNavigationItemSelected(MenuItem item) {
      // Handle navigation view item clicks here.
      int id = item.getItemId();

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
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

      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
         public void onStatusChanged(String provider, int status, Bundle extras) {}

         public void onProviderEnabled(String provider) {}

         public void onProviderDisabled(String provider) {}
      };

   }

   private void updateLocation(Location location)
   {
      LatLng updatedLocation = new LatLng(location.getLatitude(),location.getLongitude());
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
         KmlLayer layer = new KmlLayer(mMap, is, getApplicationContext());
         layer.addLayerToMap();
      } catch (org.xmlpull.v1.XmlPullParserException e) {
      } catch (java.io.IOException e) {
      }
   }
}
