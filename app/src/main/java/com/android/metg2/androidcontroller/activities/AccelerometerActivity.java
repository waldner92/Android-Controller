package com.android.metg2.androidcontroller.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.AccelerometerFragment;
import com.android.metg2.androidcontroller.fragments.MainMenuFragment;

public class AccelerometerActivity extends AppCompatActivity {

    private String ACCELEROMETER_FRAGMENT = "ACCELEROMETER_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

        initFragment();
    }

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

    @Override
    public void onStop(){

        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
