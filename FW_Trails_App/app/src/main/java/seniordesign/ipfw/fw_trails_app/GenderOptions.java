package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 3/6/2016.
 */
public enum GenderOptions {
   Male,Female,PreferNotToDisclose;

   public String getMaleString(){
      return "Male";
   }

   public String getFemleString(){
      return "Female";
   }

   public String getNotDisclosedString() {
      return "Prefer not to Disclose";
   }
}
