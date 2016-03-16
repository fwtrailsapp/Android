package seniordesign.ipfw.fw_trails_app;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jaron on 2/6/2016.
 */
public class ExerciseTypeUnitTests {

   // MET Values per http://appliedresearch.cancer.gov/atus-met/met.php

   private final String runType = "Run";
   private final int runIconID = R.drawable.ic_directions_run_black_18dp;
   private final int walkIconID = R.drawable.ic_directions_walk_black_18dp;
   private final int bikeIconID = R.drawable.ic_directions_bike_black_18dp;
   private static final String testTime = "55:33:11";
   private final int testRunCalsBurned = 1499933;
   private final int testBikeCalsBurned = 1599928;
   private final int testWalkCalsBurned = 759966;
   private Duration testDuration = new Duration(testTime);
   private final double defaultBMR = 1577.5;
   private ExerciseType exerciseType;
   private final String walkType = "Walk";
   private final String bikeType = "Bike";
   private final double runMET = 7.50;
   private final double bikeMET = 8.00;
   private final double walkMET = 3.80;


   // what needs to be done after the class is ran
   @AfterClass
   public static void tearDown() {
   }


   // This method checks that the date time object ist he same as the datetime object
   // that was passed in initially.
   @Test
   public void verifyExerciseTypeRun(){
      exerciseType = new RunExerciseType();

      printInfo("verifyExerciseTypeRun",exerciseType.getExerciseType()+" " +
              "should be "+runType);
      assertEquals(runType, exerciseType.getExerciseType());
   }

   @Test
   public void verifyExerciseTypeRunMET(){
      exerciseType = new RunExerciseType();

      printInfo("verifyExerciseTypeRunMET",exerciseType.getMETValue()+" " +
              "should be "+runMET);
      assertEquals(runMET, exerciseType.getMETValue(),
              runMET-exerciseType.getMETValue());
   }

   // Testst that the running Icon is correct.
   @Test
   public void verifyExerciseTypeRunIcon(){
      exerciseType = new RunExerciseType();

      printInfo("verifyExerciseTypeRunIcon",exerciseType.getIconResourceId()+" " +
              "should be "+ runIconID);
      assertEquals(runIconID,exerciseType.getIconResourceId());
   }

   // Test the calories burned calculations for running.
   @Test
   public void verifyExerciseTypeRunCalsBurned(){
      exerciseType = new RunExerciseType();
      printInfo("verifyExerciseTypeRunCalsBurned", exerciseType.calculateCaloriesBurned(defaultBMR) + " " +
              "should be " + testRunCalsBurned);
      assertEquals(exerciseType.calculateCaloriesBurned(defaultBMR),testRunCalsBurned,
              exerciseType.calculateCaloriesBurned(defaultBMR)- testRunCalsBurned);
   }

   // Tests that the exercise type duration is the correct one.
   @Test
   public void verifyRunDurationObject(){
      exerciseType = new RunExerciseType();
      printInfo("verifyRunDurationObject", exerciseType.getDuration().toString() + " " +
              "should be " + testDuration.toString());
      assertEquals(testDuration.toString(), exerciseType.getDuration().toString());
   }

   // This method checks that the Exercise Type has a walk type.
   @Test
   public void verifyExerciseTypeWalk(){
      exerciseType = new WalkExerciseType();

      printInfo("verifyExerciseTypeWalk",exerciseType.getExerciseType()+" " +
              "should be "+walkType);
      assertEquals(walkType, exerciseType.getExerciseType());
   }

   // Testst that the Walking MET is correct.
   @Test
   public void verifyExerciseTypeWalkMET(){
      exerciseType = new WalkExerciseType();

      printInfo("verifyExerciseTypeWalkMET",exerciseType.getMETValue()+" " +
              "should be "+walkMET);
      assertEquals(walkMET, exerciseType.getMETValue(),
              walkMET-exerciseType.getMETValue());
   }

   // Testst that the walking Icon is correct.
   @Test
   public void verifyExerciseTypeWalkIcon(){
      exerciseType = new WalkExerciseType();

      printInfo("verifyExerciseTypeWalkIcon",exerciseType.getIconResourceId()+" " +
              "should be "+ walkIconID);
      assertEquals(walkIconID,exerciseType.getIconResourceId());
   }

   // Test the calories burned calculations for biking.
   @Test
   public void verifyExerciseTypeWalkCalsBurned(){
      exerciseType = new WalkExerciseType();

      printInfo("verifyExerciseTypeWalkCalsBurned", exerciseType.calculateCaloriesBurned(defaultBMR) + " " +
              "should be " + testWalkCalsBurned);
      assertEquals(exerciseType.calculateCaloriesBurned(defaultBMR),testWalkCalsBurned,
              exerciseType.calculateCaloriesBurned(defaultBMR)- testWalkCalsBurned);
   }

   // Tests that the exercise type duration is the correct one.
   @Test
   public void verifyWalkDurationObject(){
      exerciseType = new WalkExerciseType();
      printInfo("verifyWalkDurationObject", exerciseType.getDuration().toString() + " " +
              "should be " + testDuration.toString());
      assertEquals(testDuration.toString(), exerciseType.getDuration().toString());
   }
   // This method checks that the Exercise Type has a bike type.
   @Test
   public void verifyExerciseTypeBike(){
      exerciseType = new BikeExerciseType();

      printInfo("verifyExerciseTypeBike",exerciseType.getExerciseType()+" " +
              "should be "+bikeType);
      assertEquals(bikeType, exerciseType.getExerciseType());
   }

   // Testst that the Walking MET is correct.
   @Test
   public void verifyExerciseTypeBikeMET(){
      exerciseType = new BikeExerciseType();

      printInfo("verifyExerciseTypeBikeMET",exerciseType.getMETValue()+" " +
              "should be "+bikeMET);
      assertEquals(bikeMET, exerciseType.getMETValue(),
              bikeMET-exerciseType.getMETValue());
   }

   // Tests that the biking Icon is correct.
   @Test
   public void verifyExerciseTypeBikeIcon(){
      exerciseType = new BikeExerciseType();

      printInfo("verifyExerciseTypeBikeIcon",exerciseType.getIconResourceId()+" " +
              "should be "+ bikeIconID);
      assertEquals(bikeIconID,exerciseType.getIconResourceId());
   }


   // Test the calories burned calculations for biking.
   @Test
   public void verifyExerciseTypeBikeCalsBurned(){
      exerciseType = new BikeExerciseType();
      printInfo("verifyExerciseTypeBikeCalsBurned", exerciseType.calculateCaloriesBurned(defaultBMR) + " " +
              "should be " + testBikeCalsBurned);
      assertEquals(exerciseType.calculateCaloriesBurned(defaultBMR),testBikeCalsBurned,
              exerciseType.calculateCaloriesBurned(defaultBMR)- testBikeCalsBurned);
   }

   // Tests that the exercise type duration is the correct one.
   @Test
   public void verifyBikeDurationObject(){
      exerciseType = new BikeExerciseType();
      printInfo("verifyBikeDurationObject", exerciseType.getDuration().toString() + " " +
              "should be " + testDuration.toString());
      assertEquals(testDuration.toString(), exerciseType.getDuration().toString());
   }

   // Helper methods to watch the values of the unit tests as they execute.
   private void printInfo(String methodName, String info) {
      System.out.println("ExerciseTypeUnitTest::" + methodName + ": " + info);
   }
   private void printInfo(String methodName, double info) {
      System.out.println("ExerciseTypeUnitTest::"+methodName+": "+ info);
   }
}
