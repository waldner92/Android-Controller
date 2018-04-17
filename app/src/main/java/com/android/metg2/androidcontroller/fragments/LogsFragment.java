package com.android.metg2.androidcontroller.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.viewmodels.LogsViewModel;

/**
 * Logs Fragment. It is the actual view of the Logs Activity.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class LogsFragment extends Fragment {

    /**
     * The entire list of logs
     */
    private static String entireMessage = "";

    /**
     * The textview that displays the logs
     */
    private static TextView textView;

    /**
     * The viewModel of the view
     */
    private LogsViewModel viewModel;

    /**
     * The logs observer to monitor the changes of the list of logs from the viewModel
     */
    private static Observer<String> logsObserver;

    /**
     * Method that returns a new constructed Logs Fragment.
     * @return LogsFragment The Logs Fragment
     */
    public static LogsFragment newInstance(){
        return new LogsFragment();
    }

    /**
     * onCreate method from the fragment.
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
     * ask the viewModel to start logging messages and observe (monitor) the changes in the list of logs.
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstance Bundle
     * @return View the fragment view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_logs,container,false); //Get the corresponding layout

        initViewModel(); //Start the logging service
        textView = v.findViewById(R.id.logs);
        viewModel.showLogs(getContext()).observe(this, logsObserver); //Refresh continuously the list of logs

        return v;
    }

    /**
     * onStop method from the fragment. It asks to the viewModel to stop the logging service and the
     * communication with the Arduino.
     */
    @Override
    public void onStop(){

        viewModel.stopLogs(getContext()); //Stop the logging service
        super.onStop();
    }

    /**
     * onResume method from the fragment.
     */
    @Override
    public void onResume() {

        super.onResume();
    }

    /**
     * This method initializes the Logs viewModel.
     */
    private void initViewModel(){

        viewModel = ViewModelProviders.of(this).get(LogsViewModel.class);
        logsObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String entireMessage) {
                showLogs(entireMessage);
            }
        };
    }

    /**
     * This method refreshes the list of logs.
     * @param message The log string
     */
    public static void showLogs(String message) {

        entireMessage = message;
        textView.setText(message);
    }
}

