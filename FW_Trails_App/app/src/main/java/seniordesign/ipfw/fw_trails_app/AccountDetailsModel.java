package seniordesign.ipfw.fw_trails_app;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jaron on 3/6/2016.
 */
public class AccountDetailsModel {

   private String username;
   private String password;
   private int height;
   private int weight;
   private int birthYear;
   private GenderOptions gender;
   private boolean modelChange;
   double BMR;

   public AccountDetailsModel(String username, String password, int height, int weight, int birthYear,
                              GenderOptions gender){
      this.username = username;
      this.password = password;
      this.height   = height;
      this.weight   = weight;
      this.birthYear = birthYear;
      this.gender = gender;
      modelChange = false;
      BMR = calculateBMR(height, weight, birthYear);
   }

   private double calculateBMR(int height, int weight, int birthYear) {
      return 13.75*weight + 5*height - 6.76*(Calendar.getInstance().get(Calendar.YEAR)-birthYear) + 66;
   }

   //TODO
   private double calculateBMRMale(int height, int weight, int birthYear) {
      return 13.75*weight + 5*height - 6.76*(Calendar.getInstance().get(Calendar.YEAR)-birthYear) + 66;
   }

   private double calculateBMRFemale(int height, int weight, int birthYear) {
      return 9.56*weight + 1.85*height - 4.68*(Calendar.getInstance().get(Calendar.YEAR)-birthYear) + 655;
   }

   // Returns the username for this account
   public String getUsername(){
      return username;
   }

   // Returns the password for this account
   public String getPassword(){
      return password;
   }

   // Returns the height for this account
   public int getHeight(){
      return height;
   }

   // Returns the Birth Year for this account
   public int getBirthYear(){
      return birthYear;
   }

   // Returns the weight for this account.
   public int getWeight(){
      return weight;
   }

   // Returns the gender for this account.
   public GenderOptions getGender(){
      return gender;
   }

   // Sets the new height to the parameter.
   // Typically called if the Fragment has noticed that the user put in a new number in the field
   public void changeHeight(int newHeight){
      modelChange = true;
      height = newHeight;
   }

   // Sets the new weight to the parameter.
   // Typically called if the Fragment has noticed that the user put in a new number in the field
   public void changeWeight(int newWeight){
      modelChange = true;
      weight = newWeight;
   }

   // Sets the new birth year to the parameter.
   // Typically called if the Fragment has noticed that the user put in a new number in the field
   public void changeBirthYear(int newYear){
      modelChange = true;
      birthYear = newYear;
   }

   // Sets the new gender to the parameter.
   // Typically called if the Fragment has noticed that the user put in a new number in the field
   public void changeGender(GenderOptions newGender)
   {
      modelChange = true;
      gender = newGender;
   }

   // Sets the new username to the parameter.
   // Typically called if the Fragment has noticed that the user put in a new number in the field
   public void changeUsername(String newName){
      modelChange = true;
      username = newName;
   }

   // Sets the new Password to the parameter.
   // Typically called if the Fragment has noticed that the user put in a new number in the field
   public void changePassword(String newPassword){
      modelChange = true;
      password = newPassword;
   }

   // Returns true if the data in the model has been changed by the user.
   public boolean modelChanged(){
      return modelChange;
   }
}
