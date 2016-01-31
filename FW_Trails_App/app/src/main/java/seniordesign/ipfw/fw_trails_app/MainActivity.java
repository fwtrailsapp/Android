package seniordesign.ipfw.fw_trails_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

   private Toolbar toolbar;
   private DrawerLayout mDrawer;
   private NavigationView nvDrawer;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
         }
      });

      // Grab the drawer view
      mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

      // Find our drawer view
      nvDrawer = (NavigationView) findViewById(R.id.nvView);

      // Setup drawer view
      setupDrawerContent(nvDrawer);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }
   private void setupDrawerContent(NavigationView navigationView) {
      navigationView.setNavigationItemSelectedListener(
              new NavigationView.OnNavigationItemSelectedListener() {
                 @Override
                 public boolean onNavigationItemSelected(MenuItem menuItem) {
                    selectDrawerItem(menuItem);
                    return true;
                 }
              });
   }

   public void selectDrawerItem(MenuItem menuItem) {
      // Create a new fragment and specify the planet to show based on
      // position
      Fragment fragment = null;

      Class fragmentClass;
      switch(menuItem.getItemId()) {
         case R.id.nav_accountStatistics:
            fragmentClass = AccountStatisticsFragment.class;
            break;
         case R.id.nav_accountDetails:
           // fragmentClass = AccountDetailsFragment.class;
            fragmentClass = AccountStatisticsFragment.class;
            break;
         case R.id.nav_achievements:
           // fragmentClass = AchievementsFragment.class;
            fragmentClass = AccountStatisticsFragment.class;
            break;
         case R.id.nav_activityHistory:
           // fragmentClass = ActivityHistoryFragment.class;
            fragmentClass = AccountStatisticsFragment.class;
            break;
         case R.id.nav_trailMap:
            //fragmentClass = TrailMapFragment.class;
            fragmentClass = AccountStatisticsFragment.class;
            break;
         case R.id.nav_exit:
            //fragmentClass = ExitFragment.class;
            fragmentClass = AccountStatisticsFragment.class;
            break;
         case R.id.nav_recordActivity:
            //fragmentClass = RecordActivityFragment.class;
            fragmentClass = AccountStatisticsFragment.class;
            break;
         default:
            fragmentClass = AccountStatisticsFragment.class;
      }

      try {
         fragment = (Fragment) fragmentClass.newInstance();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Insert the fragment by replacing any existing fragment
      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

      // Highlight the selected item, update the title, and close the drawer
      menuItem.setChecked(true);
      setTitle(menuItem.getTitle());
      mDrawer.closeDrawers();
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // The action bar home/up action should open or close the drawer.
      switch (item.getItemId()) {
         case android.R.id.home:
            mDrawer.openDrawer(GravityCompat.START);
            return true;
      }

      return super.onOptionsItemSelected(item);
   }

   @Override
   protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
   }
   public void goToMap(View view) {
      Intent intent = new Intent(this, MapsActivity.class);
      startActivity(intent);
   }

}
