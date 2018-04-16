package com.android.metg2.androidcontroller.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.AccelerometerFragment;

/**
 * Accelerometer Activity. This class shows on the screen the values of the accelerometer received
 * from the Arduino.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class AccelerometerActivity extends AppCompatActivity {

    private String ACCELEROMETER_FRAGMENT = "ACCELEROMETER_FRAGMENT";

    /**
     * onCreate method from the activity.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR); //Ask for the action bar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Orientation set to landscape
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer); //Get the layout
        ActionBar actBar = getSupportActionBar(); //Get the action Bar
        actBar.setDisplayHomeAsUpEnabled(true); //Display the up (back) button

        initFragment();
    }

    /**
     * This method initializes the activity's fragment.
     */
    private void initFragment() {

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = manager.findFragmentByTag(ACCELEROMETER_FRAGMENT);

        if (fragment == null){

            fragment = AccelerometerFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_accelerometer_container,fragment, ACCELEROMETER_FRAGMENT);
        transaction.commit();
    }

    /**
     * onStop method from the activity.
     */
    @Override
    public void onStop(){

        super.onStop();
    }

    /**
     * onResume method from the activity.
     */
    @Override
    public void onResume() {

        super.onResume();
    }
}
