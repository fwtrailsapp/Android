package seniordesign.ipfw.fw_trails_app;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jaron on 2/6/2016.
 */
public class DurationUnitTests {


   private static final String testTime = "55:33:11";
   private static final String testTimeTick = "55:33:12";
   private static final int testTimeInSeconds = 199991;
   private final double testTimeInMinutes = 3333.18;
   private static Duration duration;
   private static Duration durationTick;
   private static Duration durationTick2;
   private static Duration durationSeconds;

   @BeforeClass
   public static void setup(){
      duration = new Duration(testTime);
      durationSeconds = new Duration(testTimeInSeconds);
      durationTick = new Duration(testTime);
      durationTick2 = new Duration(testTime);
   }


   // what needs to be done after the class is ran
   @AfterClass
   public static void tearDown() {
   }

   // Tests that the Duration class exists
   @Test
   public void classExists(){
      assertNotNull(duration);
   }

   // Verifies that the duration string is the same as the one passed in.
   @Test
   public void verifyDurationString(){
      printInfo("verifyDurationString", duration.toString());
      assertEquals(testTime, duration.toString());
   }

   //Tests that the duration string is converted to seconds properly.
   @Test
   public void verifyDurationStringToSeconds(){
      printInfo("verifyDurationStringToSeconds", duration.toString());
      assertEquals(testTimeInSeconds, duration.getDurationInSeconds(),
              testTimeInSeconds-duration.getDurationInSeconds());
   }



   @Test
   public void verifyDurationSecondsToString(){

      printInfo("verifyDurationSecondsToString", testTimeInSeconds + "" +
              " should equal "+durationSeconds.getDurationInSeconds());
      assertEquals(testTimeInSeconds, durationSeconds.getDurationInSeconds(),
              testTimeInSeconds -durationSeconds.getDurationInSeconds());
   }

   // Tests that the Tick method adds one second to the time in the duration.
   @Test
   public void verifyDurationTickInt(){
      durationTick.tickInt();
      printInfo("verifyDurationTick", testTimeInSeconds+1 + "" +
              " should equal "+durationTick.getDurationInSeconds());
      assertEquals(testTimeInSeconds+1, durationTick.getDurationInSeconds(),
              (testTimeInSeconds+1) -durationTick.getDurationInSeconds());
   }

   // Tests that the Tick method adds one second to the time in the duration.
   @Test
   public void verifyDurationTickString(){
      durationTick2.tickString();
      printInfo("verifyDurationTick", testTimeTick +
              " should equal " + durationTick2.toString());
      assertEquals(testTimeTick, durationTick2.toString());
   }

   // Helper methods to watch the values of the unit tests as they execute.
   private void printInfo(String methodName, String info) {
      System.out.println("DurationUnitTest::" + methodName + ": " + info);
   }
   private void printInfo(String methodName, double info) {
      System.out.println("DurationUnitTest::"+methodName+": "+ info);
   }
}
