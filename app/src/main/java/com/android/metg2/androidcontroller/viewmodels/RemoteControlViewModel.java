package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.metg2.androidcontroller.activities.MainMenuActivity;
import com.android.metg2.androidcontroller.repository.Repository;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.utils.RemoteControlInfo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The viewModel of the Remote Control Activity. This class performs the actions when the view listeners are
 * triggered. It communicates with the repository to sent a message,
 * and gets the received ones from it to pass the new gathered values to the view.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 2.0
 */
public class RemoteControlViewModel extends ViewModel implements Repository.RepositoryCallbacks{

    /**
     * The repository interface to get the logs.
     */
    private Repository repository;

    /**
     * The observable object that the view monitors and contains all the info
     */
    private MutableLiveData<RemoteControlInfo> infoMutableLiveData;

    /**
     * The list of all logs.
     */
    private static RemoteControlInfo info;

    /**
     * The message to be sent
     */
    private String message;

    /**
     * The constructor method. It initializes the repository interface.
     */
    public RemoteControlViewModel(){
        repository = new Repository(this);
    }

    /**
     * This method refreshes continuously the remote control challenge info to the view and asks the repository to
     * start the communication service.
     *
     * @param context Context The application context
     * @return LiveData<String> The list of logs
     */
    public LiveData<RemoteControlInfo> showInfo(Context context){

        //If it is the first call, the object will be null and we have to initialize it
        if(infoMutableLiveData == null){

            infoMutableLiveData = new MutableLiveData<>(); //initialize the observable variable
            if (info == null) info = new RemoteControlInfo(); //If the object is empty (first call), initialize it
            infoMutableLiveData.postValue(info); //update the observable variable with the new object
        }

        repository.startService(context); //ask the repository to start the service
        constructMessage();
        repository.sendMessage(message);
        return infoMutableLiveData; //return the observable variable
    }

    /**
     * This method asks the repository to stop the communication service.
     *
     * @param context Context The application context
     */
    public void stopRemoteControl(Context context){

        repository.sendMessage("type:stop,mode:none"); //send the stop message to the robot
        repository.stopService(context); //ask the repository to stop the service
    }

    /**
     * This method is called when the Aut/Man button is pressed (called from the listener). It updates
     * the new driving mode and constructs the message to ask the Arduino to do it.
     *
     * @param context Context The application context
     * @param isManual boolean True if the current mode is manual and false if it is automatic
     */
    public void onAutManPressed(Context context, boolean isManual) {

        /*if (isManual){

            Toast.makeText(context, "Changed RC mode to automatic", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Changed RC mode to manual", Toast.LENGTH_SHORT).show();
        }*/

        info.setManual(!isManual);
        constructMessage();
        repository.sendMessage(message);
    }


    /**
     * This method is called when the Lights button is pressed (called from the listener). It asks the robot
     * to turn lights on(off and updates the boolean value with the new lights status.
     *
     * @param context Context The application context
     * @param lightsON boolean True if the lights are currently on and false if they are off
     */
    public void onLightsPressed(Context context, boolean lightsON) {

        /*if (lightsON){

            Toast.makeText(context, "Lights turned OFF", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(context, "Lights turned ON", Toast.LENGTH_SHORT).show();
        }*/

        info.setLightsON(!lightsON);
        constructMessage();
        repository.sendMessage(message);
    }

    /**
     * This method is called when the Gear Up button is pressed (called from the listener). It increases the
     * value of the robot's driving gear and asks the robot to do it.
     * @param context Context The application context
     * @param gear int The current gear
     */
    public void onGearUpPressed(Context context, int gear) {

        //control if the current gear is already the maximum and update its value if it is not
        if (gear < Constants.MAX_GEAR){

            gear++;
        }

        info.setGear(gear);
        info.setSpeed(calculateSpeed(gear));
        constructMessage();
        repository.sendMessage(message);
    }

    /**
     * This method is called when the Gear Down button is pressed (called from the listener). It decreases the
     * value of the robot's driving gear and asks the robot to do it.
     * @param context Context The application context
     * @param gear int The current gear
     */
    public void onGearDownPressed(Context context, int gear) {

        //control if the current gear is already the minimum and update its value if it is not
        if (gear > Constants.MIN_GEAR){

            gear--;
        }

        info.setGear(gear);
        info.setSpeed(calculateSpeed(gear));
        constructMessage();
        repository.sendMessage(message);
    }

    /**
     * This method calculates the robot's driving speed depending on its current gear.
     * @param gear int The current gear
     * @return int The current robot's speed
     */
    public int calculateSpeed(int gear) {

        if (info.isGas()) {
            return gear * 10;
        }else {
            return 0;
        }
    }

    /**
     * This method is called when the Gas button is being pressed (called from the listener). It asks
     * the robot to accelerate and updates the boolean value with the new "gas" status.
     *
     * @param context Context The application context
     */
    public void onGas(Context context) {

        //Toast.makeText(context, "G0!!!", Toast.LENGTH_SHORT).show();
        info.setGas(true);
        constructMessage();
        repository.sendMessage(message);
    }

