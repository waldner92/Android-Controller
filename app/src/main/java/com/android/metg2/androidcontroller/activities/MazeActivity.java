package com.android.metg2.androidcontroller.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.AccelerometerFragment;
import com.android.metg2.androidcontroller.fragments.MazeFragment;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.MazeViewModel;

public class MazeActivity extends AppCompatActivity {

    private String MAZE_FRAGMENT = "MAZE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

        initFragment();
    }

    private void initFragment() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = manager.findFragmentByTag(MAZE_FRAGMENT);
        if (fragment == null){

            fragment = MazeFragment.newInstance();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_maze_container,fragment, MAZE_FRAGMENT);
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
