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

import android.util.Log;

import java.text.DecimalFormat;

public class ActivityHistoryModel {


   private String theDate;
   private Duration theDuration;
   private double theMileage;
   private int    theCalsBurned;
   private ExerciseType theExercise;
   private final DecimalFormat distanceFormatter = new DecimalFormat("#.#");


   public ActivityHistoryModel(String theDate, Duration theDuration, double theMileage,
                               int theCalsBurned, ExerciseType theExercise) {
      this.theDuration = theDuration;
      this.theDate = formatDate(theDate);
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

   // Returns the Exercise Type's icon ID
   public int getExerciseTypeIconID(){
      return theExercise.getIconResourceId();
   }

   // Precondition that the original date is in the format of
   // yyyy-MM-dd'T'HH:mm:ss
   private String formatDate(String originalDate){
      String newDate;
      try{

         String allDatePieces[] = originalDate.split("T");

         String datePiece = allDatePieces[0];
         String onlyDatePieces[] = datePiece.split("-");

         newDate = onlyDatePieces[1]+"/"+onlyDatePieces[2]+"/"+onlyDatePieces[0];
      }
      catch(Exception ex)
      {
         newDate = "00/00/0000";
         Log.e("FormatDate: ", "Illegal date format: "+originalDate);
      }

      return newDate;
   }
}
