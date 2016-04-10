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
