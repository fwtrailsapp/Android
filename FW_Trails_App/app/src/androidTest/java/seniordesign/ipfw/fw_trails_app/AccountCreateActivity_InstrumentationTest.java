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
import static android.support.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.allOf;
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

   // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
   @Rule
   public ActivityTestRule<AccountCreateActivity> mActivityRule = new ActivityTestRule(AccountCreateActivity.class);

   @Test
   public void createNewAccountTitleExists(){
      onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
   }

   // tests the toolbar's title which inherits a text view so we look for anything that
   // is the parent of the toolbar that is also a TextView and see if it matches with our desired
   // title.
   // source: http://www.captechconsulting.com/blogs/introduction-to-espresso
   @Test
   public void createNewAccountToolbarTitleVerification() {
      onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
              .check(matches(withText(createNewAccountTitle)));
   }

   // Tests if the first name edit text box is displayed.
   @Test
   public void firstNameEditTextExists(){
      onView(withId(R.id.firstName_editText)).check(matches(isDisplayed()));
   }

   // Tests if the first name text is correct
   @Test
   public void firstNameEditTextVerification(){
      onView(withId(R.id.firstName_editText)).check(matches(withHint(firstNameEditText)));
   }

   @Test
   public void lastNameEditTextExists(){
      onView(withId(R.id.lastName_editText)).check(matches(isDisplayed()));
   }

   @Test
   public void lastNameEditTextVerification(){
      onView(withId(R.id.lastName_editText)).check(matches(withHint(lastNameEditText)));
   }

   @Test
   public void heightEditTextExists(){
      onView(withId(R.id.height_editText)).check(matches(isDisplayed()));
   }

   @Test
   public void heighteditTextVerification(){
      onView(withId(R.id.height_editText)).check(matches(withHint(heightEditText)));
   }

   @Test
   public void weightEditTextExists(){
      onView(withId(R.id.weight_editText)).check(matches(isDisplayed()));
   }

   @Test
   public void weighteditTextVerification(){
      onView(withId(R.id.weight_editText)).check(matches(withHint(weightEditText)));
   }

   @Test
   public void ageEditTextExists(){
      onView(withId(R.id.age_editText)).check(matches(isDisplayed()));
   }

   @Test
   public void ageTextVerification(){
      onView(withId(R.id.age_editText)).check(matches(withHint(ageEditText)));
   }
}
