package seniordesign.ipfw.fw_trails_app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        implements NavigationView.OnNavigationItemSelectedListener {

   private final String exitDialogMessage = "Are you sure you want to exit?";
   private final String exitDialogTitle = "Confirm";
   private final String YES = "YES";
   private final String NO = "NO";
   private MenuItem currentMenuItem = null;
   private final String mainActivityTitle = "Record Activity";
   AlertDialog.Builder builder;
   private boolean viewIsAtHome;  // Record Activity is the Home View.


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

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
      displayView(R.id.nav_recordActivity);
   }

   @Override
   public void onBackPressed() {
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      if (drawer.isDrawerOpen(GravityCompat.START)) {
         drawer.closeDrawer(GravityCompat.START);
      }
      if (!viewIsAtHome) { //if the current view is not the Home fragment
         displayView(R.id.nav_recordActivity); //display the Home fragment
      } else {
         moveTaskToBack(true);  //If view is in Record Activity fragment, exit application
      }
   }

   // Determines what fragment to display based on the viewId passed in
   // from the onNavigationItemSelected method.
   public void displayView(int viewId) {

      Fragment fragment = null;

      //todo: figure out a way to hold current toolbar title if user cancels exit dialog.
      // Set the default title to the record activity title.
      String title = mainActivityTitle;
      String currentTitle = (String) getSupportActionBar().getTitle();
      switch (viewId) {
         case R.id.nav_accountStatistics:
            fragment = new AccountStatisticsFragment();
            title  = ((AccountStatisticsFragment)fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_achievements:
            fragment = new AchievementsFragment();
            title = ((AchievementsFragment)fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_accountDetails:
            fragment = new AccountDetailsFragment();
            title = ((AccountDetailsFragment)fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_trailMap:
            fragment = new TrailMapFragment();
            title = ((TrailMapFragment)fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_activityHistory:
            fragment = new ActivityHistoryFragment();
            title = ((ActivityHistoryFragment)fragment).getTitle();
            viewIsAtHome = false;
            break;

        case R.id.nav_recordActivity:
            fragment = new RecordActivityFragment();
            title = ((RecordActivityFragment)fragment).getTitle();
            viewIsAtHome = true;
            break;

         case R.id.nav_exit:
            initializeExitAlert();
            AlertDialog alert = builder.create();
            alert.show();
            title = currentTitle;
            break;
      }

      // Changes the current fragment
      if (fragment != null) {
         FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
         ft.replace(R.id.frameLayoutContent, fragment);
         ft.commit();
      }

      // set the toolbar title
      if (getSupportActionBar() != null) {
         getSupportActionBar().setTitle(title);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
   }

   // Triggers when a Navigation Drawer Item is selected.
   @Override
   public boolean onNavigationItemSelected(MenuItem item) {

      if(currentMenuItem == null)
      {
         currentMenuItem = item;
      }

      displayView(item.getItemId());

      // Don't select Exit Item since it doesn't relate to the current fragment being displayed
      if(item.getTitle().equals("Exit"))
      {
         currentMenuItem.setChecked(true);
      }
      else
      {
         currentMenuItem = item;
      }

      return true;
   }


   // The Alert for Exit Navigation drawer Item
   private void initializeExitAlert(){
      builder = new AlertDialog.Builder(this);

      // Set the properties
      builder.setTitle(exitDialogTitle);
      builder.setMessage(exitDialogMessage);

      // Create the yes and no listeners
      builder.setPositiveButton(YES, new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface dialog, int which) {
            // Dismiss the dialog and exit the application
            dialog.dismiss();
            finish();
         }

      });

      builder.setNegativeButton(NO, new DialogInterface.OnClickListener() {

         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
         }
      });
   }
}
