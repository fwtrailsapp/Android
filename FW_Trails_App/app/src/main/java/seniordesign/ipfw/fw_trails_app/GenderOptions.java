package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 3/6/2016.
 */
public enum GenderOptions {
   Male,Female,PreferNotToDisclose;

   public static final int MALE = 0;
   public static final int FEMALE = 1;
   public static final int PREFER_NOT_TO_DISCLOSE = 2;
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
