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

public class Duration {

   private String theDuration;
   private final int SECONDS_PER_HOUR = 3600;
   private final int SECONDS_PER_MINUTE = 60;


   public Duration(String theDuration) {
      this.theDuration = theDuration;
   }

   public Duration(int startSeconds){
      theDuration = getDurationFromSeconds(startSeconds);
   }

   // Returns the string representation of the duration.
   // i.e. "00:05:21"
   @Override
   public String toString(){
      return theDuration;
   }

   // Returns the duration in number of seconds
   // ie 55:33:11 == 199,991 seconds
   public int getDurationInSeconds(){
      int totalSeconds;
      String durationPieces[] = theDuration.split(":");

      // Parse the integers from HH:MM:SS
      int hours = Integer.valueOf(durationPieces[0]);
      int minutes = Integer.valueOf(durationPieces[1]);
      int seconds = Integer.valueOf(durationPieces[2]);

      // Calculate the total time in seconds
      totalSeconds = hours*SECONDS_PER_HOUR + minutes*SECONDS_PER_MINUTE + seconds;

      return totalSeconds;
   }

   //  Converts the number of seconds into a format of HH:MM:SS as a String
   public String getDurationFromSeconds(int startSeconds){
      String duration = "";
      int hours = startSeconds / SECONDS_PER_HOUR;
      startSeconds %= SECONDS_PER_HOUR;

      int minutes = startSeconds / SECONDS_PER_MINUTE;
      startSeconds %= SECONDS_PER_MINUTE;

      // Ensure formatting by checking for single digit integers.
      if(hours < 10){
         duration+="0"+Integer.toString(hours)+":";
      }
      else{
         duration+=Integer.toString(hours)+":";
      }

      if(minutes < 10){
         duration+="0"+Integer.toString(minutes)+":";
      }
      else{
         duration+=Integer.toString(minutes)+":";
      }

      if(startSeconds < 10){
         duration+="0"+Integer.toString(startSeconds);
      }
      else{
         duration+=Integer.toString(startSeconds);
      }

      return duration;
   }
   // Returns the string represntation of the duration with one second added to the object.
   public String tickString(){

      int seconds = getDurationInSeconds();
      seconds+=1;

      // Reset the duration String
      theDuration = getDurationFromSeconds(seconds);

      return theDuration;

   }

   // Returns the string representation of the duration with one second added to the object.
   public int tickInt(){
      int seconds = getDurationInSeconds();
      seconds+=1;

      // Reset the duration String.
      theDuration = getDurationFromSeconds(seconds);

      return seconds;
   }

}