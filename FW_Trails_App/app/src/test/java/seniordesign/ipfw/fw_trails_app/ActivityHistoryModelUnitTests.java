package seniordesign.ipfw.fw_trails_app;


import android.util.Log;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import android.util.Log;

/**
 * Created by Jaron on 2/6/2016.
 */
public class ActivityHistoryModelUnitTests {

   private static ActivityHistoryModel activityHistoryModel;
   private static String nowAsISO;
   private static Duration theDuration;
   private static double theMileage;
   private static int theCalsBurned;
   private static ExerciseType theExerciseType;

   // What needs to be done before the class is ran
   // Sets up the DateTime
   // Sets up the Duration
   // Sets up the Mileage
   // Sets up the Calories Burned
   @BeforeClass
   public static void setUp() {
      // Set up the DateTime
      TimeZone tz = TimeZone.getTimeZone("UTC");
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      df.setTimeZone(tz);
      nowAsISO = df.format(new Date());

      //Set up the duration
      theDuration = new Duration("55:31:29");

      //Set up the mileage
      theMileage = 11.5;

      //Set up the calories burned
      theCalsBurned = 884;

      theExerciseType = new RunExerciseType(theDuration);

      activityHistoryModel = new ActivityHistoryModel(nowAsISO,theDuration,
              theMileage, theCalsBurned, theExerciseType);
   }

   // what needs to be done after the class is ran
   @AfterClass
   public static void tearDown() {
   }

   //This method verifies that we have a date time object getter method and object.
   @Test
   public void hasDateTimeObject(){
      printInfo("hasDateTimeObject",activityHistoryModel.getDate()+
      " should be not null");
      assertNotNull(activityHistoryModel.getDate());
   }


   // This method tests the format of the date
   // mm/dd/yyyy
   @Test
   public void verifyDateObjectFormat(){
      printInfo("verifyDateObjectFormat",activityHistoryModel.getDate()+
              " should be "+formatDate(nowAsISO));
      assertEquals(formatDate(nowAsISO),activityHistoryModel.getDate());
   }

   // This method tests that we have a duration method/field in activityHistoryModel.
   @Test
   public void hasDuration(){
      printInfo("hasDuration", activityHistoryModel.getDuration()+
      " should be not null");
      assertNotNull(activityHistoryModel.getDuration());
   }

   // This method tests that the duration object is the same as the one created here.
   @Test
   public void verifyDuration(){
      printInfo("verifyDuration",activityHistoryModel.getDuration()+
      " should be "+theDuration.toString());
      assertEquals(theDuration.toString(), activityHistoryModel.getDuration());
   }

   //Tests that the ActivityHistoryModel has the mileage field and getter
   @Test
   public void hasMileage(){
      printInfo("hasMileage", activityHistoryModel.getMileage()+
      " should be not null");
      assertNotNull(activityHistoryModel.getMileage());
   }

   // This method tests that the mileage is the same mileage that was passed into the
   // constructor.
   @Test
   public void verifyMileage(){
      printInfo("verifyMileage",activityHistoryModel.getMileage()+
      " should be "+theMileage);
      assertEquals(theMileage, activityHistoryModel.getMileage(),
              theMileage - activityHistoryModel.getMileage());
   }

   //Tests that ActivityHistoryModel has a calories burned field.
   @Test
   public void hasCaloriesBurned(){
      printInfo("hasCaloriesBurned", activityHistoryModel.getCalsBurned()+
      " should be not null");
      assertNotNull(activityHistoryModel.getCalsBurned());
   }

   // Verifies that the calories burned value is the same as the one passed into
   // the constructor
   @Test
   public void verifyCaloriesBurned(){
      printInfo("verifyCaloriesBurned",activityHistoryModel.getCalsBurned()+
      " should be "+ theCalsBurned);
      assertEquals(theCalsBurned, activityHistoryModel.getCalsBurned(),
              theCalsBurned - activityHistoryModel.getCalsBurned());
   }

   //Verify the exercise type
   @Test
   public void verifyExerciseType(){
      printInfo("verifyExerciseType",activityHistoryModel.getExerciseType()+
      " should be "+ theExerciseType.getExerciseType());
      assertEquals(theExerciseType.getExerciseType(),
              activityHistoryModel.getExerciseType());
   }

   //Verify the exercise type has the correct resource ID for the icon
   @Test
   public void verifyExerciseTypeIconID(){
      printInfo("verifyExerciseTypeIconID",activityHistoryModel.getExerciseTypeIconID()+
              " should be "+ theExerciseType.getIconResourceId());
      assertEquals(activityHistoryModel.getExerciseTypeIconID(),
              theExerciseType.getIconResourceId());
   }
   // Tests that the class exists.
   @Test
   public void classExists() {

      assertNotNull(activityHistoryModel);
   }


   private void printInfo(String methodName, String info) {
      System.out.println("ActivityHistoryModelUnitTest::" + methodName + ": " + info);
   }
   private void printInfo(String methodName, double info) {
      System.out.println("ActivityHistoryModelUnitTest::"+methodName+": "+ info);
   }

   // Precondition that the original date is in the format of
   // yyyy-MM-dd'T'HH:mm:ss
   private static String formatDate(String originalDate){
      String newDate;

      String allDatePieces[] = originalDate.split("T");

      String datePiece = allDatePieces[0];
      String onlyDatePieces[] = datePiece.split("-");

      newDate = onlyDatePieces[1]+"/"+onlyDatePieces[2]+"/"+onlyDatePieces[0];

      return newDate;
   }
}
