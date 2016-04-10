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

import android.app.Application;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import junit.framework.*;
import org.junit.Test;

import org.junit.*;


import org.junit.runner.RunWith;


import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

 // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
 @Rule
 public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

 // Open Nav Drawer before each test.
 @Before
 public void navDrawerOpen(){
  openDrawer();
  onView(withId(R.id.nav_drawerImage)).check(matches(isDisplayed()));
  onView(withText(R.string.navDrawer_accountDetails)).perform(click());
 }
 // Close Nav Drawer after each test
 @After
 public void navDrawerClose(){
  closeDrawer();
  onView(withId(R.id.nav_drawerImage))
          .check(matches(not(isDisplayed())));
 }

 // Ignore these tests until toolbar and nav drawer is made
 // Make sure the toolbar is displayed.
 @Test
 public void toolbarExists() {
  onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
 }

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