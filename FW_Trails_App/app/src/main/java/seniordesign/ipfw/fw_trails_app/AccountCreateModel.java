package seniordesign.ipfw.fw_trails_app;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by Jaron on 2/21/2016.
 */
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
