package seniordesign.ipfw.fw_trails_app;

/**
 * Created by Warren on 4/5/2016.
 *
 * This class is an attempt to be the only place to update a recognized activity types
 */
public class ExerciseTypes {

    private CharSequence exerciseTypes[] = new CharSequence[]{"Bike", "Run", "Walk"};

    /**
     * Default constructor
     */
    public ExerciseTypes(){

    }

    /**
     *
     * @return Returns a CharSequence to enumerate each activity type
     */
    public CharSequence[] getExerciseTypes(){
        return exerciseTypes;
    }

}