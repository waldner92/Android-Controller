package com.android.metg2.androidcontroller.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.MainMenuActivity;
import com.android.metg2.androidcontroller.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The initial Splash Activity, it runs for 1 second before accessing MainMenuActivity.
 *
 * @author  Adria Acero, Adria Malloqui, Jordi Miro
 * @version 1.0
 */
public class SplashFragment extends android.support.v4.app.Fragment {

    public static SplashFragment newInstance(){
        return new SplashFragment();
    }

    /**
     * OnCreate Method from Fragment.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_splash,container,false);
        //Create a Timer Task that will trigger the MainMenuActivity after the set delay
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                //Create the intent that will start the MainMenuActivity
                Intent intent = new Intent(getContext(), MainMenuActivity.class);
                startActivity(intent);
                //finish(); //We don't want to show this activity again
            }
        };

        //Create a new timer, assign it to the Timer Task and set the delay
        Timer timer = new Timer();
        timer.schedule(timerTask, Constants.SPLASH_SCREEN_DELAY);
        return v;
    }
}
