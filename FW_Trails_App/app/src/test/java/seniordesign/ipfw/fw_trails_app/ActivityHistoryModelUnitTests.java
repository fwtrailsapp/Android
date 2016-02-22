package seniordesign.ipfw.fw_trails_app;


import android.util.Log;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertNotNull;
import android.util.Log;

/**
 * Created by Jaron on 2/6/2016.
 */
public class ActivityHistoryModelUnitTests {

   private static ActivityHistoryModel activityHistoryModel;
   private static String nowAsISO;


   // What needs to be done before the class is ran
   @BeforeClass
   public static void setUp() {
      TimeZone tz = TimeZone.getTimeZone("UTC");
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
      df.setTimeZone(tz);
      nowAsISO = df.format(new Date());

      activityHistoryModel = new ActivityHistoryModel(nowAsISO);
   }

   // what needs to be done after the class is ran
   @AfterClass
   public static void tearDown() {
   }

   @Test
   public void hasDateTimeObject(){
      printInfo("hasDateTimeObject",activityHistoryModel.getDate());
      assertNotNull(activityHistoryModel.getDate());

   }

   @Test
   public void classExists() {

      assertNotNull(activityHistoryModel);
   }

   private void printInfo(String methodName, String info) {
      System.out.println("ActivityHistoryModelUnitTest::"+methodName+": "+ info);
   }
}
