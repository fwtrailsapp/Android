package seniordesign.ipfw.fw_trails_app;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jaron on 2/27/2016.
 */
public class AccountStatisticsModelUnitTests {

   private static AccountStatisticsModel accountStatModel;

   @BeforeClass
   public static void Setup(){
      accountStatModel = new AccountStatisticsModel();
   }

   //Tests the class exists
   @Test
   public void verifyClassExists(){
      assertNotNull(accountStatModel);
   }


   private void printInfo(String methodName, String info) {
      System.out.println("AchievementModelUnitTest::" + methodName + ": " + info);
   }
   private void printInfo(String methodName, double info) {
      System.out.println("AchievementModelUnitTest::"+methodName+": "+ info);
   }
}
