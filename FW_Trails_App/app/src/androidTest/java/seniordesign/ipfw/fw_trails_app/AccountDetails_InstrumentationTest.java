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

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


/**
 * Created by Jaron on 1/23/2016.
 * Design Doc: https://drive.google.com/drive/u/1/folders/0B46qah_2e5yKbzdMbVNxUDNPeE0
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AccountDetails_InstrumentationTest {

   private final String toolbarTitle = "Account Details";
   private final String acctInfoTitleText = "Account Info";
   private final String testUsernameFieldText = "Username";
   private final String userTest = "UserTest";
   private final String passwordText = "Password";
   private final String resetPasswordText = "Reset Password";
   private final String heightText = "Height";
   private final String weightText = "Weight";
   private final String testWeight = "188 lbs";
   private final String testHeight = "72 in";
   private final String birthYearText = "Birth Year";
   private final String testBirthYear = "1994";
   private final String[] genderSpinner = {"Male","Female","Prefer not to disclose"};
   private final String sexText = "Sex";
   private final String acceptText = "Accept";
   private final String cancelText = "Cancel";
   
   
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

   // tests the toolbar's title which inherits a text view so we look for anything that
   // is the parent of the toolbar that is also a TextView and see if it matches with our desired
   // title. The title is set in the onCreate method.
   // source: http://www.captechconsulting.com/blogs/introduction-to-espresso
   @Test
   public void accountDetailsToolbarTitleVerification() {
      onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
              .check(matches(withText(toolbarTitle)));
   }

   @Test
   //Tests if the Account Info text appears within the fragment
   public void accountDetailsInfoTextVerification(){
      onView(withText(acctInfoTitleText)).check(matches(isDisplayed()));
   }
   @Test
   //Tests if the Account username text appears within the fragment
   public void accountDetailsUsernameTextVerification(){
      onView(withText(testUsernameFieldText)).check(matches(isDisplayed()));
   }
   @Test
   //Tests if the fake user text appears within the fragment
   public void accountDetailsTestUsernameTextVerification(){
      onView(withText(userTest)).check(matches(isDisplayed()));
   }
   @Test
   //Tests if the password text appears within the fragment
   public void accountDetailsPasswordTextVerification(){
      onView(withText(passwordText)).check(matches(isDisplayed()));
   }
   @Test
   //Tests if the password text appears within the fragment
   public void accountDetailsPasswordResetTextVerification(){
      onView(withText(resetPasswordText)).check(matches(isDisplayed()));
   }

   @Test
   //Tests if the height text appears within the fragment
   public void accountDetailsHeightTextVerification(){
      onView(withText(heightText)).check(matches(isDisplayed()));
   }
   @Test
   //Tests if the height text appears within the fragment
   public void accountDetailsTestHeightTextVerification(){
      onView(withHint(testHeight)).check(matches(isDisplayed()));
   }
   @Test
   //Tests if the weight text appears within the fragment
   public void accountDetailsWeightTextVerification(){
      onView(withText(weightText)).check(matches(isDisplayed()));
   }
   @Test
   //Tests if the test weight text appears within the fragment
   public void accountDetailsTestWeightTextVerification(){
      onView(withHint(testWeight)).check(matches(isDisplayed()));
   }

   @Test
   // Tests if the birth year text appears within the fragment
   public void accountDetailsBirthYearTextVerification(){
      onView(withText(birthYearText)).check(matches(isDisplayed()));
   }

   @Test
   // Tests if the test birth year text appears within the fragment
   public void accountDetailsBirthYearTestTextVerification(){
      onView(withHint(testBirthYear)).check(matches(isDisplayed()));
   }

   @Test
   // Tests if the test birth year text appears within the fragment
   public void accountDetailsSexTextVerification(){
      onView(withText(sexText)).check(matches(isDisplayed()));
   }
   // Tests the entries of the gender spinner and verifies its existence
   @Test
   public void genderSpinnerVerification(){
      for(int i = 0; i < genderSpinner.length; i++) {
         onView(withId(R.id.accountDetails_gender_Spinner)).perform(click());
         onData(allOf(is(instanceOf(String.class)), is(genderSpinner[i]))).perform(click());
         onView(withId(R.id.accountDetails_gender_Spinner)).check(matches(withSpinnerText(containsString(genderSpinner[i]))));
      }
   }

   // Tests that the accept button appears.
   @Test
   public void acceptButtonTextVerification(){
      onView(withText(acceptText)).check(matches(isDisplayed()));
   }

   //Tests that the cancel button appears
   @Test
   public void cancelButtonTextVerification(){
      onView(withText(cancelText)).check(matches(isDisplayed()));
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
