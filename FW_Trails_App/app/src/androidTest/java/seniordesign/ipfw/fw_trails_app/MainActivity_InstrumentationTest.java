package seniordesign.ipfw.fw_trails_app;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.RunWith;
//Espresso Imports
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import android.support.test.espresso.ViewInteraction;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;




/**
 * Created by Jaron on 1/23/2016.
 * Design Doc: https://drive.google.com/drive/u/1/folders/0B46qah_2e5yKbzdMbVNxUDNPeE0
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivity_InstrumentationTest {


   private final String accountDetailsText = "Account Details";


   // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
   @Rule
   public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

   // Open Nav Drawer before each test.
   @Before
   public void navDrawerOpen(){
      openDrawer();
      onView(withId(R.id.nav_drawerImage)).check(matches(isDisplayed()));

   }
   // Close Nav Drawer after each test
   @After
   public void navDrawerClose(){
      closeDrawer();
      onView(withId(R.id.nav_drawerImage))
              .check(matches(not(isDisplayed())));
   }

   @Test
   public void accountDetailsVerification(){
      onView(withText(R.string.navDrawer_accountDetails)).check(matches(withText(accountDetailsText)));
   }

   // Select a nav drawer item.
   @Test
   public void clickAccountDetails() {
      onView(withText(R.string.navDrawer_accountDetails)).perform(click());
   }

   @Test
   public void clickAccountStatistics() {
      onView(withText(R.string.navDrawer_accountStatistics)).perform(click());
   }

   @Test
    public void clickStaticTrailMap() {
      onView(withText(R.string.navDrawer_trailMap)).perform(click());
   }

   @Test
    public void clickAchievements() {
      onView(withText(R.string.navDrawer_achievements)).perform(click());
   }

   @Test
    public void clickActivityHistory() {
      onView(withText(R.string.navDrawer_activityHistory)).perform(click());
   }

   // Handle the record Activity click after we navigate upwards.
   @Ignore
    public void clickRecordActivity() {
      //onView(allOf(withId(R.id.drawerItemNameTextView), hasSibling(withText("test"))).perform(click()));
      onView(withText(R.string.navDrawer_recordActivity)).perform(click());
   }


   //Still need to check if an item has been clicked.
   // Check out http://stackoverflow.com/questions/32135157/espresso-check-if-navigationdrawer-list-item-is-selected

   // Opens the Navigation Drawer
   private void openDrawer()
   {
      onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
   }

   // Closes the Navigation Drawer
   private void closeDrawer(){
      onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
   }
}


