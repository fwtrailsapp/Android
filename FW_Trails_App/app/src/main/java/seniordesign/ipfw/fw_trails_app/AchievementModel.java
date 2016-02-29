package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 2/27/2016.
 */
public class AchievementModel {
   private String achievementName;
   private String achievementDate;
   private String achievementDesc;

   public AchievementModel(String name, String date, String achievDesc){
      achievementName = name;

      // If not earned, don't give it a date.
      if(date == null)
      {
         achievementDate = "Not Earned";
      }
      else
      {
         achievementDate = date;
      }

      achievementDesc = achievDesc;
   }

   // Return the name of this achievement.
   public String getName(){
      return achievementName;
   }

   // Return the date or status of the achievement
   public String getDate(){
      return achievementDate;
   }

   // Returns the description of the achievement
   public String getDescription(){
      return achievementDesc;
   }
}
