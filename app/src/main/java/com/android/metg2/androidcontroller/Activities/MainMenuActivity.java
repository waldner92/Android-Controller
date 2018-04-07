package com.android.metg2.androidcontroller.Activities;

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
        Button button_rc = findViewById(R.id.button);
        Button button_maze = findViewById(R.id.button2);
        Button button_acc = findViewById(R.id.button3);
        Button button_logs = findViewById(R.id.button4);

        //When a button is hit, each one creates its own intent to go through the corresponding activity

        //First button leads to Remote Control
        button_rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, RemoteControlActivity.class);

                startActivity(intent);
            }
        });

        //Second button leads to the Maze Challenge
        button_maze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, MazeActivity.class);

                startActivity(intent);
            }
        });

        //Third button leads to the Accelerometer Challenge
        button_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, AccelerometerActivity.class);

                startActivity(intent);
            }
        });

        //Fourth button leads to Logs screen
        button_logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, LogsActivity.class);

                startActivity(intent);
            }
        });
    }
}
