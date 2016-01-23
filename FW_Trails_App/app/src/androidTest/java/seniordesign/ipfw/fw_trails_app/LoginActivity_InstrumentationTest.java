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


   private String usernameHint = "Name";
   private String passwordHint = "Password";
   // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
   @Rule
   public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

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
}
