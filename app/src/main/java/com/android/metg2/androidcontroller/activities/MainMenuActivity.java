package com.android.metg2.androidcontroller.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.metg2.androidcontroller.R;

/**
 * Main Menu Activity. It is the menu that shows the four available options to do next.
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class MainMenuActivity extends AppCompatActivity {

    private Button rcButton;
    private Button mazeButton;
    private Button accButton;
    private Button logsButton;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Set orientation

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //Initialize the four buttons from the menu
        rcButton = findViewById(R.id.button);
        mazeButton = findViewById(R.id.button2);
        accButton = findViewById(R.id.button3);
        logsButton = findViewById(R.id.button4);

        //When a button is hit, each one creates its own intent to go through the corresponding activity

        //First button leads to Remote Control
        rcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, RemoteControlActivity.class);

                startActivity(intent);
                //finish();
            }
        });

        //Second button leads to the Maze Challenge
        mazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, MazeActivity.class);

                startActivity(intent);
                //finish();
            }
        });

        //Third button leads to the Accelerometer Challenge
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, AccelerometerActivity.class);

                startActivity(intent);
                //finish();
            }
        });

        //Fourth button leads to Logs screen
        logsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, LogsActivity.class);

                startActivity(intent);
                //finish();
            }
        });
    }
}
