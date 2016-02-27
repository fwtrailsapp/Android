package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Jaron on 2/27/2016.
 */
public class Duration {

   private String theDuration;
   private final int SECONDS_PER_HOUR = 3600;
   private final int SECONDS_PER_MINUTE = 60;
   private final int MINUTES_PER_HOUR = 60;


   public Duration(String theDuration) {
      this.theDuration = theDuration;
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
      int totalSeconds = 0;
      String durationPieces[] = theDuration.split(":");

      // Parse the integers from HH:MM:SS
      int hours = Integer.valueOf(durationPieces[0]);
      int minutes = Integer.valueOf(durationPieces[1]);
      int seconds = Integer.valueOf(durationPieces[2]);

      // Calculate the total time in seconds
      totalSeconds = hours*SECONDS_PER_HOUR + minutes*SECONDS_PER_MINUTE + seconds;

      return totalSeconds;
   }

   // Returns the duration in number of minutes
   // ie "55:33:11" == 3,333.18 minutes
   public double getDurationInMinutes(){
      double totalMinutes;

      String durationPieces[] = theDuration.split(":");

      double hours = Double.valueOf(durationPieces[0]);
      double minutes = Double.valueOf(durationPieces[1]);
      double seconds = Double.valueOf(durationPieces[2]);

      // Round seconds to two decimal places
      seconds = Math.round(seconds * 100);
      seconds = seconds/100;

      //Calculate the total time in minutes.
      totalMinutes =  hours*MINUTES_PER_HOUR+minutes+seconds;

      return totalMinutes;
   }
}
