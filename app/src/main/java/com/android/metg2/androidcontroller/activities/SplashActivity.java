package com.android.metg2.androidcontroller.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.SplashFragment;
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

    private String SPLASH_FRAGMENT = "SPLASH_FRAGMENT";

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

        initFragment();
    }

    private void initFragment() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = manager.findFragmentByTag(SPLASH_FRAGMENT);
        if (fragment == null){

            fragment = SplashFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_splash_container,fragment,SPLASH_FRAGMENT);
        transaction.commit();
    }
}
