package com.android.metg2.androidcontroller.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.AccelerometerActivity;
import com.android.metg2.androidcontroller.activities.LogsActivity;
import com.android.metg2.androidcontroller.activities.MazeActivity;
import com.android.metg2.androidcontroller.activities.RemoteControlActivity;

public class AccelerometerFragment extends android.support.v4.app.Fragment {

    private TextView accX;
    private TextView accY;
    private TextView accZ;

    public static AccelerometerFragment newInstance(){
        return new AccelerometerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_accelerometer,container,false);

        accX = v.findViewById(R.id.tv_X);
        accY = v.findViewById(R.id.tv_Y);
        accZ = v.findViewById(R.id.tv_Z);

        accX.append(" 30");
        accY.append(" 40");
        accZ.append(" 50");

        return v;
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
