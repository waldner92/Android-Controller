package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;

/**
 * The viewModel of the Remote Control Activity. This class performs the actions when the view listeners are
 * triggered. It will communicate with the robot in the future, but right now it just shows toasts
 * on the screen and update some values of the view.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class RemoteControlViewModel extends ViewModel {

    /**
     * The constructor method.
     */
    public RemoteControlViewModel(){}

    /**
     * This method is called when the Aut/Man button is pressed (called from the listener). It shows a
     * Toast on the screen indicating that driving mode has changed and updates the boolean value with
     * the new driving mode.
     *
     * @param context Context The application context
     * @param isManual boolean True if the current mode is manual and false if it is automatic
     * @return boolean The new driving mode
     */
    public boolean onAutManPressed(Context context, boolean isManual) {

        if (isManual){

            Toast.makeText(context, "Changed RC mode to automatic", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(context, "Changed RC mode to manual", Toast.LENGTH_SHORT).show();
        }

        return !isManual;
    }

    /**
     * This method is called when the Lights button is pressed (called from the listener). It shows a
     * Toast on the screen indicating that lights have been turned on/off and updates the boolean value with
     * the new lights status.
     *
     * @param context Context The application context
     * @param lightsON boolean True if the lights are currently on and false if they are off
     * @return boolean The new lights status
     */
    public boolean onLightsPressed(Context context, boolean lightsON) {

        if (lightsON){

            Toast.makeText(context, "Lights turned OFF", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(context, "Lights turned ON", Toast.LENGTH_SHORT).show();
        }

        return !lightsON;
    }

    /**
     * This method is called when the Gear Up button is pressed (called from the listener). It increases the
     * value of the robot's driving gear
     * @param context Conext The application context
     * @param gear int The current gear
     * @return int The new gear
     */
    public int onGearUpPressed(Context context, int gear) {

        //control if the current gear is already the maximum and update its value if it is not
        if (gear < Constants.MAX_GEAR){

            gear++;
        }

        return gear;
    }

    /**
     * This method is called when the Gear Down button is pressed (called from the listener). It decreases the
     * value of the robot's driving gear
     * @param context Context The application context
     * @param gear int The current gear
     * @return int The new gear
     */
    public int onGearDownPressed(Context context, int gear) {

        //control if the current gear is already the minimum and update its value if it is not
        if (gear > Constants.MIN_GEAR){

            gear--;
        }

        return gear;
    }

    /**
     * This method calculates the robot's driving speed depending on its current gear.
     * @param gear int The current gear
     * @return int The current robot's speed
     */
    public int calculateSpeed(int gear) {

        return gear*10;
    }

    /**
     * This method is called when the Gas button is being pressed (called from the listener). It shows a
     * Toast on the screen indicating that the robot is accelerating and updates the boolean value with
     * the new "gas" status.
     *
     * @param context Context The application context
     * @return boolean Always true, indicating that the robot is accelerating.
     */
    public boolean onGas(Context context) {

        Toast.makeText(context, "G0!!!", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * This method is called when the Gas button is released (called from the listener). It shows a
     * Toast on the screen indicating that the robot has stopped and updates the boolean value with
     * the new "gas" status.
     *
     * @param context Context The application context
     * @return boolean Always false, indicating that the robot is stopped.
     */
    public boolean onStop(Context context) {

        Toast.makeText(context, "STOP!", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * This method is called when the turning angle of the robot has to be modified (called from
     * the sensor listener). It shows a Toast on the screen indicating how the robot is moving and
     * returns the string of the new turning angle.
     *
     * @param applicationContext Context The application context
     * @param angle String The turning angle to switch to
     * @return String The new turning angle
     */
    public String onAngleChanged(Context applicationContext, String angle) {

        switch (angle){
            case Constants.ANGLE_L2: if (applicationContext != null) Toast.makeText(applicationContext, "Turning left hard!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning left hard!");
                break;

            case Constants.ANGLE_L1: if (applicationContext != null) Toast.makeText(applicationContext, "Turning left soft!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning left soft!");
                break;

            case Constants.ANGLE_N: if (applicationContext != null) Toast.makeText(applicationContext, "Going straight forward!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Going straight forward");
                break;

            case Constants.ANGLE_R1: if (applicationContext != null) Toast.makeText(applicationContext, "Turning right soft", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning right soft!");
                break;

            case Constants.ANGLE_R2: if (applicationContext != null) Toast.makeText(applicationContext, "Turning right hard!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning right hard!");
                break;
        }

        return angle;
    }

    /**
     * This method is called when a basic shape is recognised (called from the gesture overlay listener).
     * It shows a Toast on the screen indicating which shape has been recognised.
     * @param context Context The application context
     * @param name String The name of the recognised shape
     */
    public void onShapeDetected(Context context, String name) {

        Toast.makeText(context, "Yow drawed a " + name , Toast.LENGTH_SHORT).show();
    }
}
