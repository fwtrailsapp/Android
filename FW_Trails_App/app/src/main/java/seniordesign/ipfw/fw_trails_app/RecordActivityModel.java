package seniordesign.ipfw.fw_trails_app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Warren on 3/13/2016.
 */
public class RecordActivityModel {

    private double totalDistance;
    private ExerciseType exerciseType;
    private String startTimestamp;
    Duration durationTimer = new Duration("00:00:01");
    private final String EST = "EST";
    private final String isoDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public RecordActivityModel(ExerciseType exerciseType){

        // Create timestamp for now
        TimeZone tz = TimeZone.getDefault();
        java.text.DateFormat df = new SimpleDateFormat(isoDateFormat);
        df.setTimeZone(tz);
        startTimestamp = df.format(new Date());

        totalDistance = 0.0;
        this.exerciseType = exerciseType;
    }

    public void addDistance(double distance){
        totalDistance+=distance;
    }

    public double getTotalDistance(){
        return totalDistance;
    }

    public String getStartTimestamp(){
        return startTimestamp;
    }

    public ExerciseType getExerciseType(){
        return exerciseType;
    }

    public int getDurationInSeconds(){
        return durationTimer.getDurationInSeconds();
    }

    public Duration getDuration(){
        return durationTimer;
    }

}
