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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class AccountDetailsModelUnitTests {

   private static AccountDetailsModel acctDetails;
   private static String password;
   private static String diffPassword;
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

      // Setup the passwords
      password = "test";
      diffPassword = "tset";

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

      acctDetails = new AccountDetailsModel(username,password, height, weight, birthYear, gender);
   }

   //Tests the class exists
   @Test
   public void verifyClassExists(){
      assertNotNull(acctDetails);
   }

   @Test
   public void verifyUsernameChanged(){
      acctDetails.changeUsername(diffUsername);
      printInfo("verifyUsernameChanged", diffUsername + " should equal "
              + acctDetails.getUsername());
      assertEquals(diffUsername, acctDetails.getUsername());
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

   @Test
   public void verifyPasswordChanged(){
      acctDetails.changePassword(diffPassword);

      printInfo("verifyPasswordChanged", diffPassword + " should equal "
              + acctDetails.getPassword());

      assertEquals(diffPassword, acctDetails.getPassword());
      assertTrue(acctDetails.modelChanged());
   }


   private void printInfo(String methodName, String info) {
      System.out.println("AccountDetailsModelUnitTest::" + methodName + ": " + info);
   }

   private void printInfo(String methodName, double info) {
      System.out.println("AccountDetailsModelUnitTest::"+methodName+": "+ info);
   }
}
