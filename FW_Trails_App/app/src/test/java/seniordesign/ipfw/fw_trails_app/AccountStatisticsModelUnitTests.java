package seniordesign.ipfw.fw_trails_app;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jaron on 2/27/2016.
 */
public class AccountStatisticsModelUnitTests {

   // Test data fields used to test the model.
   private static AccountStatisticsModel accountStatModel;
   private static String accountActive;
   private static Duration duration;
   private static Duration bikeDuration;
   private static Duration runDuration;
   private static Duration walkDuration;
   private static double overallDistance;
   private static double bikeDistance;
   private static double runDistance;
   private static double walkDistance;
   private static int overallCalories;
   private static int bikeCalories;
   private static int runCalories;
   private static int walkCalories;
   private static int achievementsEarned;

   @BeforeClass
   public static void Setup(){

      // Setup default duration
      duration = new Duration("55:54:05");

      // Setup the overall distance
      overallDistance = 1568.65;

      // Setup the overall calories burned
      overallCalories = 10923;

      // Setup the achievements earned
      achievementsEarned = 3;

      // Setup the bike duration
      bikeDuration = new Duration("25:48:09");

      // Setup the bike distance
      bikeDistance = 453.5;

      // Setup the bike calories
      bikeCalories = 3535;

      // Setup the run duration
      runDuration = new Duration("14:11:54");

      // Setup the run distance
      runDistance = 681.1;

      // Setup the run calories burned
      runCalories = 8921;

      // Setup the walk duration
      walkDuration = new Duration("23:13:57");

      // Setup the walk distance
      walkDistance = 35.3;

      // Setup the walk calories
      walkCalories = 712;

      // Setup the account Active
      accountActive = "< 1 year";

      accountStatModel = new AccountStatisticsModel(duration, overallDistance, overallCalories,
              achievementsEarned,bikeDuration, bikeDistance,bikeCalories, runDuration,runDistance,
              runCalories, walkDuration, walkDistance, walkCalories, accountActive);
   }

   //Tests the class exists
   @Test
   public void verifyClassExists(){
      assertNotNull(accountStatModel);
   }

   @Test
   public void verifyOverallDurationExists() {
      assertNotNull(accountStatModel.getOverallDuration());
   }

   @Test
   public void verifyOverallDuration(){
      printInfo("verifyOverallDuration", duration.toString() +" should equal "
      + accountStatModel.getOverallDuration().toString());
      assertEquals(duration.toString(), accountStatModel.getOverallDuration());
   }


   @Test
   public void verifyOverallDistance(){
      printInfo("verifyOverallDistance", overallDistance + " should equal "+
      accountStatModel.getOverallDistance());
      assertEquals(overallDistance,accountStatModel.getOverallDistance(),overallDistance-
      accountStatModel.getOverallDistance());
   }

   @Test
   public void verifyAchievementsEarned(){
      printInfo("verifyAchievementsEarned", achievementsEarned + " should equal "+
              accountStatModel.getAchievementsEarned());
      assertEquals(achievementsEarned,accountStatModel.getAchievementsEarned(),achievementsEarned-
              accountStatModel.getAchievementsEarned());
   }
   @Test
   public void verifyOverallCaloriesExpended(){
      printInfo("verifyOverallCaloriesExpended", overallCalories + " should equal "+
              accountStatModel.getOverallCalories());
      assertEquals(overallCalories,accountStatModel.getOverallCalories(),overallCalories-
              accountStatModel.getOverallCalories());
   }

   @Test
   public void verifyBikeActivityDuration(){
      printInfo("verifyBikeActivityDuration", bikeDuration.toString() + " should equal "+
              accountStatModel.getBikeDuration());
      assertEquals(bikeDuration.toString(),accountStatModel.getBikeDuration());
   }

   @Test
   public void verifyBikeActivityDistance(){
      printInfo("verifyBikeActivityDistance", bikeDistance + " should equal "+
              accountStatModel.getBikeDistance());
      assertEquals(bikeDistance,accountStatModel.getBikeDistance(),bikeDistance-
              accountStatModel.getBikeDistance());
   }

   @Test
   public void verifyBikeCaloriesExpended(){
      printInfo("verifyBikeCaloriesExpended", bikeCalories + " should equal "+
              accountStatModel.getBikeCalories());
      assertEquals(bikeCalories,accountStatModel.getBikeCalories(), bikeCalories-
              accountStatModel.getBikeCalories());
   }

   @Test
   public void verifyRunActivityDuration(){
      printInfo("verifyRunActivityDuration", runDuration.toString() + " should equal "+
              accountStatModel.getRunDuration());
      assertEquals(runDuration.toString(),accountStatModel.getRunDuration());
   }

   @Test
   public void verifyRunActivityDistance(){
      printInfo("verifyRunActivityDistance", runDistance + " should equal "+
              accountStatModel.getRunDistance());
      assertEquals(runDistance,accountStatModel.getRunDistance(),runDistance-
              accountStatModel.getRunDistance());
   }

   @Test
   public void verifyRunCaloriesExpended(){
      printInfo("verifyRunCaloriesExpended", runCalories + " should equal "+
              accountStatModel.getRunCalories());
      assertEquals(runCalories,accountStatModel.getRunCalories(), runCalories-
              accountStatModel.getRunCalories());
   }

   @Test
   public void verifyWalkActivityDuration(){
      printInfo("verifyWalkActivityDuration", walkDuration.toString() + " should equal "+
              accountStatModel.getWalkDuration());
      assertEquals(walkDuration.toString(),accountStatModel.getWalkDuration());
   }

   @Test
   public void verifyWalkActivityDistance(){
      printInfo("verifyWalkActivityDistance", walkDistance + " should equal "+
              accountStatModel.getWalkDistance());
      assertEquals(walkDistance,accountStatModel.getWalkDistance(),walkDistance-
              accountStatModel.getWalkDistance());
   }

   @Test
   public void verifyWalkCaloriesExpended(){
      printInfo("verifyWalkCaloriesExpended", walkCalories + " should equal "+
              accountStatModel.getWalkCalories());
      assertEquals(walkCalories,accountStatModel.getWalkCalories(), walkCalories-
              accountStatModel.getWalkCalories());
   }

   @Test
   public void verifyAccountActive(){
      printInfo("verifyAccountActive", accountActive + " should equal "+
         accountStatModel.getAccountActive());
      assertEquals(accountActive,accountStatModel.getAccountActive());
   }

   private void printInfo(String methodName, String info) {
      System.out.println("AccountStatisticsModelUnitTest::" + methodName + ": " + info);
   }


   private void printInfo(String methodName, double info) {
      System.out.println("AccountStatisticsModelUnitTest::"+methodName+": "+ info);
   }
}
