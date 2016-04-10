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


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AccountCreateModelUnitTests {

   private static AccountCreateModel actCreate;
   private static final String testUsername = "test";
   private static final String testPassword = "test";
   private static final int testHeight = 70;
   private static final int testWeight = 199;
   private static final int testBirthYear = 2003;
   private static final GenderOptions testGender = GenderOptions.Female;



   // What needs to be done before the class is ran
   @BeforeClass
   public static void setUp() {

      // Create the test object.
      actCreate = new AccountCreateModel(testUsername, testPassword);
      actCreate.setBirthYear(testBirthYear);
      actCreate.setGender(testGender);
      actCreate.setWeight(testWeight);
      actCreate.setHeight(testHeight);
   }

   // what needs to be done after the class is ran
   @AfterClass
   public static void tearDown() {
   }

   @Test
   public void verifyAccountCreateModelExists() {
      printInfo("verifyAccountCreateModelExists", actCreate.toString());
      assertNotNull(actCreate);
   }

   @Test
   public void verifyAccountCreateUsername(){
      printInfo("verifyAccountCreateUsername", actCreate.getUsername() +" should equal "+ testUsername);

      assertEquals(testUsername, actCreate.getUsername());
   }

   @Test
   public void verifyAccountCreatePassword(){
      printInfo("verifyAccountCreatePassword", actCreate.getPassword() +" should equal "+ testPassword);

      assertEquals(testPassword,actCreate.getPassword());
   }

   @Test
   public void verifyAccountCreateHeight(){
      printInfo("verifyAccountCreateHeight", actCreate.getHeight() +" should equal "+ testHeight);

      assertEquals(testHeight,actCreate.getHeight());
   }


   @Test
   public void verifyAccountCreateWeight(){
      printInfo("verifyAccountCreateWeight", actCreate.getWeight() +" should equal "+ testWeight);

      assertEquals(testWeight,actCreate.getWeight());
   }


   @Test
   public void verifyAccountCreateBirthYear(){
      printInfo("verifyAccountCreateBirthYear", actCreate.getBirthYear() +" should equal "+ testBirthYear);

      assertEquals(testBirthYear,actCreate.getBirthYear());
   }


   @Test
   public void verifyAccountCreateGender(){
      printInfo("verifyAccountCreateGender", actCreate.getGender() +" should equal "+ testGender);

      assertEquals(testGender,actCreate.getGender());
   }

   @Test
   public void verifyAccountCreateGenderChange(){
      actCreate.setGender(GenderOptions.PreferNotToDisclose);

      printInfo("verifyAccountCreateGenderChange", actCreate.getGender() + " should equal " + GenderOptions.PreferNotToDisclose);

      assertEquals(GenderOptions.PreferNotToDisclose,actCreate.getGender());
   }

   private void printInfo(String methodName, String info) {
      System.out.println("AccountCreateModelUnitTest::" + methodName + ": " + info);
   }

   private void printInfo(String methodName, double info) {
      System.out.println("AccountCreateModelUnitTest::" + methodName + ": " + info);
   }
}
