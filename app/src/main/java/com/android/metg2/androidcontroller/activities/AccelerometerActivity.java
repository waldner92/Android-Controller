package com.android.metg2.androidcontroller.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;

public class AccelerometerActivity extends AppCompatActivity {

    private TextView accX;
    private TextView accY;
    private TextView accZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
        accX = findViewById(R.id.tv_X);
        accY = findViewById(R.id.tv_Y);
        accZ = findViewById(R.id.tv_Z);

        accX.append(" 30");
        accY.append(" 40");
        accZ.append(" 50");
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
