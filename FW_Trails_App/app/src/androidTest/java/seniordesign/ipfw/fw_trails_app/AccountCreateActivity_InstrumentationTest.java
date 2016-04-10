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
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

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
public class AccountCreateActivity_InstrumentationTest {

   private String createNewAccountTitle = "Create New Account";
   private String firstNameEditText = "First Name";
   private String lastNameEditText = "Last Name";
   private String heightEditText = "Height (in inches)";
   private String weightEditText = "Weight (in pounds)";
   private String ageEditText = "Age";
   private String emailEditText = "Email";
   private String[] genderSpinner = {"Male","Female","Prefer not to disclose"};
   private String disclaimerTextView = "Disclaimer: This information is used\n" +
           "        for calories burned estimation.";
   private String createNewAcctBtn = "Create Account";
   private String cancelCreateNewAcctBtn = "Cancel";

   // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test


   //Ignore tests until toolbar/nav drawer iscreated
   @Ignore
   public void createNewAccountTitleExists(){
      onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
   }

   // tests the toolbar's title which inherits a text view so we look for anything that
   // is the parent of the toolbar that is also a TextView and see if it matches with our desired
   // title.
   // source: http://www.captechconsulting.com/blogs/introduction-to-espresso
   @Ignore
   public void createNewAccountToolbarTitleVerification() {
      onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
              .check(matches(withText(createNewAccountTitle)));
   }


   @Test
   public void heightEditTextExists(){
      onView(withId(R.id.accountCreate_heightEditText)).check(matches(isDisplayed()));
   }

   @Test
   public void heighteditTextVerification() {
      onView(withId(R.id.accountCreate_heightEditText)).check(matches(withHint(heightEditText)));
   }

   @Test
   public void weightEditTextExists(){
      onView(withId(R.id.accountCreate_weightEditText)).check(matches(isDisplayed()));
   }

   @Test
   public void weighteditTextVerification() {
      onView(withId(R.id.accountCreate_weightEditText)).check(matches(withHint(weightEditText)));
   }

   @Test
   public void ageEditTextExists(){
      onView(withId(R.id.accountCreate_birthYearEditText)).check(matches(isDisplayed()));
   }

   @Test
   public void ageTextVerification(){
      onView(withId(R.id.accountCreate_birthYearEditText)).check(matches(withHint(ageEditText)));
   }



   @Test
   public void genderSpinnerExists(){
      onView(withId(R.id.accountCreate_genderSpinner)).check(matches(isDisplayed()));
   }

   @Test
   public void genderSpinnerVerification(){
      for(int i = 0; i < genderSpinner.length; i++) {
         onView(withId(R.id.accountCreate_genderSpinner)).perform(click());
         onData(allOf(is(instanceOf(String.class)), is(genderSpinner[i]))).perform(click());
         onView(withId(R.id.accountCreate_genderSpinner)).check(matches(withSpinnerText(containsString(genderSpinner[i]))));
      }
   }

   @Test
   public void disclaimerTextViewExists(){
      onView(withId(R.id.disclaimer_Textview)).check(matches(isDisplayed()));
   }

   @Test
   public void disclaimerTextViewVerification() {
      onView(withId(R.id.disclaimer_Textview)).check(matches(withText(disclaimerTextView)));
   }

   @Test
   public void createAccountButtonExists(){
      onView(withId(R.id.createAccountButton)).check(matches(isDisplayed()));
   }
   @Test
   public void cancelCreateAccountButtonExists(){
      onView(withId(R.id.cancelCreateAccountButton)).check(matches(isDisplayed()));
   }

   @Test
   public void createAccountButtonVerification() {
      onView(withId(R.id.createAccountButton)).check(matches(withText(createNewAcctBtn)));
   }

   @Test
   public void cancelCreateAccountButtonVerification() {
      onView(withId(R.id.cancelCreateAccountButton)).check(matches(withText(cancelCreateNewAcctBtn)));
   }
}
