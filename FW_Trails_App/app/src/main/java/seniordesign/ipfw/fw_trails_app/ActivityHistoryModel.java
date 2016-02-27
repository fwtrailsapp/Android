package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 2/21/2016.
 */
public class ActivityHistoryModel {


   private String theDate;
   private Duration theDuration;
   private double theMileage;
   private int    theCalsBurned;
   private ExerciseType theExercise;


   public ActivityHistoryModel(String theDate, Duration theDuration, double theMileage,
                               int theCalsBurned, ExerciseType theExercise) {
      this.theDuration = theDuration;
      this.theDate = theDate;
      this.theCalsBurned = theCalsBurned;
      this.theMileage = theMileage;
      this.theExercise = theExercise;
   }

   // Returns the date of this activity for this model object.
   public String getDate() {
      return theDate;
   }

   // Returns the duration for this model object.
   public String getDuration(){
      return theDuration.toString();
   }

   // Returns the mileage for this model object.
   public double getMileage(){
      return theMileage;
   }

   // Returns the calories burned for this model object.
   public int getCalsBurned(){
      return theCalsBurned;
   }

   // Returns the Exercise Type
   // ie "Run", "Walk", "Bike"
   public String getExerciseType(){
      return theExercise.getExerciseType();
   }
}
