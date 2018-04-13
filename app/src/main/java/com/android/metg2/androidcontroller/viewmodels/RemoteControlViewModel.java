package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;

/**
 * Created by Adri on 12/4/18.
 */

public class RemoteControlViewModel extends ViewModel {

    public RemoteControlViewModel(){}


    public boolean onAutManPressed(Context context, boolean isManual) {

        if (isManual){

            Toast.makeText(context, "Changed RC mode to automatic", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Changed RC mode to manual", Toast.LENGTH_SHORT).show();
        }
        return !isManual;
    }

    public boolean onLightsPressed(Context context, boolean lightsON) {

        if (lightsON){

            Toast.makeText(context, "Lights turned OFF", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Lights turned ON", Toast.LENGTH_SHORT).show();
        }
        return !lightsON;
    }

    public int onGearUpPressed(Context context, int gear) {

        if (gear < Constants.MAX_GEAR){
            gear++;
        }
        return gear;
    }

    public int onGearDownPressed(Context context, int gear) {

        if (gear > Constants.MIN_GEAR){
            gear--;
        }
        return gear;
    }

    public int calculateSpeed(int gear) {

        return gear*10;
    }

    public boolean onGas(Context context) {

        Toast.makeText(context, "G0!!!", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onStop(Context context) {

        Toast.makeText(context, "STOP!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public String onAngleChanged(Context applicationContext, String angle) {

        switch (angle){

            case Constants.ANGLE_L2: Toast.makeText(applicationContext, "Turning left hard!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning left hard!");
                break;
            case Constants.ANGLE_L1: Toast.makeText(applicationContext, "Turning left soft!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning left soft!");
                break;
            case Constants.ANGLE_N: Toast.makeText(applicationContext, "Going straight forward!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Going straight forward");
                break;
            case Constants.ANGLE_R1: Toast.makeText(applicationContext, "Turning right soft", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning right soft!");
                break;
            case Constants.ANGLE_R2: Toast.makeText(applicationContext, "Turning right hard!", Toast.LENGTH_SHORT).show();
                DebugUtils.debug("ACCEL", "Turning right hard!");
                break;

        }
        return angle;
    }
}
