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
