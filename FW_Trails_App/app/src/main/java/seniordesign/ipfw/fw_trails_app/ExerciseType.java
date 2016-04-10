/**
 Copyright (C) 2016 Jared Perry, Jaron Somers, Warren Barnes, Scott Weidenkopf, and Grant Grimm
 Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 and associated documentation files (the "Software"), to deal in the Software without restriction,
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies\n
 or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package seniordesign.ipfw.fw_trails_app;


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
