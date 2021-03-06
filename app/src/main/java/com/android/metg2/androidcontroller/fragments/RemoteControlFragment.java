package com.android.metg2.androidcontroller.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.MainMenuActivity;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.utils.RemoteControlInfo;
import com.android.metg2.androidcontroller.viewmodels.LogsViewModel;
import com.android.metg2.androidcontroller.viewmodels.RemoteControlViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Arrays.asList;

/**
 * Remote Control Fragment. It is the actual view of the Remote Control Activity. It implements the
 * SensorEvent Listener to obtain the mobible's accelerometer values and the OnGesturePerformedListener to
 * recognise shapes drawed on the screen. When a user asks for an action to control the Arduino, the
 * fragment asks the viewModel to sent a message with the corresponding order to the robot.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 2.0
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
    private static View bumperLeftView;

    /**
     * Shows the collision detection from the right bumper
     */
    private static View bumperRightView;

    /**
     * Shows the collision detection from the ultrasound sensor
     */
    private static View ultraSoundView;

    /**
     * Shows the received temperature from the robot
     */
    private static TextView temperatureTextView;

    /**
     * Show the lights state from the robot
     */
    private static TextView lightsTextView;

    /**
     * Shows the current gear from the robot
     */
    private static TextView gearsTextView;

    /**
     * Shows the current speed from the robot
     */
    private static TextView speedTextView;

    /**
     * The current gear
     */
    private static int gear;

    /**
     * The current speed
     */
    private static int speed;

    /**
     * True if robot lights are on and false if they are off
     */
    private static boolean lightsON;

    /**
     * True if the current driving mode is manual and false if it is automatic
     */
    private static boolean isManual;

    /**
     * The received temperature
     */
    private static float temperature;

    /**
     * True if the robot is accelerating and false if it is stopped
     */
    private static boolean gas;

    /**
     * True if the robot detects collision danger with the ultrasonic sensor and false otherwise
     */
    private static boolean ultraSonic;

    /**
     * True if the robot detects collision with the right bumper and false otherwise
     */
    private static boolean rightBumper;

    /**
     * True if the robot detects collision with the left bumper and false otherwise
     */
    private static boolean leftBumper;

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
     * The gesture library
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
     * Contains the current status of all items showed on the screen
     */
    private static Observer<RemoteControlInfo> rcInfo;

    /**
     * Drawable style for the buttons shown in the screen
     */
    private static Drawable buttonStyle;


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

        //Group the buttons that must be disabled when the robot is in automatic mode
        final List<Button> deactivateButtons = asList(gearUpButton, gearDownButton,
                gasButton, lightsButton);

        //Initialize the views
        bumperLeftView = v.findViewById(R.id.bumper_left);
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
        buttonStyle = this.getResources().getDrawable(R.drawable.button_style);

        //Get the accelerometer sensor and register its listener
        sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        initViewModel(deactivateButtons); //Start the communication service

        viewModel.showInfo(getContext()).observe(this, rcInfo); //Refresh continuously the list of logs

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

                viewModel.onAutManPressed(v.getContext(), isManual);
            }
        });

        //When the Lights Button is pressed, we ask the robot to switch them
        lightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.onLightsPressed(v.getContext(), lightsON);
            }
        });

        //When the Gear Up Button is pressed, we ask the robot to increase its gear (and speed)
        gearUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.onGearUpPressed(v.getContext(), gear);
                viewModel.calculateSpeed(gear);
            }
        });

        //When the Gear Down Button is pressed, we ask the robot to decrease its gear (and speed)
        gearDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.onGearDownPressed(v.getContext(), gear);
                viewModel.calculateSpeed(gear);
            }
        });

        //When the Gas Button is pressed, we ask the robot to accelerate. When it it is released, we
        //ask the robot to stop.
        gasButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    viewModel.onGas(v.getContext()); //accelerate
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    viewModel.onStop(v.getContext()); //stop
                }
                return false;
            }
        });

        return v;
    }


    /**
     * This method is executed everytime that the values
     * of the accelerometer of the mobile change. It also calculates the turning angle to request to
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
     * This method refreshes the info of the remote control status that is showed on the screen.
     * @param info The object that contains all the info
     * @param deactivateButtons The list of buttons to deactivate when the remote control mode is automatic
     */
    public static void showInfo(RemoteControlInfo info, List<Button> deactivateButtons) {

        //If the left bumpers detects collision change the background color for some time
        if (info.isLeftBumper()){

            leftBumper = true;
            bumperLeftView.setBackgroundColor(Color.RED);

            //Create a Timer Task that will change the background color after the desired delay
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {


                    Activity activity = (Activity)bumperLeftView.getContext();

                    activity.runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            // update ui here
                            leftBumper = false;
                            bumperLeftView.setBackgroundColor(Color.BLUE);

                        }
                    });

                }
            };

            //Create a new timer, assign it to the Timer Task and set the delay
            Timer timer = new Timer();
            timer.schedule(timerTask, Constants.BUMPER_SCREEN_DELAY);

        }else {

            leftBumper = false;
            bumperLeftView.setBackgroundColor(Color.BLUE);
        }

        //Same for the right bumper
        if (info.isRightBumper()){

            rightBumper = true;
            bumperRightView.setBackgroundColor(Color.RED);

            //Create a Timer Task that will change the background color after the desired delay
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {


                    Activity activity = (Activity)bumperRightView.getContext();

                    activity.runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            // update ui here
                            rightBumper = false;
                            bumperRightView.setBackgroundColor(Color.BLUE);
                        }
                    });

                }
            };

            //Create a new timer, assign it to the Timer Task and set the delay
            Timer timer = new Timer();
            timer.schedule(timerTask, Constants.BUMPER_SCREEN_DELAY);

        }else {

            rightBumper = false;
            bumperRightView.setBackgroundColor(Color.BLUE);
        }

        //Update the background of the UltraSonic Collision view depending on its value
        if (info.isUltraSonic()){

            ultraSonic = true;
            ultraSoundView.setBackgroundColor(Color.RED);
        }else{

            ultraSonic = false;
            ultraSoundView.setBackgroundColor(Color.BLUE);
        }

        //update the lights status
        if (info.isLightsON()){

            lightsON = true;
            lightsTextView.setText(R.string.on);
        }else {

            lightsON = false;
            lightsTextView.setText(R.string.off);
        }

        //Update the temperature, gear and speed
        temperature = info.getTemperature();
        gear = info.getGear();
        speed = info.getSpeed();
        String gearString = "Gear: " + gear;
        String speedString = "Speed: " + speed + " Km/h";
        String tempString = temperature + " ºC";
        gearsTextView.setText(gearString);
        speedTextView.setText(speedString);
        temperatureTextView.setText(tempString);

        //Check if we are in automatic or manual mode
        if (isManual && !info.isManual()){

            isManual = false;
        }else if (!isManual && info.isManual()){

            isManual = true;
        }

        // We deactivate some buttons if we are riding in automatic and viceversa.
        for (Button button : deactivateButtons){

            button.setEnabled(isManual);

            if (!isManual){

                button.setBackgroundColor(Color.DKGRAY);
            }else {

                button.setBackground(buttonStyle);
            }
        }

    }

    /**
     * This method initializes the Remote Control viewModel.
     * @param deactivateButtons The list of buttons to deactivate when the remote control mode is automatic
     */
    private void initViewModel(final List<Button> deactivateButtons){

        viewModel = ViewModelProviders.of(this).get(RemoteControlViewModel.class);
        rcInfo = new Observer<RemoteControlInfo>() {
            @Override
            public void onChanged(@Nullable RemoteControlInfo info) {
                showInfo(info, deactivateButtons);
            }
        };
    }


    /**
     * onStop method from the fragment.
     */
    @Override
    public void onStop(){

        viewModel.stopRemoteControl(getContext()); //Stop the communication service
        super.onStop();
        getActivity().finish();
    }

}
