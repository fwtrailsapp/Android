package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 2/27/2016.
 */
public class RunExerciseType extends ExerciseType {
   private final int runIcon = R.drawable.ic_directions_run_black_18dp; // icon for running.
   private final String exerciseType = "Run";
   private final double runningMET = 7.5;


   public RunExerciseType(Duration duration){
      MET = runningMET;
      this.duration = duration;
   }

   // Return the String representation of a run activity.
   public String getExerciseType(){
      return exerciseType;
   }

   // Returns the Duration Object
   public Duration getDuration(){
      return duration;
   }
   // Returns the MET value per the activity.
   public double getMETValue() {
      return MET;
   }

   // Calculate Calories for run exercise
   public int calculateCaloriesBurned(double BMR){
      return super.calculateCaloriesBurned(BMR);
   }
   // Returns the R.drawable.etc for the icon related to the exercise type.
   public int getIconResourceId() {
      return runIcon;
   }
}