    /**
     * This method is called when the Gas button is released (called from the listener). It asks
     * the robot to stop and updates the boolean value with the new "gas" status.
     *
     * @param context Context The application context
     */
    public void onStop(Context context) {

        //Toast.makeText(context, "STOP!", Toast.LENGTH_SHORT).show();
        info.setGas(false);
        constructMessage();
        repository.sendMessage(message);
    }

    /**
     * This method is called when the turning angle of the robot has to be modified (called from
     * the sensor listener). It updates the current angle and
     * asks the robot to turn at the indicated angle.
     *
     * @param applicationContext Context The application context
     * @param angle String The turning angle to switch to
     * @return String The new turning angle
     */
    public String onAngleChanged(Context applicationContext, String angle) {

        /*switch (angle){
            case Constants.ANGLE_L2: if (applicationContext != null) Toast.makeText(applicationContext, "Turning left hard!", Toast.LENGTH_SHORT).show();
                //DebugUtils.debug("ACCEL", "Turning left hard!");
                break;

            case Constants.ANGLE_L1: if (applicationContext != null) Toast.makeText(applicationContext, "Turning left soft!", Toast.LENGTH_SHORT).show();
                //DebugUtils.debug("ACCEL", "Turning left soft!");
                break;

            case Constants.ANGLE_N: if (applicationContext != null) Toast.makeText(applicationContext, "Going straight forward!", Toast.LENGTH_SHORT).show();
                //DebugUtils.debug("ACCEL", "Going straight forward");
                break;

            case Constants.ANGLE_R1: if (applicationContext != null) Toast.makeText(applicationContext, "Turning right soft", Toast.LENGTH_SHORT).show();
                //DebugUtils.debug("ACCEL", "Turning right soft!");
                break;

            case Constants.ANGLE_R2: if (applicationContext != null) Toast.makeText(applicationContext, "Turning right hard!", Toast.LENGTH_SHORT).show();
                //DebugUtils.debug("ACCEL", "Turning right hard!");
                break;
        }*/

        info.setAngle(angle);
        constructMessage();
        repository.sendMessage(message);
        return angle;
    }

    /**
     * This method is called when a basic shape is recognised (called from the gesture overlay listener).
     * It shows a Toast on the screen indicating which shape has been recognised and asks the robot
     * to perform it.
     *
     * @param context Context The application context
     * @param name String The name of the recognised shape
     */
    public void onShapeDetected(Context context, String name) {

        Toast.makeText(context, "Yow drawed a " + name , Toast.LENGTH_SHORT).show();

        switch (name){

            case Constants.CIRCLE:
                info.setShape(Constants.SHAPE_C);
                break;

            case Constants.SQUARE:
                info.setShape(Constants.SHAPE_S);
                break;

            case Constants.TRIANGLE:
                info.setShape(Constants.SHAPE_T);
                break;

            default:
                info.setShape(Constants.SHAPE_N);
                break;
        }
        constructMessage();
        repository.sendMessage(message);
    }

    /**
     * This method is called when the repository passes a new received message to the viewModel.
     * It parses the received message and passes the new info to the view.
     * @param message String The received message
     * @param time String Timestamp of the received message
     */
    @Override
    public void onNewMessage(String message, String time) {

        deconstructMessage(message);
        infoMutableLiveData.postValue(info);
    }

    /**
     * This callback method is called when the communication has stopped. It does nothing right now,
     * but it is already defined for future implementations.
     */
    @Override
    public void onServiceStopped() {

    }

    /**
     * This method constructs a message of type "remote control"
     */
    private void constructMessage() {

        String mode, lights, gear, gas;

        if (info.isManual()){

            mode = Constants.RC_MAN;
        }else {

            mode = Constants.RC_AUT;
            info.setGas(false);
        }

        if (info.isLightsON()){

            lights = Constants.RC_LIGTHS_ON;
        }else {

            lights = Constants.RC_LIGTHS_OFF;
        }

        if (info.isGas()){

            gas = Constants.ACC_START;
        }else {

            gas = Constants.ACC_STOP;
        }

        switch (info.getGear()){

            case 3:
                gear = Constants.RC_GEAR_3;
                break;
            case 2:
                gear = Constants.RC_GEAR_2;
                break;
            case 1:
                gear = Constants.RC_GEAR_1;
                break;
            case -3:
                gear = Constants.RC_GEAR_B3;
                break;
            case -2:
                gear = Constants.RC_GEAR_B2;
                break;
            case -1:
                gear = Constants.RC_GEAR_B1;
                break;
            default:
                gear = Constants.RC_GEAR_0;
                break;
        }

        message = "type:" + Constants.RC_TYPE + ",mode:" + mode + ",lights:" + lights + ",gear:" + gear + ",shape:" + info.getShape() + ",angle:" + info.getAngle() + ",gas:" + gas;
    }

