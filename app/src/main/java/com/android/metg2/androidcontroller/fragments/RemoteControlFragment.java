package com.android.metg2.androidcontroller.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.MazeViewModel;
import com.android.metg2.androidcontroller.viewmodels.RemoteControlViewModel;

import java.util.ArrayList;

public class RemoteControlFragment extends Fragment implements SensorEventListener, GestureOverlayView.OnGesturePerformedListener{

    private Button autManButton;
    private Button lightsButton;
    private Button gearUpButton;
    private Button gearDownButton;
    private Button gasButton;

    private View bumberLeftView;
    private View bumperRightView;
    private View ultraSoundView;
    private TextView temperatureTextView;
    private TextView lightsTextView;
    private TextView gearsTextView;
    private TextView speedTextView;

    private int gear;
    private int speed;
    private boolean lightsON;
    private boolean isManual;
    private int temperature;
    private boolean gas;
    private String angle;
    private String current_angle;
    private double gY;

    private Sensor accelerometer;
    private SensorManager sManager;

    private GestureLibrary gestureLibrary;
    private GestureOverlayView gestureOverlayView;

    private RemoteControlViewModel viewModel;

    public static RemoteControlFragment newInstance(){
        return new RemoteControlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_remote_control,container,false);

        autManButton = v.findViewById(R.id.autMan_button);
        lightsButton = v.findViewById(R.id.lights_button);
        gearUpButton = v.findViewById(R.id.gear_up_button);
        gearDownButton = v.findViewById(R.id.gear_down_button);
        gasButton = v.findViewById(R.id.gas_button);

        bumberLeftView = v.findViewById(R.id.bumper_left);
        bumperRightView = v.findViewById(R.id.bumper_right);
        ultraSoundView = v.findViewById(R.id.ultrasound);
        temperatureTextView = v.findViewById(R.id.temperature);
        lightsTextView = v.findViewById(R.id.lights_status);
        gearsTextView = v.findViewById(R.id.gears);
        speedTextView = v.findViewById(R.id.speed);

        speed = 0;
        gear = 0;
        temperature = 10;
        lightsON = false;
        isManual = true;
        gas = false;
        angle = Constants.ANGLE_N;
        current_angle = Constants.ANGLE_N;
        gY = 0;

        sManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        viewModel = new RemoteControlViewModel();

        gestureOverlayView = v.findViewById(R.id.gestureOverlay);
        if (gestureOverlayView == null){
            DebugUtils.debug("Gesture Overlay", "is NULL");
        }
        gestureOverlayView.addOnGesturePerformedListener(this);
        gestureLibrary = GestureLibraries.fromRawResource(getContext(), R.raw.gesture);
        if (!gestureLibrary.load()) {
            DebugUtils.debug("Gesture", "GestureLib not loaded");
            getActivity().finish();
        }


        autManButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it returns the value of isManual, but in future implementations this value will
                //be returned after receiving the corresponding message from the Arduino
                isManual = viewModel.onAutManPressed(v.getContext(), isManual);
            }
        });

        lightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it returns the value of lightsON, but in future implementations this value will
                //be returned after receiving the corresponding message from the Arduino
                lightsON = viewModel.onLightsPressed(v.getContext(), lightsON);
                if (lightsON){

                    lightsTextView.setText(getString(R.string.on));
                }else {

                    lightsTextView.setText(getString(R.string.off));
                }
            }
        });

        gearUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it returns the value of gear, but in future implementations this value will
                //be returned after receiving the corresponding message from the Arduino
                gear = viewModel.onGearUpPressed(v.getContext(), gear);
                speed = viewModel.calculateSpeed(gear);
                setTextviews();
            }
        });

        gearDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now it returns the value of gear, but in future implementations this value will
                //be returned after receiving the corresponding message from the Arduino
                gear = viewModel.onGearDownPressed(v.getContext(), gear);
                speed = viewModel.calculateSpeed(gear);
                setTextviews();
            }
        });

        /*gasButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //now it returns the value of gas, but in future implementations this value will
                //be updated after receiving the response message from the Arduino
                gas = viewModel.onGas(v.getContext());
                return true;
            }
        });*/

        gasButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    gas = viewModel.onGas(v.getContext());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gas = viewModel.onStop(v.getContext());
                }
                return false;
            }
        });

        return v;
    }

    public void setTextviews(){

        String gearString = "Gear: " + gear;
        String speedString = "Speed: " + speed + " Km/h";
        String tempString = temperature + " ºC";

        gearsTextView.setText(gearString);
        speedTextView.setText(speedString);
        temperatureTextView.setText(tempString);
    }

    /**
     * This method will be executed everytime that the values
     * of the accelerometer of the smartphone change.
     *
     * In this case, it also calculates the angle the robot will turn.
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

            gY = alpha * gY + (1 - alpha) * event.values[1];
            //DebugUtils.debug("ACCEL", "gY is " + gY);

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

            if (!current_angle.equals(angle)){

                angle = viewModel.onAngleChanged(getContext(), current_angle);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * It detects the gesture the user has drawn on the screen.
     *
     * @param overlay GestureOverlayView
     * @param gesture Gesture
     */
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
        Prediction prediction = predictions.get(0);
        DebugUtils.debug("Shape Pred", "Score is " + prediction.score + " and name is " + prediction.name);


        if (prediction.score > 1.0 && prediction.name.length() > 1) {

            viewModel.onShapeDetected(getContext(), prediction.name);
        }
    }

    @Override
    public void onStop(){

        super.onStop();

    }



    @Override
    public void onResume() {
        super.onResume();

    }
}
