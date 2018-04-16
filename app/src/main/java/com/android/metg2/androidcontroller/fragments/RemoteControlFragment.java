package com.android.metg2.androidcontroller.fragments;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.RemoteControlViewModel;

import java.util.ArrayList;

/**
 * Remote Control Fragment. It is the actual view of the Remote Control Activity. It implements the
 * SensorEvent Listener to obtain the mobible's accelerometer values and the OnGesturePerformedListener to
 * recognise shapes drawed on the screen.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class RemoteControlFragment extends Fragment implements SensorEventListener, GestureOverlayView.OnGesturePerformedListener{

    /**
     * Button that switches between the manual and automatic driving modes
     */
    private Button autManButton;

    /**
     * Button that asks to turn on or turn off the robot lightd
     */
    private Button lightsButton;

    /**
     * Button that asks to increase one gear up
     */
    private Button gearUpButton;

    /**
     * Button that asks to decrease one gear down
     */
    private Button gearDownButton;

    /**
     * Button used to accelerate the robot
     */
    private Button gasButton;

    /**
     * Shows the collision detection from the left bumper
     */
    private View bumberLeftView;

    /**
     * Shows the collision detection from the right bumper
     */
    private View bumperRightView;

    /**
     * Shows the collision detection from the ultrasound sensor
     */
    private View ultraSoundView;

    /**
     * Shows the received temperature from the robot
     */
    private TextView temperatureTextView;

    /**
     * Show the lights state from the robot
     */
    private TextView lightsTextView;

    /**
     * Shows the current gear from the robot
     */
    private TextView gearsTextView;

    /**
     * Shows the current speed from the robot
     */
    private TextView speedTextView;

    /**
     * The current gear
     */
    private int gear;

    /**
     * The current speed
     */
    private int speed;

    /**
     * True if robot lights are on and false if they are off
     */
    private boolean lightsON;

    /**
     * True if the current driving mode is manual and false if it is automatic
     */
    private boolean isManual;

    /**
     * The received temperature
     */
    private int temperature;

    /**
     * True if the robot is accelerating and false if it is stopped
     */
    private boolean gas;

    /**
     * The current turning angle of the robot
     */
    private String angle;

    /**
     * The current turning angle readed from the mobile's accelerometer
     */
    private String current_angle;

    /**
     * The Gy mobile's accelerometer read value
     */
    private double gY;

    /**
     * The accelerometer sensor
     */
    private Sensor accelerometer;

    /**
     * The sensor manager
     */
    private SensorManager sManager;

    /**
     * The gesture libray
     */
    private GestureLibrary gestureLibrary;

    /**
     * An overlay view to perform gesture recognition
     */
    private GestureOverlayView gestureOverlayView;

    /**
     * The viewModel
     */
    private RemoteControlViewModel viewModel;

    /**
     * Method that returns a new constructed Remote Control Fragment.
     * @return RemoteControlFragment The Remote Control Fragment
     */
    public static RemoteControlFragment newInstance(){
        return new RemoteControlFragment();
    }

    /**
     * onCreate method from the fragment.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    /**
     * onActivityCreated method from the fragment.
     *
     * @param savedInstanceState
     */
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * onCreateView method from the fragment. It sets the fragment layout and binds the views. It also
     * defines the button listeners, the accelerometer listener and the gesture overlay view (and its listener).
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstance Bundle
     * @return View the fragment view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_remote_control,container,false); //get the corresponding layout

        //Initialize the buttons
        autManButton = v.findViewById(R.id.autMan_button);
        lightsButton = v.findViewById(R.id.lights_button);
        gearUpButton = v.findViewById(R.id.gear_up_button);
        gearDownButton = v.findViewById(R.id.gear_down_button);
        gasButton = v.findViewById(R.id.gas_button);

        //Initialize the views
        bumberLeftView = v.findViewById(R.id.bumper_left);
        bumperRightView = v.findViewById(R.id.bumper_right);
        ultraSoundView = v.findViewById(R.id.ultrasound);
        temperatureTextView = v.findViewById(R.id.temperature);
        lightsTextView = v.findViewById(R.id.lights_status);
        gearsTextView = v.findViewById(R.id.gears);
        speedTextView = v.findViewById(R.id.speed);

        //Initialize data variables
        speed = 0;
        gear = 0;
        temperature = 10;
        lightsON = false;
        isManual = true;
        gas = false;
        angle = Constants.ANGLE_N;
        current_angle = Constants.ANGLE_N;
        gY = 0;

        //Get the acceleremoter sensor and register its listener
        sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        viewModel = new RemoteControlViewModel(); //initialize the viewModel

        //Create the gesture overlay view and register its listener
        gestureOverlayView = v.findViewById(R.id.gestureOverlay);
        if (gestureOverlayView == null) DebugUtils.debug("Gesture Overlay", "is NULL");
        gestureOverlayView.addOnGesturePerformedListener(this);

        //Load the gesture library that has the three reference shapes
        gestureLibrary = GestureLibraries.fromRawResource(getContext(), R.raw.gesture);

        if (!gestureLibrary.load()) {

            DebugUtils.debug("Gesture", "GestureLib not loaded");
            getActivity().finish();
        }

        //When the AutMan Button is pressed, we ask the robot to modify its driving mode
        autManButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it updates the value of isManual, but in future implementations this value will
                //be updated after receiving the corresponding message from the Arduino
                isManual = viewModel.onAutManPressed(v.getContext(), isManual);
            }
        });

        //When the Lights Button is pressed, we ask the robot to switch them
        lightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it updates the value of lightsON, but in future implementations this value will
                //be updated after receiving the corresponding message from the Arduino
                lightsON = viewModel.onLightsPressed(v.getContext(), lightsON);

                if (lightsON){

                    lightsTextView.setText(getString(R.string.on));
                }else {

                    lightsTextView.setText(getString(R.string.off));
                }
            }
        });

        //When the Gear Up Button is pressed, we ask the robot to increase its gear (and speed)
        gearUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it updates the value of gear, but in future implementations this value will
                //be updated after receiving the corresponding message from the Arduino
                gear = viewModel.onGearUpPressed(v.getContext(), gear);
                speed = viewModel.calculateSpeed(gear);
                setTextviews();
            }
        });

        //When the Gear Down Button is pressed, we ask the robot to decrease its gear (and speed)
        gearDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it updates the value of gear, but in future implementations this value will
                //be updated after receiving the corresponding message from the Arduino
                gear = viewModel.onGearDownPressed(v.getContext(), gear);
                speed = viewModel.calculateSpeed(gear);
                setTextviews();
            }
        });

        //When the Gas Button is pressed, we ask the robot to accelerate. When it it is released, we
        //ask the robot to stop.
        gasButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //now it updates the value of gear, but in future implementations this value will
                //be updated after receiving the corresponding message from the Arduino
                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    gas = viewModel.onGas(v.getContext()); //accelerate
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    gas = viewModel.onStop(v.getContext()); //stop
                }
                return false;
            }
        });

        return v;
    }

    /**
     * This method refreshes the values of the Gear, Speed and Temperature textViews
     */
    public void setTextviews(){

        String gearString = "Gear: " + gear;
        String speedString = "Speed: " + speed + " Km/h";
        String tempString = temperature + " ºC";

        gearsTextView.setText(gearString);
        speedTextView.setText(speedString);
        temperatureTextView.setText(tempString);
    }

    /**
     * This method is executed everytime that the values
     * of the accelerometer of the mobile change. It also calculates the turning angle to rquest to
     * the robot.
     *
     * @param event SensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        final double alpha = 0.9;

        if (event.sensor.getType()== Sensor.TYPE_ACCELEROMETER){

            gY = alpha * gY + (1 - alpha) * event.values[1]; //apply a low-pass filter to the read value from the accelerometer

            //Set the value of the current read angle depending on the Gy value
            if (gY < -6) {

                current_angle = Constants.ANGLE_L2;

            } else if (-6 < gY && gY < -3) {

                current_angle = Constants.ANGLE_L1;

            } else if (-3 < gY && gY < 3) {

                current_angle = Constants.ANGLE_N;

            } else if (3 <  gY && gY < 6) {

                current_angle = Constants.ANGLE_R1;

            } else {

                current_angle = Constants.ANGLE_R2;
            }

            //If the angle has changed from the current turning angle of the robot, ask it to change its turning angle
            if (!current_angle.equals(angle)){

                //now it updates the value of angle, but in future implementations this value will
                //be updated after receiving the corresponding message from the Arduino
                angle = viewModel.onAngleChanged(getContext(), current_angle);
            }
        }
    }

    /**
     * onAccuracyChanged method from the fragment. It must be overwritten to implement the SensorEventListener
     * @param sensor Sensor
     * @param accuracy int
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * It detects the gesture the user has drawn on the screen.
     *
     * @param overlay GestureOverlayView
     * @param gesture Gesture
     */
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture); //get the recognized shapes
        Prediction prediction = predictions.get(0); //get the recognized shape with highest score
        DebugUtils.debug("Shape Pred", "Score is " + prediction.score + " and name is " + prediction.name);

        //We assume that a good recognition must have a score higher than 1
        if (prediction.score > 1.0 && prediction.name.length() > 1) {

            viewModel.onShapeDetected(getContext(), prediction.name); //Ask the robot to reproduce the drawn shape
        }
    }

    /**
     * onStop method from the fragment.
     */
    @Override
    public void onStop(){

        super.onStop();
    }

    /**
     * onResume method from the fragment.
     */
    @Override
    public void onResume() {

        super.onResume();
    }
}
