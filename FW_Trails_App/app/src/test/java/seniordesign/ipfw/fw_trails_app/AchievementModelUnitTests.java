package seniordesign.ipfw.fw_trails_app;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jaron on 2/27/2016.
 */
public class AchievementModelUnitTests {

   private static AchievementModel achievementModel;
   private static AchievementModel notEarnedAchiev;
   private static final String achievName = "Daily Dose";
   private static final String achievDate = "02/22/2016";
   private static final String achievDesc = "Bike for 24 hours";
   private final String notEarnedDateValue = "Not Earned";

   @BeforeClass
   public static void Setup(){
      achievementModel = new AchievementModel(achievName, achievDate,
              achievDesc);
      notEarnedAchiev = new AchievementModel(achievName,null,achievDesc);
   }

   //Tests the class exists
   @Test
   public void verifyClassExists(){
      assertNotNull(achievementModel);
   }

   //Test that the achievement has a name
   @Test
   public void verifyAchievementName(){
      printInfo("verifyAchievementName",achievementModel.getName()+
              " should be "+ achievName);
      assertEquals(achievName, achievementModel.getName());
   }

   //Test that the date earned is formatted correctly
   @Test
   public void verifyAchievementDate(){
      printInfo("verifyAchievementDate",achievementModel.getDate()+
              " should be "+ achievDate);
      assertEquals(achievDate, achievementModel.getDate());
   }

   //Test that the achievement has a description
   @Test
   public void verifyAchievementDescription(){
      printInfo("verifyAchievementDescription",achievementModel.getDescription()+
              " should be "+ achievDesc);
      assertEquals(achievDesc, achievementModel.getDescription());
   }

   // Verify that the not earned achievements exist properly
   @Test
   public void verifyNotEarnedAchievementDate(){
      printInfo("verifyNotEarnedAchievementDate",notEarnedAchiev.getDate()+
              " should be "+ notEarnedDateValue);
      assertEquals(notEarnedDateValue, notEarnedAchiev.getDate());
   }
   private void printInfo(String methodName, String info) {
      System.out.println("AchievementModelUnitTest::" + methodName + ": " + info);
   }
   private void printInfo(String methodName, double info) {
      System.out.println("AchievementModelUnitTest::"+methodName+": "+ info);
   }
}
