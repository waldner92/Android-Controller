package com.android.metg2.androidcontroller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.MainMenuActivity;
import com.android.metg2.androidcontroller.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Splash Fragment. It is the actual view of the Splash Activity.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class SplashFragment extends android.support.v4.app.Fragment {

    /**
     * Method that returns a new constructed Splash Fragment.
     * @return SplashFragment The Splash Fragment
     */
    public static SplashFragment newInstance(){
        return new SplashFragment();
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
     * @param savedInstanceState Bundle
     */
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * onCreateView method from the fragment. It sets the fragment layout (the splash screen) and schedules a Timer Task that
     * will trigger the Main Menu Activity after a short period of time.
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstance Bundle
     * @return View the fragment view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_splash,container,false); //get the corresponding view

        //Create a Timer Task that will trigger the MainMenuActivity after the set delay
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                //Create the intent that will start the MainMenuActivity
                Intent intent = new Intent(getContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        };

        //Create a new timer, assign it to the Timer Task and set the delay
        Timer timer = new Timer();
        timer.schedule(timerTask, Constants.SPLASH_SCREEN_DELAY);

        return v;
    }
}
