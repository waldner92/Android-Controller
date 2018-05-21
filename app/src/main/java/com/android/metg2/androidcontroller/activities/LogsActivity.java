package com.android.metg2.androidcontroller.activities;


import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.LogsFragment;


/**
 * Logs Activity. It shows all logs from the communication with the Arduino robot.
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class LogsActivity extends AppCompatActivity {

    private String LOGS_FRAGMENT = "LOGS_FRAGMENT";
    android.support.v4.app.Fragment fragment;

    /**
     * OnCreate Method from the activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR); //Request for the action bar
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Set the orientation to portrait
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs); //Get the corresponding layout
        ActionBar actBar = getSupportActionBar(); ////Get the action bar
        actBar.setDisplayHomeAsUpEnabled(true); //Enable the up (back) button

        initFragment();
    }

    /**
     * This method initializes the activity's fragment.
     */
    private void initFragment() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentByTag(LOGS_FRAGMENT);

        if (fragment == null){

            fragment = LogsFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_logs_container,fragment,LOGS_FRAGMENT);
        transaction.commit();
    }


    /**
     * onOptionItemSelected method from the activity. When the up button is pressed, it is needed to
     * call the onStop method from the fragment
     *
     * @param item The item from the action bar that has been pressed.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: //Handle Up button
                com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in view");
                fragment.onStop();
                return super.onOptionsItemSelected(item);

            default: return super.onOptionsItemSelected(item);
        }
    }
}

