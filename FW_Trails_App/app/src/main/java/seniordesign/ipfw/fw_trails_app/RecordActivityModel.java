package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Warren on 3/13/2016.
 */
public class RecordActivityModel {

    private double totalDistance;
    private ExerciseType exerciseType;
    Duration durationTimer = new Duration("00:00:01");;

    public RecordActivityModel(ExerciseType exerciseType){
        totalDistance = 0.0;
        this.exerciseType = exerciseType;
    }

    public void addDistance(double distance){
        totalDistance+=distance;
    }

    public double getTotalDistance(){
        return totalDistance;
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
