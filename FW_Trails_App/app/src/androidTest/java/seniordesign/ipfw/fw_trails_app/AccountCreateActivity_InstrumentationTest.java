package seniordesign.ipfw.fw_trails_app;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import org.junit.*;
import org.junit.Test;
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

import dalvik.annotation.TestTargetClass;


/**
 * Created by Jaron on 1/23/2016.
 * Design Doc: https://drive.google.com/drive/u/1/folders/0B46qah_2e5yKbzdMbVNxUDNPeE0
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AccountCreateActivity_InstrumentationTest {

   private String createNewAccountTitle = "Create New Account";
   // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
   @Rule
   public ActivityTestRule<AccountCreateActivity> mActivityRule = new ActivityTestRule(AccountCreateActivity.class);

   @Test
   public void createNewAccountTitleExists(){
      onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
   }

   // tests the toolbar's title which has a text view within itself so we look for anything that
   // is the parent of the toolbar that is also a TextView and sees if it matches with our desired
   // title.
   // source: http://www.captechconsulting.com/blogs/introduction-to-espresso
   @Test
   public void createNewAccountToolbarTitleVerification() {
      onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
              .check(matches(withText(createNewAccountTitle)));
   }

}
