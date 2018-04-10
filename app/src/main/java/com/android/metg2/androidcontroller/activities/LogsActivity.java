package com.android.metg2.androidcontroller.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DebugUtils;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.viewmodels.LogsViewModel;

/**
 * This Activity shows all generated logs form the communication with the Arduino robot
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class LogsActivity extends AppCompatActivity {

    private static String entireMessage = "";
    private static TextView textView;

    private LogsViewModel viewModel;
    private Observer<String> logsObserver;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        com.android.metg2.androidcontroller.utils.DebugUtils.debug("DEBUG", "requestWindowFeature(Window.FEATURE_ACTION_BAR);");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        com.android.metg2.androidcontroller.utils.DebugUtils.debug("DEBUG", "setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);");


        super.onCreate(savedInstanceState);
        com.android.metg2.androidcontroller.utils.DebugUtils.debug("DEBUG", "super.onCreate(savedInstanceState);");
        setContentView(R.layout.activity_logs);
        com.android.metg2.androidcontroller.utils.DebugUtils.debug("DEBUG", "setContentView(R.layout.activity_logs);");
        ActionBar actBar = getSupportActionBar();
        actBar.setHomeButtonEnabled(true);

        initViewModel();

        textView = findViewById(R.id.logs);
        //textView.setText(Constants.LOGS_HEADER);

        viewModel.showLogs(this).observe(this, logsObserver);

        /*CommunicationService.timeBetweenFrames = timeBetweenFrames;
        if (!CommunicationService.isServiceRunning) {
            startService(new Intent(this, CommunicationService.class));
        }*/
    }

    @Override
    public void onStop(){

        super.onStop();

    }

    /**
     * This method finishes the current activity when the the button to go to parent activity
     * is pressed.
     *
     * @param item The item pressed from the Action Bar
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (textView != null)
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

        textView.setText(message);
    }
}

