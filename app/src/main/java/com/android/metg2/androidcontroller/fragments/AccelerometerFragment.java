package com.android.metg2.androidcontroller.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;

/**
 * Accelerometer Fragment. It is the actual view of the Accelerometer Activity.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class AccelerometerFragment extends android.support.v4.app.Fragment {

    /**
     * The textview that shows the Gx value of the Arduino accelerometer.
     */
    private TextView accX;

    /**
     * The textview that shows the Gy value of the Arduino accelerometer.
     */
    private TextView accY;

    /**
     * The textview that shows the Gz value of the Arduino accelerometer.
     */
    private TextView accZ;

    /**
     * Method that returns a new constructed Accelerometer Fragment.
     * @return AcceleromterFragment The Accelerometer Fragment
     */
    public static AccelerometerFragment newInstance(){
        return new AccelerometerFragment();
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
     * onActivityCreated method from the fragment
     *
     * @param savedInstanceState
     */
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * onCreateView method from the fragment. It sets the fragment layout and binds the views.
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstance Bundle
     * @return View the fragment view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_accelerometer,container,false); //Get the corresponding view

        accX = v.findViewById(R.id.tv_X);
        accY = v.findViewById(R.id.tv_Y);
        accZ = v.findViewById(R.id.tv_Z);

        accX.append(" 30");
        accY.append(" 40");
        accZ.append(" 50");

        return v;
    }

    /**
     * onStop method from the fragment.
     */
    @Override
    public void onStop(){

        super.onStop();
    }

    /**
     * onResume method from the fragment.
     */
    @Override
    public void onResume() {

        super.onResume();
    }
}
