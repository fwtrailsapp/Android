package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 2/27/2016.
 */
public class WalkExerciseType extends ExerciseType {
   private final int walkIcon = R.drawable.ic_directions_walk_black_24dp; // icon for walking.
   private final String exerciseType = "Walk";
   private final double walkingMET = 3.8;


   public WalkExerciseType(Duration duration){
      MET = walkingMET;
      this.duration = duration;
   }

   // Return the String representation of a run activity.
   public String getExerciseType(){
      return exerciseType;
   }

   // Returns the Duration object
   public Duration getDuration(){
      return duration;
   }

   // Calculate Calories for walk exercise
   public int calculateCaloriesBurned(double BMR){
      return super.calculateCaloriesBurned(BMR);
   }
   // Returns the MET value per the activity.
   public double getMETValue() {
      return MET;
   }

   // Returns the R.drawable.etc for the icon related to the exercise type.
   public int getIconResourceId() {
      return walkIcon;
   }
}
