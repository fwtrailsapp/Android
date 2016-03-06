package seniordesign.ipfw.fw_trails_app;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.String;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
public class AccountStatistics_InstrumentationTest {

   private final String dummyTime = "00:00:00";
   private final String toolbarTitle = "Your Statistics";
   private final String overallStatText = "Overall Statistics";
   private final String totalTimeText   = "Total Duration";
   private final String dummyDistance = "0.00 mi.";
   private final String totalDistanceText = "Total Distance";
   private final String numberOfAchievements = "Achievements Earned";
   private final String dummyNumOfActivities = "0";
   private final String activitySpecificText = "View Activity Statistics";
   private final String[] activitySpinner = {"Running","Biking","Walking"};
   private final String overallCalsExpendedText = "Total Calories Expended";
   private final String defaultCalorieAmount = "0 kcal";
   private final String accountActiveText = "Account Active";
   private final String accountActiveDefaultAmount = "< 1 year";


   // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
   @Rule
   public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

   // Open Nav Drawer before each test.
   @Before
   public void navDrawerOpen(){
      openDrawer();
      onView(withId(R.id.nav_drawerImage)).check(matches(isDisplayed()));
      onView(withText(R.string.navDrawer_accountStatistics)).perform(click());
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
   @Ignore
   public void accountStatisticsToolbarExists() {
      onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
   }

   // tests the toolbar's title which inherits a text view so we look for anything that
   // is the parent of the toolbar that is also a TextView and see if it matches with our desired
   // title. The title is set in the onCreate method.
   // source: http://www.captechconsulting.com/blogs/introduction-to-espresso
   @Ignore
   public void AccountStatisticsToolbarTitleVerification() {
      onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
              .check(matches(withText(toolbarTitle)));
   }

   @Test
   public void overallStatisticsTitleExists(){
      onView(withId(R.id.overallStatTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void overallStatisticsTitleVerification() {
      onView(withId(R.id.overallStatTextView)).check(matches(withText(overallStatText)));
   }

   @Test
   public void overallTotalTimeTextViewExists(){
      onView(withId(R.id.overallTotalTimeTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void overallTotalTimeTextViewVerification(){
      onView(withId(R.id.overallTotalTimeTextView)).check(matches(withText(totalTimeText)));
   }

   @Test
   public void overallTotalTimeAmountTextViewExists() {
      onView(withId(R.id.overallTotalTimeAmountTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void overallTotalTimeAmountTextViewVerification() {
      onView(withId(R.id.overallTotalTimeAmountTextView)).check(matches(withText(dummyTime)));
   }

   @Test
   public void overallTotalDistanceTextViewExists() {
      onView(withId(R.id.overallTotalDistanceTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void overallTotalDistanceTextViewVerification() {
      onView(withId(R.id.overallTotalDistanceTextView)).check(matches(withText(totalDistanceText)));
   }

   @Test
   public void overallCaloriesExpendedVerification() {
      onView(withId(R.id.totalCaloriesExpendedTextView)).check(matches(withText(overallCalsExpendedText)));
   }

   @Test
   public void overallCaloriesExpendedAmountVerification() {
      onView(withId(R.id.totalCaloriesExpendedAmountTextView)).check(matches(withText(defaultCalorieAmount)));
   }

   @Test
   public void specificActivityCaloriesExpendedVerification() {
      onView(withId(R.id.activitySpecificCaloriesExpendedTextView)).check(matches(withText(overallCalsExpendedText)));
   }

   @Test
   public void specificActivityCaloriesExpendedAmountVerification() {
      onView(withId(R.id.activitySpecificCaloriesExpendedAmountTextView)).check(matches(withText(defaultCalorieAmount)));
   }

   @Test
   public void accountActiveVerification(){
      onView(withId(R.id.accountActiveTextView)).check(matches(withText(accountActiveText)));
   }

   @Test
   public void accountActiveAmountVerification() {
      onView(withId(R.id.accountActiveAmountTextView)).check(matches(withText(accountActiveDefaultAmount)));
   }

   @Test
   public void overallTotalDistanceAmountTextViewExists() {
      onView(withId(R.id.overallTotalDistanceAmountTextView)).check(matches(isDisplayed()));
   }
   @Test
   public void overallTotalDistanceAmountTextViewVerification() {
      onView(withId(R.id.overallTotalDistanceAmountTextView)).check(matches(withText(dummyDistance)));
   }


   @Test
   public void overallNumberOfActivitiesTextViewExists() {
      onView(withId(R.id.overallNumberOfAchievementsTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void overallNumberOfActivitiesTextViewVerification() {
      onView(withId(R.id.overallNumberOfAchievementsTextView)).check(matches(withText(numberOfAchievements)));
   }

   @Test
   public void overallNumberOfActivitiesAmountTextViewExists() {
      onView(withId(R.id.overallNumberOfAchievementsAmountTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void overallNumberOfActivitiesAmountTextViewVerification() {
      onView(withId(R.id.overallNumberOfAchievementsAmountTextView)).check(matches(withText(dummyNumOfActivities)));
   }

   @Test
       public void activitySpecificStatisticsTitleExists(){
      onView(withId(R.id.activitySpecificStatTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void activitySpecificStatisticsTitleVerification() {
      onView(withId(R.id.activitySpecificStatTextView)).check(matches(withText(activitySpecificText)));
   }

   @Test
   public void activitySpinnerExists(){
      onView(withId(R.id.activity_Spinner)).check(matches(isDisplayed()));
   }

   @Test
   public void activitySpinnerVerification(){
      for(int i = 0; i < activitySpinner.length; i++) {
         onView(withId(R.id.activity_Spinner)).perform(click());
         onData(allOf(is(instanceOf(String.class)), is(activitySpinner[i]))).perform(click());
         onView(withId(R.id.activity_Spinner)).check(matches(withSpinnerText(containsString(activitySpinner[i]))));
      }
   }

   @Test
   public void activitySpecificTotalTimeExists(){
      onView(withId(R.id.activitySpecificTotalTimeTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void activitySpecificTotalTimeVerification() {
      onView(withId(R.id.activitySpecificTotalTimeTextView)).check(matches(withText(totalTimeText)));
   }

   @Test
   public void activitySpecificTotalTimeAmountExists(){
      onView(withId(R.id.activitySpecificTotalTimeAmountTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void activitySpecificTotalTimeAmountVerification() {
      onView(withId(R.id.activitySpecificTotalTimeAmountTextView)).check(matches(withText(dummyTime)));
   }

   @Test
   public void activitySpecificTotalDistanceExists(){
      onView(withId(R.id.activitySpecificTotalDistanceTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void activitySpecificTotalDistanceVerification() {
      onView(withId(R.id.activitySpecificTotalDistanceTextView)).check(matches(withText(totalDistanceText)));
   }

   @Test
   public void activitySpecificTotalDistanceAmountExists(){
      onView(withId(R.id.activitySpecificTotalDistanceAmountTextView)).check(matches(isDisplayed()));
   }

   @Test
   public void activitySpecificTotalDistanceAmountVerification() {
      onView(withId(R.id.activitySpecificTotalDistanceAmountTextView)).check(matches(withText(dummyDistance)));
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
