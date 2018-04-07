package com.android.metg2.androidcontroller.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.Utils.Constants;

/**
 * This Activity shows all generated logs form the communication with the Arduino robot
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class LogsActivity extends AppCompatActivity {

    //private static final long timeBetweenFrames = 500;
    private static String entireMessage = "";
    private static TextView textView;

    /**
     * OnCreate Method from Activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        ActionBar actBar = getSupportActionBar();
        actBar.setHomeButtonEnabled(true);
        textView = findViewById(R.id.logs);
        textView.setText(Constants.LOGS_HEADER);
        /*CommunicationService.timeBetweenFrames = timeBetweenFrames;
        if (!CommunicationService.isServiceRunning) {
            startService(new Intent(this, CommunicationService.class));
        }*/
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

    /**
     * Method for adding a new row to the log register.
     * @param message String
     */
    public static void addLogRow(String message) {
        entireMessage += message;
        if (textView != null)
            textView.append("\n" + message);
    }
}

