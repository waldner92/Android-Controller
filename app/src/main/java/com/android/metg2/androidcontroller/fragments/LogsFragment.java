package com.android.metg2.androidcontroller.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.viewmodels.LogsViewModel;

/**
 * This Activity shows all generated logs form the communication with the Arduino robot
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class LogsFragment extends Fragment {

    private static String entireMessage = "";
    private static TextView textView;

    private LogsViewModel viewModel;
    private Observer<String> logsObserver;

    public static LogsFragment newInstance(){
        return new LogsFragment();
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

        View v = inflater.inflate(R.layout.fragment_logs,container,false);

        initViewModel();

        textView = v.findViewById(R.id.logs);
        //textView.setText(Constants.LOGS_HEADER);

        viewModel.showLogs(getContext()).observe(this, logsObserver);

        /*CommunicationService.timeBetweenFrames = timeBetweenFrames;
        if (!CommunicationService.isServiceRunning) {
            startService(new Intent(this, CommunicationService.class));
        }*/

        return v;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Handle Up button
                com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in view");
                viewModel.stopLogs(getContext());
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onStop(){

        viewModel.stopLogs(getContext());
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.showLogs(getContext()).observe(this, logsObserver);
        if (textView == null)
            textView.append(entireMessage);
    }

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
     * Method for adding a new row to the log register.
     * @param message The log string
     */
    public static void showLogs(String message) {

        entireMessage = message;
        textView.setText(message);
        //textView.append(message);
    }
}

