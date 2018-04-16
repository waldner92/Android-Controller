package com.android.metg2.androidcontroller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.AccelerometerActivity;
import com.android.metg2.androidcontroller.activities.LogsActivity;
import com.android.metg2.androidcontroller.activities.MazeActivity;
import com.android.metg2.androidcontroller.activities.RemoteControlActivity;

/**
 * Main Menu Fragment. It is the actual view of the Main Menu Activity.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class MainMenuFragment extends Fragment {

    /**
     * Button that triggers the Remote Control Activity.
     */
    private Button rcButton;

    /**
     * Button that triggers the Maze Activity.
     */
    private Button mazeButton;

    /**
     * Button that triggers the Accelerometer Activity.
     */
    private Button accButton;

    /**
     * Button that triggers the Logs Activity.
     */
    private Button logsButton;

    /**
     * Method that returns a new constructed Main Menu Fragment.
     * @return MainMenuFragment The Main Menu Fragment
     */
    public static MainMenuFragment newInstance(){

        return new MainMenuFragment();
    }

    /**
     * onCreate method from the fragment
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
     * @param savedInstanceState
     */
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * onCreateView method from the fragment. It sets the fragment layout and binds the views. It also
     * defines the button listeners and triggers the next activity depending on the pressed button.
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstance Bundle
     * @return View the fragment view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_main_menu,container,false); //Get the corresponding layout

        //Initialize the four buttons from the menu
        rcButton = v.findViewById(R.id.button);
        mazeButton = v.findViewById(R.id.button2);
        accButton = v.findViewById(R.id.button3);
        logsButton = v.findViewById(R.id.button4);

        //When a button is hit, each one creates its own intent to go through the corresponding activity

        //First button leads to Remote Control
        rcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), RemoteControlActivity.class);

                startActivity(intent);
            }
        });

        //Second button leads to the Maze Challenge
        mazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MazeActivity.class);

                startActivity(intent);
            }
        });

        //Third button leads to the Accelerometer Challenge
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AccelerometerActivity.class);

                startActivity(intent);
            }
        });

        //Fourth button leads to Logs screen
        logsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), LogsActivity.class);

                startActivity(intent);
            }
        });

        return v;
    }
}
