package seniordesign.ipfw.fw_trails_app;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
//Espresso Imports
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;


import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;


/**
 * Created by Jaron on 1/17/2016.
 * Design Doc: https://drive.google.com/drive/u/1/folders/0B46qah_2e5yKbzdMbVNxUDNPeE0
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivity_InstrumentationTest {


   private String usernameHint     = "Name";
   private String passwordHint     = "Password";
   private String button_OK_String = "Ok";
   private String button_CANCEL_String = "Cancel";
   private String textView_NEW_USER    = "New User?";
   private String button_CreateNewAccount_String = "Create New Account";

   // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
  // @Rule
  // public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

   // Checks to see if the Username Edit Text is on the screen.
   @Test
   public void usernameEditTextExists() {
      onView(withId(R.id.usernameEditText)).check(matches(isDisplayed()));
   }

   // Verifies the Username hint is "Name" per UI Design Doc
   @Test
   public void usernameHintVerification(){
      onView(withId(R.id.usernameEditText)).check(matches(withHint(usernameHint)));
   }

   // Checks to see if the Password Edit Text is on the screen.
   @Test
   public void passwordEditTextExists() {
      onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()));
   }

   // Verifies the Password hint is "Password" per UI Design Doc
   @Test
   public void passwordHintVerification(){
      onView(withId(R.id.passwordEditText)).check(matches(withHint(passwordHint)));
   }

   // Verifies the sponsor's image/logo per UI Design Doc
   @Test
   public void verifySponsorImage(){
      onView(withId(R.id.sponsorImage)).check(matches(isDisplayed()));
   }

   // Verifies the Ok button exists.
   @Test
   public void okButtonExists(){
      onView(withId(R.id.button_Ok)).check(matches(isDisplayed()));
   }

   // Tests if the okay button has the correct text
   @Test
   public void okButtonTextVerification(){
      onView(withId(R.id.button_Ok)).check(matches(withText(button_OK_String)));
   }

   // Tests if the cancel button exists
   @Test
   public void cancelButtonExists() {
      onView(withId(R.id.button_Cancel)).check(matches(isDisplayed()));
   }

   // Tests if the cancel button has the correct text.
   @Test
   public void cancelButtonVerification() {
      onView(withId(R.id.button_Cancel)).check(matches(withText(button_CANCEL_String)));
   }

   //Tests that the text view for the new user area exists
   @Test
   public void newUserTextViewExists(){
      onView(withId(R.id.textViewNewUser)).check(matches(isDisplayed()));
   }

   // Tests that the text is the correct value for the new user static text
   @Test
   public void newUserTextViewVerification() {
      onView(withId(R.id.textViewNewUser)).check(matches(withText(textView_NEW_USER)));
   }

   // Tests if the create new account button exists
   @Test
   public void createAccountButtonExists(){
      onView(withId(R.id.button_CreateAccount)).check(matches(isDisplayed()));
   }

   // Tests that the text is the correct value for the new account button
   @Test
   public void createNewAccountButtonVerification() {
      onView(withId(R.id.button_CreateAccount)).check(matches(withText(button_CreateNewAccount_String)));
   }
}
