package com.android.metg2.androidcontroller.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.RemoteControlFragment;
import com.android.metg2.androidcontroller.fragments.SplashFragment;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.RemoteControlViewModel;

import java.util.ArrayList;

public class RemoteControlActivity extends AppCompatActivity{

    private String REMOTE_CONTROL_FRAGMENT = "REMOTE_CONTROL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

        initFragment();
    }

    private void initFragment() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = manager.findFragmentByTag(REMOTE_CONTROL_FRAGMENT);
        if (fragment == null){

            fragment = RemoteControlFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_remote_control_container,fragment,REMOTE_CONTROL_FRAGMENT);
        transaction.commit();
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
