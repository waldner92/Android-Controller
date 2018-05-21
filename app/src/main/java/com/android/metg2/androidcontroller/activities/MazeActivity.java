package com.android.metg2.androidcontroller.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.MazeFragment;

/**
 * Maze Activity. It shows the 5x5 cell-maze and the buttons to explore and play its solution.
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class MazeActivity extends AppCompatActivity {

    private String MAZE_FRAGMENT = "MAZE_FRAGMENT";

    /**
     * OnCreate Method from the activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //requestWindowFeature(Window.FEATURE_ACTION_BAR); //Request for the action bar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Set orientation to landscape
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze); //Get the corresponding layout
        ActionBar actBar = getSupportActionBar(); //Get the action bar
        actBar.setDisplayHomeAsUpEnabled(true); //Enable the up (back) button

        initFragment();
    }

    /**
     * This method initializes the activity's fragment.
     */
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
}
