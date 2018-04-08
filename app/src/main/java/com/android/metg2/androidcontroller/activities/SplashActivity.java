package com.android.metg2.androidcontroller.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The initial Splash Activity, it runs for 1 second before accessing MainMenuActivity.
 *
 * @author  Adria Acero, Adria Malloqui, Jordi Miro
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Set orientation
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //Erase title from this activity
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        //Do this after pre-configuration to avoid app crashing
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        //Create a Timer Task that will trigger the MainMenuActivity after the set delay
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                //Create the intent that will start the MainMenuActivity
                Intent intent = new Intent(SplashActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish(); //We don't want to show this activity again
            }
        };

        //Create a new timer, assign it to the Timer Task and set the delay
        Timer timer = new Timer();
        timer.schedule(timerTask, Constants.SPLASH_SCREEN_DELAY);
    }
}
