package seniordesign.ipfw.fw_trails_app;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   private final String exitDialogMessage = "Are you sure you want to exit?";
   private final String exitDialogTitle = "Confirm";
   private final String YES = "YES";
   private final String NO = "NO";
   private final String mainActivityTitle = "Record Activity";
   AlertDialog.Builder builder;
   private boolean viewIsAtHome;  // Record Activity is the Home View.

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.setDrawerListener(toggle);
      toggle.syncState();

      // Add a backstack listener for when the backstack changes.
      // Also updates the app bar titel and nav drawer with the respective new fragment.
      setupBackStackListener();

      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);
      displayView(R.id.nav_recordActivity);
   }

   // Method creates a backstack listener and keeps the nav drawer up to date with what
   // current fragment is displayed.
   private void setupBackStackListener() {
      getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

         @Override
         public void onBackStackChanged() {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.frameLayoutContent);
            if (f != null) {
               updateTitleAndDrawer(f);
            }

         }
      });
   }

   // Updates the app bar title and nav drawer selected item per the fragment passed in.
   private void updateTitleAndDrawer(Fragment fragment) {
      String fragClassName = fragment.getClass().getName();
      NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

      if (fragClassName.equals(AccountDetailsFragment.class.getName())) {
         if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(((AccountDetailsFragment) fragment).getTitle());
         }
         //set selected item position, etc
         navView.setCheckedItem(R.id.nav_accountDetails);
      } else if (fragClassName.equals(AccountStatisticsFragment.class.getName())) {
         if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(((AccountStatisticsFragment) fragment).getTitle());
         }
         //set selected item position, etc
         navView.setCheckedItem(R.id.nav_accountStatistics);
      } else if (fragClassName.equals(AchievementsFragment.class.getName())) {
         if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(((AchievementsFragment) fragment).getTitle());
         }
         //set selected item position, etc
         navView.setCheckedItem(R.id.nav_achievements);
      } else if (fragClassName.equals(ActivityHistoryFragment.class.getName())) {
         if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(((ActivityHistoryFragment) fragment).getTitle());
         }
         //set selected item position, etc
         navView.setCheckedItem(R.id.nav_activityHistory);
      } else if (fragClassName.equals(RecordActivityFragment.class.getName())) {
         if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(((RecordActivityFragment) fragment).getTitle());
         }
         //set selected item position, etc
         navView.setCheckedItem(R.id.nav_recordActivity);
      } else if (fragClassName.equals(TrailMapFragment.class.getName())) {
         if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(((TrailMapFragment) fragment).getTitle());
         }
         //set selected item position, etc
         navView.setCheckedItem(R.id.nav_trailMap);
      }
   }

   // This method replaces the current fragment with a fragment on the backstack
   private void replaceFragment(Fragment fragment) {
      String backStateName = fragment.getClass().getName();
      String fragmentTag = backStateName;

      FragmentManager manager = getSupportFragmentManager();
      boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

      if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
         FragmentTransaction ft = manager.beginTransaction();
         ft.replace(R.id.frameLayoutContent, fragment, fragmentTag);
         ft.addToBackStack(backStateName);
         ft.commit();
      }
   }

   // Always have at least one item on the backstack, so if we are at one,
   // we want to exit the application, else let Android handle the back op.
   @Override
   public void onBackPressed() {
      if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
         finish();
      } else {
         super.onBackPressed();
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
            title = ((AccountStatisticsFragment) fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_achievements:
            fragment = new AchievementsFragment();
            title = ((AchievementsFragment) fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_accountDetails:
            fragment = new AccountDetailsFragment();
            title = ((AccountDetailsFragment) fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_trailMap:
            fragment = new TrailMapFragment();
            title = ((TrailMapFragment) fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_activityHistory:
            fragment = new ActivityHistoryFragment();
            title = ((ActivityHistoryFragment) fragment).getTitle();
            viewIsAtHome = false;
            break;

         case R.id.nav_recordActivity:
            fragment = new RecordActivityFragment();
            title = ((RecordActivityFragment) fragment).getTitle();
            viewIsAtHome = true;
            break;

         case R.id.nav_exit:
            initializeExitAlert();
            AlertDialog alert = builder.create();
            alert.show();
            title = currentTitle;
            break;
      }

      if(fragment != null){
         replaceFragment(fragment);
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

      displayView(item.getItemId());

      return true;
   }

   // The Alert for Exit Navigation drawer Item
   private void initializeExitAlert() {
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
