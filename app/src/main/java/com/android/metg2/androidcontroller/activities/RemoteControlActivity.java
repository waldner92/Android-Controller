package com.android.metg2.androidcontroller.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.RemoteControlFragment;

/**
 * Remote Control Activity. This activity displays all the options to control the Arduino Robot,
 * including the turning (accelerometer) control and the shape recognition.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class RemoteControlActivity extends AppCompatActivity{

    private String REMOTE_CONTROL_FRAGMENT = "REMOTE_CONTROL_FRAGMENT";

    /**
     * onCreate Method from the activity.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR); //Request for the action bar

        setContentView(R.layout.activity_remote_control); //Get the corresponding layout
        ActionBar actBar = getSupportActionBar(); //Get the action bar
        actBar.setDisplayHomeAsUpEnabled(true); //Enable the up (back) button

        initFragment();
    }

    /**
     * This method initializes the activity's fragment.
     */
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
}
