package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 2/27/2016.
 */
public class BikeExerciseType extends ExerciseType {
   private final int bikeIcon =R.drawable.ic_directions_bike_black_18dp; // icon for Biking.
   private final String exerciseType = "Bike";
   private final double bikingMET = 8.0;


   public BikeExerciseType(Duration duration){
      MET = bikingMET;
      this.duration = duration;
   }

   // Return the String representation of a run activity.
   public String getExerciseType(){
      return exerciseType;
   }

   // Calculate Calories for bike exercise
   public int calculateCaloriesBurned(double BMR){
      return super.calculateCaloriesBurned(BMR);
   }

   // Returns the Duration object
   public Duration getDuration(){
      return duration;
   }
   // Returns the MET value per the activity.
   public double getMETValue() {
      return MET;
   }

   // Returns the R.drawable.etc for the icon related to the exercise type.
   public int getIconResourceId() {
      return bikeIcon;
   }
}
