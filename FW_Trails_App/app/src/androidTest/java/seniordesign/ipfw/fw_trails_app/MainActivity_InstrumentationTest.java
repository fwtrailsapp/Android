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
   private final String reportTo311Text = "Report a problem to 311";

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

   @Test
   public void report311Dialer(){

      onView(withText(reportTo311Text)).check(matches(isDisplayed()));
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


