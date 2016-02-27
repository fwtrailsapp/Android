package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 2/27/2016.
 */
public abstract class ExerciseType {
   private final double SECONDS_PER_DAY = 86400.00;
   protected double caloriesBurned;              // the calories burned for this activity.
   protected double MET;                         //METABOLIC EQUIVALENT (MET) VALUES FOR ACTIVITIES
   protected double distance;                    // The distance traveled for the activity.
   protected Duration duration;                    // The duration of the activity in seconds.


   // Return the String representation of a run activity.
   abstract String getExerciseType();

   // Returns the MET value per the activity.
   abstract double getMETValue();

   // Returns the R.drawable.etc for the icon related to the exercise type.
   abstract int getIconResourceId();

   //Returns the duration object
   abstract Duration getDuration();

   // General calories burned calculation
   // BMR is the BMR of the account that is performing the activity
   // Calculated per https://en.wikipedia.org/wiki/Basal_metabolic_rate
   // Mifflin St. Jeor Equation.
   // Instead of days, we convert to seconds.
   // ie: (rest calories per second) + MET*DurationOfActivity
   public int calculateCaloriesBurned(double BMR){

      caloriesBurned = (BMR/ SECONDS_PER_DAY) + MET * duration.getDurationInSeconds();

      return (int)Math.round(caloriesBurned);
   }

}
