package com.android.metg2.androidcontroller.fragments;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.utils.AccelerometerInfo;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.RemoteControlInfo;
import com.android.metg2.androidcontroller.viewmodels.AccelerometerViewModel;
import com.android.metg2.androidcontroller.viewmodels.RemoteControlViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private static TextView accX;

    /**
     * The textview that shows the Gy value of the Arduino accelerometer.
     */
    private static TextView accY;

    /**
     * The textview that shows the Gz value of the Arduino accelerometer.
     */
    private static TextView accZ;

    /**
     * The positive progress bar for the Gx value of the Arduino accelerometer.
     */
    private static ProgressBar pBarX;

    /**
     * The negative progress bar for the Gx value of the Arduino accelerometer.
     */
    private static ProgressBar nBarX;

    /**
     * The positive progress bar for the Gy value of the Arduino accelerometer.
     */
    private static ProgressBar pBarY;

    /**
     * The negative progress bar for the Gy value of the Arduino accelerometer.
     */
    private static ProgressBar nBarY;

    /**
     * The positive progress bar for the Gz value of the Arduino accelerometer.
     */
    private static ProgressBar pBarZ;

    /**
     * The negative progress bar for the Gz value of the Arduino accelerometer.
     */
    private static ProgressBar nBarZ;

    /**
     * Contains the current status of the Arduino's accelerometer received values
     */
    private static Observer<AccelerometerInfo> accInfo;

    /**
     * The viewModel
     */
    private AccelerometerViewModel viewModel;

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

        initViewModel(); //start the communication service

        viewModel.showInfo(getContext()).observe(this, accInfo); //Refresh continuously the list of logs

        accX = v.findViewById(R.id.tv_X);
        accY = v.findViewById(R.id.tv_Y);
        accZ = v.findViewById(R.id.tv_Z);

        pBarX = v.findViewById(R.id.positiveBarX);
        nBarX = v.findViewById(R.id.negativeBarX);
        pBarY = v.findViewById(R.id.positiveBarY);
        nBarY = v.findViewById(R.id.negativeBarY);
        pBarZ = v.findViewById(R.id.positiveBarZ);
        nBarZ = v.findViewById(R.id.negativeBarZ);

        pBarX.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress_bar));
        nBarX.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress_bar));
        pBarY.setProgressDrawable(getResources().getDrawable(R.drawable.blue_progress_bar));
        nBarY.setProgressDrawable(getResources().getDrawable(R.drawable.blue_progress_bar));
        pBarZ.setProgressDrawable(getResources().getDrawable(R.drawable.red_progress_bar));
        nBarZ.setProgressDrawable(getResources().getDrawable(R.drawable.red_progress_bar));

        nBarX.setRotation(180);
        nBarY.setRotation(180);
        nBarZ.setRotation(180);

        accX.append(" 0.03");
        accY.append(" 0.04");
        accZ.append(" 0.98");



        return v;
    }

    private void initViewModel(){

        viewModel = ViewModelProviders.of(this).get(AccelerometerViewModel.class);
        accInfo = new Observer<AccelerometerInfo>() {
            @Override
            public void onChanged(@Nullable AccelerometerInfo info) {
                showInfo(info);
            }
        };
    }

    /**
     * This method refreshes the info of the remote control status.
     * @param info The object that contains all the info
     */
    public static void showInfo(AccelerometerInfo info) {

        accX.setText(R.string.accX);
        accX.append(info.getX().toString());
        accY.setText(R.string.accY);
        accY.append(info.getY().toString());
        accZ.setText(R.string.accZ);
        accZ.append(info.getZ().toString());

        if (info.getX() > 0){

            pBarX.setProgress((int)(info.getX()*100));
            nBarX.setProgress(0);
        }else {
            pBarX.setProgress(0);
            nBarX.setProgress((int)(-info.getX()*100));
        }

        if (info.getY() > 0){

            pBarY.setProgress((int)(info.getY()*100));
            nBarY.setProgress(0);
        }else {
            pBarY.setProgress(0);
            nBarY.setProgress((int)(-info.getY()*100));
        }

        if (info.getZ() > 0){

            pBarZ.setProgress((int)(info.getZ()*100));
            nBarZ.setProgress(0);
        }else {
            pBarZ.setProgress(0);
            nBarZ.setProgress((int)(-info.getZ()*100));
        }
    }


    /**
     * onStop method from the fragment.
     */
    @Override
    public void onStop(){

        viewModel.stopAccelerometer(getContext());
        super.onStop();
        getActivity().finish();
    }
}
