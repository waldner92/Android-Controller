package com.android.metg2.androidcontroller.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DebugUtils;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.LogsFragment;
import com.android.metg2.androidcontroller.fragments.MainMenuFragment;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.viewmodels.LogsViewModel;

/**
 * This Activity shows all generated logs form the communication with the Arduino robot
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class LogsActivity extends AppCompatActivity {

    private String LOGS_FRAGMENT = "LOGS_FRAGMENT";
    android.support.v4.app.Fragment fragment;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

        initFragment();
    }

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Handle Up button
                com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in view");
                fragment.onStop();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

