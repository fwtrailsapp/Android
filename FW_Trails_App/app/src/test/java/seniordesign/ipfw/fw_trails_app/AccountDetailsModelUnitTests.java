package seniordesign.ipfw.fw_trails_app;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jaron on 2/27/2016.
 */
public class AccountDetailsModelUnitTests {

   private static AccountDetailsModel acctDetails;
   private static String username;
   private static String diffUsername;
   private static int height;
   private static int diffHeight;
   private static int weight;
   private static int birthYear;
   private static int diffYear;
   private static int diffWeight;
   private static GenderOptions gender;
   private static GenderOptions diffGender;

   @BeforeClass
   public static void Setup(){

      // Setup the usernames
      username = "User Test";
      diffUsername = "Diff User Test";

      // Setup the height
      height = 72;
      diffHeight = 71;

      // Setup the weight
      weight = 167;
      diffWeight = 173;

      // Setup the birth year
      birthYear = 1903;
      diffYear = 1993;

      // Setup the genders
      gender = GenderOptions.Female;
      diffGender = GenderOptions.Male;

      acctDetails = new AccountDetailsModel(username, height, weight, birthYear, gender);
   }

   //Tests the class exists
   @Test
   public void verifyClassExists(){
      assertNotNull(acctDetails);
   }

   @Test
   public void verifyUsername(){
      printInfo("verifyUsername", username + " should equal "
              + acctDetails.getUsername());
      assertEquals(username, acctDetails.getUsername());
   }

   // Tests whether or not the height was changed successfully and that the model recognizes that it
   // was changed.
   @Test
   public void verifyHeightChanged(){
      acctDetails.changeHeight(diffHeight);
      printInfo("verifyHeightChanged", diffHeight + " should equal "
              + acctDetails.getHeight());
      assertEquals(diffHeight, acctDetails.getHeight());
      assertTrue(acctDetails.modelChanged());
   }

   // Tests whether or not the weight was changed successfully and that the model recognizes that it
   // was changed.
   @Test
   public void verifyWeightChanged(){
      acctDetails.changeWeight(diffWeight);

      printInfo("verifyWeightChanged", diffWeight + " should equal "
              + acctDetails.getWeight());
      assertEquals(diffWeight, acctDetails.getWeight());
      assertTrue(acctDetails.modelChanged());
   }

   // Tests whether or not the birth year was changed successfully and that the model recognizes that it
   // was changed.
   @Test
   public void verifyBirthYearChanged(){
      acctDetails.changeBirthYear(diffYear);

      printInfo("verifyBirthYearChanged", diffYear + " should equal "
              + acctDetails.getBirthYear());
      assertEquals(diffYear, acctDetails.getBirthYear());
      assertTrue(acctDetails.modelChanged());
   }

   @Test
   public void verifyGenderChanged(){
      acctDetails.changeGender(diffGender);

      printInfo("verifyGenderChanged", diffGender + " should equal "
              + acctDetails.getGender());
      assertEquals(diffGender, acctDetails.getGender());
      assertTrue(acctDetails.modelChanged());
   }

   private void printInfo(String methodName, String info) {
      System.out.println("AccountDetailsModelUnitTest::" + methodName + ": " + info);
   }

   private void printInfo(String methodName, double info) {
      System.out.println("AccountDetailsModelUnitTest::"+methodName+": "+ info);
   }
}
