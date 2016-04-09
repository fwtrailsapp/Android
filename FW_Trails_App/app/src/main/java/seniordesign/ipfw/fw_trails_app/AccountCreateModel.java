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

import android.util.Log;

import java.text.DecimalFormat;


public class AccountCreateModel {

   private String username;
   private String password;
   private final String userSpec = "User: ";
   private int height;
   private int weight;
   private int birthYear;
   private GenderOptions gender;

   public AccountCreateModel(String username, String password) throws IllegalArgumentException{
      if(username.equals("") || password.equals("")){
         throw new IllegalArgumentException();
      }

      this.username = username;
      this.password = password;
   }

   // Returns the username of this account.
   public String getUsername(){
      return username;
   }

   // Returns the password of this account.
   public String getPassword(){
      return password;
   }

   // Sets the gender
   public void setGender(GenderOptions theGender){
      gender = theGender;
   }

   // sets the height
   public void setHeight(int theHeight){
      height = theHeight;
   }

   // sets the weight
   public void setWeight(int theWeight){
      weight = theWeight;
   }

   // sets the year the account member was born.
   public void setBirthYear(int theYear){
      birthYear = theYear;
   }

   // returns the gender for this account.
   public GenderOptions getGender(){
      return gender;
   }

   // Returns the height of this account.
   public int getHeight(){
      return height;
   }

   // Returns the weight of this account.
   public int getWeight(){
      return weight;
   }

   // returns the year of birth of this account member
   public int getBirthYear(){
      return birthYear;
   }

   // Specifies the account name when toString is called.
   @Override
   public String toString(){
      return userSpec + username;
   }
}
