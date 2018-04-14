package com.android.metg2.androidcontroller.fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.AccelerometerActivity;
import com.android.metg2.androidcontroller.activities.LogsActivity;
import com.android.metg2.androidcontroller.activities.MainMenuActivity;
import com.android.metg2.androidcontroller.activities.MazeActivity;
import com.android.metg2.androidcontroller.activities.RemoteControlActivity;
import com.android.metg2.androidcontroller.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main Menu Activity. It is the menu that shows the four available options to do next.
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class MainMenuFragment extends Fragment {

    private Button rcButton;
    private Button mazeButton;
    private Button accButton;
    private Button logsButton;


    public static MainMenuFragment newInstance(){
        return new MainMenuFragment();
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

        View v = inflater.inflate(R.layout.fragment_main_menu,container,false);

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
                //finish();
            }
        });

        //Second button leads to the Maze Challenge
        mazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MazeActivity.class);

                startActivity(intent);
                //finish();
            }
        });

        //Third button leads to the Accelerometer Challenge
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AccelerometerActivity.class);

                startActivity(intent);
                //finish();
            }
        });

        //Fourth button leads to Logs screen
        logsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), LogsActivity.class);

                startActivity(intent);
                //finish();
            }
        });

        return v;
    }
}