    /**
     * This method parses all the info from the received "remote control", "bumper" or "ultrasonic" message
     * @param msg The received message to be parsed
     */
    private void deconstructMessage(String msg) {

        String[] fields = msg.split(",");
        String[] aux = fields[Constants.TYPE_FIELD].split(":");
        String type = aux[Constants.VALUE_FIELD];
        DebugUtils.debug("PARSED", "Type-> " + type);

        switch (type){

            case Constants.TEMP_TYPE:
                aux = fields[Constants.TEMPERATURE_FIELD].split(":");
                Float temp = Float.parseFloat(aux[Constants.VALUE_FIELD]);
                info.setTemperature(temp.floatValue());
                DebugUtils.debug("PARSED", "Temperature-> " + temp);
                break;

            case Constants.US_TYPE:
                aux = fields[Constants.US_FIELD].split(":");
                Integer collision = Integer.parseInt(aux[Constants.VALUE_FIELD]);
                DebugUtils.debug("PARSED", "US-> " + collision.toString());

                if (collision.intValue() == 1){

                    info.setUltraSonic(true);
                }else {

                    info.setUltraSonic(false);
                }
                break;

            case Constants.BUMP_TYPE:
                aux = fields[Constants.BUMP_FIELD].split(":");
                String bump = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Bump-> " + bump);

                if (bump.equals(Constants.BUMP_LEFT)){

                    DebugUtils.debug("PARSED", "Right Bump-> true");
                    info.setLeftBumper(true);
                    //Create a Timer Task that will update the bumper state after the set delay
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {

                            info.setLeftBumper(false);
                        }
                    };

                    //Create a new timer, assign it to the Timer Task and set the delay
                    Timer timer = new Timer();
                    timer.schedule(timerTask, Constants.BUMPER_SCREEN_DELAY);

                }else if (bump.equals(Constants.BUMP_RIGHT)){

                    DebugUtils.debug("PARSED", "Left Bump-> true");
                    info.setRightBumper(true);
                    //Create a Timer Task that will update the bumper state after the set delay
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {

                            info.setRightBumper(false);
                        }
                    };

                    //Create a new timer, assign it to the Timer Task and set the delay
                    Timer timer = new Timer();
                    timer.schedule(timerTask, Constants.BUMPER_SCREEN_DELAY);
                }
                break;

            case Constants.RC_TYPE:
                aux = fields[Constants.MANUAL_FIELD].split(":");
                String value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Aut-> " + value);

                if (Integer.parseInt(value) == 1){

                    DebugUtils.debug("PARSED", "Aut-> true");
                    info.setManual(false);
                }else {

                    DebugUtils.debug("PARSED", "Aut-> false");
                    info.setManual(true);
                }

                //Ignore BUMPER status in the "remote control" message

                /*aux = fields[Constants.RIGHTB_FIELD].split(":");
                value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Right bumper-> " + value);
                if (Integer.parseInt(value) == 1){

                    info.setRightBumper(true);
                }else {

                    info.setRightBumper(false);
                }
                aux = fields[Constants.LEFTTB_FIELD].split(":");
                value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Left bumper-> " + value);

                if (Integer.parseInt(value) == 1){

                    info.setLeftBumper(true);
                }else {

                    info.setLeftBumper(false);
                }*/


                aux = fields[Constants.USONIC_FIELD].split(":");
                value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Ultrasonic-> " + value);

                if (Integer.parseInt(value) == 1){

                    info.setUltraSonic(true);
                }else {

                    info.setUltraSonic(false);
                }

                aux = fields[Constants.GEAR_FIELD].split(":");
                value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Gear-> " + value);

                switch (value){

                    case Constants.RC_GEAR_3:
                        info.setGear(3);
                        break;

                    case Constants.RC_GEAR_2:
                        info.setGear(2);
                        break;

                    case Constants.RC_GEAR_1:
                        info.setGear(1);
                        break;

                    case Constants.RC_GEAR_B3:
                        info.setGear(-3);
                        break;

                    case Constants.RC_GEAR_B2:
                        info.setGear(-2);
                        break;

                    case Constants.RC_GEAR_B1:
                        info.setGear(-1);
                        break;

                    default:
                        info.setGear(0);
                }
                info.setSpeed(calculateSpeed(info.getGear()));

                aux = fields[Constants.LIGHTS_FIELD].split(":");
                value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Lights-> " + value);

                if (Integer.parseInt(value) == 1){

                    info.setLightsON(true);
                }else {

                    info.setLightsON(false);
                }

                aux = fields[Constants.SHAPE_FIELD].split(":");
                value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Shape-> " + value);
                info.setShape(value);

                aux = fields[Constants.ANGLE_FIELD].split(":");
                value = aux[Constants.VALUE_FIELD];
                DebugUtils.debug("PARSED", "Angle-> " + value);
                info.setAngle(value);

                aux = fields[Constants.TEMP_FIELD].split(":");
                Float temperature = Float.parseFloat(aux[Constants.VALUE_FIELD]);
                DebugUtils.debug("PARSED", "Temp-> " + temperature);
                info.setTemperature(temperature.floatValue());
                break;

        }
    }

}
