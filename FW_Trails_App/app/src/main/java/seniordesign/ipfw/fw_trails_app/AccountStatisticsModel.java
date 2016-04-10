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


public class AccountStatisticsModel {


   private String accountActive;
   private Duration overallDuration;
   private Duration bikeDuration;
   private Duration runDuration;
   private Duration walkDuration;
   private double overallDistance;
   private double bikeDistance;
   private double runDistance;
   private double walkDistance;
   private int overallCalories;
   private int bikeCalories;
   private int runCalories;
   private int walkCalories;
   private int achievementsEarned;

   public AccountStatisticsModel(Duration overallDuration, double distance,
                                 int overallCalories, int achievementsEarned,
                                 Duration bikeDuration,double bikeDistance,
                                 int bikeCalories, Duration runDuration,
                                 double runDistance, int runCalories,
                                 Duration walkDuration, double walkDistance,
                                 int walkCalories, String accountActive){

      this.overallDuration = overallDuration;
      overallDistance = distance;
      this.overallCalories = overallCalories;
      this.achievementsEarned = achievementsEarned;
      this.bikeDuration = bikeDuration;
      this.bikeDistance = bikeDistance;
      this.bikeCalories = bikeCalories;
      this.runDuration = runDuration;
      this.runDistance = runDistance;
      this.runCalories = runCalories;
      this.walkDuration = walkDuration;
      this.walkDistance = walkDistance;
      this.walkCalories = walkCalories;
      this.accountActive = accountActive;
   }

   // Returns the string representation of the duration.
   public String getOverallDuration() {
      return overallDuration.toString();
   }

   // Returns the distance as a double.
   public double getOverallDistance(){
      return overallDistance;
   }

   // Returns the overall calories expended as a int
   public int getOverallCalories(){
      return overallCalories;
   }

   // Returns the achievements earned as an int
   public int getAchievementsEarned(){
      return achievementsEarned;
   }

   // Returns the bike stat duration as a string
   public String getBikeDuration(){
      return bikeDuration.toString();
   }

   // Returns the bike stat calories as a int;
   public int getBikeCalories(){
      return bikeCalories;
   }

   // Returns the bike stat distance as a double
   public double getBikeDistance(){
      return bikeDistance;
   }

   // Returns the run duration as a string.
   public String getRunDuration(){
      return runDuration.toString();
   }

   // Returns the run distance as a doiuble
   public double getRunDistance(){
      return runDistance;
   }

   // Returns the run calories as an int
   public int getRunCalories(){
      return runCalories;
   }

   // Returns the walk duration as a string
   public String getWalkDuration(){
      return walkDuration.toString();
   }

   // Returns the walk distance as a double
   public double getWalkDistance(){
       return walkDistance;
   }

   // Returns the walk calories as an int
   public int getWalkCalories(){
      return walkCalories;
   }

   // Returns the account active as a string
   public String getAccountActive(){
      return accountActive;
   }
}
