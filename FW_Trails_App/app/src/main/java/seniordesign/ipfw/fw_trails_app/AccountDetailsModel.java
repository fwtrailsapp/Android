package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 3/6/2016.
 */
public class AccountDetailsModel {

   private String username;
   private int height;
   private int weight;
   private int birthYear;
   private GenderOptions gender;
   private boolean modelChange;

   public AccountDetailsModel(String username, int height, int weight, int birthYear,
                              GenderOptions gender){
      this.username = username;
      this.height   = height;
      this.weight   = weight;
      this.birthYear = birthYear;
      this.gender = gender;
      modelChange = false;
   }

   // Returns the username for this account
   public String getUsername(){
      return username;
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

   public void changeUsername(String newName){
      modelChange = true;
      username = newName;
   }

   // Returns true if the data in the model has been changed by the user.
   public boolean modelChanged(){
      return modelChange;
   }
}
