package seniordesign.ipfw.fw_trails_app;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Warren on 3/13/2016.
 */
public class RecordActivityModel {

    //Static variables
    private final String EST = "EST";
    private final String isoDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    //Activity dependant variables
    private double totalDistance;
    private ExerciseType exerciseType;
    private String startTimestamp;
    private Duration durationTimer;
    private ArrayList<LatLng> currentCoordinates;
    private ArrayList<LatLng> allCoordinates;


    /**
     * This constructor initializes the values for this activity
     * @param exerciseType This is the exercise type being completed by the user
     */
    public RecordActivityModel(ExerciseType exerciseType){
        totalDistance = 0.0;
        this.exerciseType = exerciseType;
        // Create timestamp for now
        TimeZone tz = TimeZone.getDefault();
        java.text.DateFormat df = new SimpleDateFormat(isoDateFormat);
        df.setTimeZone(tz);
        startTimestamp = df.format(new Date());
        durationTimer = new Duration(0);
        currentCoordinates = new ArrayList<>();
        allCoordinates = new ArrayList<>();
    }

    public void addLatLng(LatLng ll){
        currentCoordinates.add(ll);
        allCoordinates.add(ll);
        Log.i("Development", Integer.toString(currentCoordinates.size()));
    }

    public ArrayList<LatLng> getCurrentLatLngs(){
        return currentCoordinates;
    }

    public void flushCurrentPath(){
        currentCoordinates.clear();
    }

    public ArrayList<LatLng> getAllLatLngs(){
        return allCoordinates;
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
