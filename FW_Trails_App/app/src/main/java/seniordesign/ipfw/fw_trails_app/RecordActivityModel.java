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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


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
