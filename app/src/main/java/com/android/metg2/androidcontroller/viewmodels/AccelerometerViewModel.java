package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.android.metg2.androidcontroller.repository.Repository;
import com.android.metg2.androidcontroller.utils.AccelerometerInfo;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.utils.RemoteControlInfo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The viewModel of the Accelerometer Activity. It will communicate with the robot in the future,
 * but right now it just shows toasts
 * on the screen and update some values of the view.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class AccelerometerViewModel extends ViewModel implements Repository.RepositoryCallbacks{

    /**
     * The repository interface to get the logs.
     */
    private Repository repository;

    /**
     * The observable string that the view monitors and contains the list of logs.
     */
    private MutableLiveData<AccelerometerInfo> infoMutableLiveData;

    /**
     * The list of all logs.
     */
    private static AccelerometerInfo info;

    /**
     * The message to be sent
     */
    private String message;

    /**
     * The constructor method. It initializes the repository interface.
     */
    public AccelerometerViewModel(){
        repository = new Repository(this);
    }

    /**
     * This method refreshes continuously the list of logs to the view and asks the repository to
     * start the communication service.
     *
     * @param context Context The application context
     * @return LiveData<String> The list of logs
     */
    public LiveData<AccelerometerInfo> showInfo(Context context){

        //If it is the first call, logs will be null and we have to initialize it
        if(infoMutableLiveData == null){

            infoMutableLiveData = new MutableLiveData<>(); //initialize the observable variable
            if (info == null) info = new AccelerometerInfo(); //If the list of logs is empty (first call), fill it with the header
            infoMutableLiveData.postValue(info); //update the observable variable with the list of logs
        }

        repository.startService(context); //ask the repository to start the service
        constructMessage();
        repository.sendMessage(message);
        return infoMutableLiveData; //return the observable variable
    }

    /**
     * This method the repository to stop the communication service.
     *
     * @param context Context The application context
     */
    public void stopAccelerometer(Context context){

        //com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in viewModel");
        repository.sendMessage("type:stop,mode:none");
        repository.stopService(context); //ask the repository to stop the service
    }

    @Override
    public void onNewMessage(String message, String time) {

        deconstructMessage(message);
        infoMutableLiveData.postValue(info);


    }

    @Override
    public void onServiceStopped() {

    }

    private void constructMessage() {

        message = "type:" + Constants.ACCEL_TYPE + ",mode:none";
    }

    private void deconstructMessage(String msg) {

        //Type:rc,Aut:1,RBump:0,LBump:0,ColUs:1,gear:+2,ledsOn:0,sh:N,ang:00,temp:28.38
        String[] fields = msg.split(",");
        String[] aux = fields[Constants.TYPE_FIELD].split(":");
        String type = aux[Constants.VALUE_FIELD];
        DebugUtils.debug("PARSED", "Type-> " + type);

        switch (type){

            case Constants.ACCEL_TYPE:

                aux = fields[Constants.X_FIELD].split(":");
                Float val = Float.parseFloat(aux[Constants.VALUE_FIELD]);
                DebugUtils.debug("PARSED", "X-> " + val);

                info.setX(val.floatValue());

                aux = fields[Constants.Y_FIELD].split(":");
                val = Float.parseFloat(aux[Constants.VALUE_FIELD]);
                DebugUtils.debug("PARSED", "Y-> " + val);

                info.setY(val.floatValue());

                aux = fields[Constants.Z_FIELD].split(":");
                val = Float.parseFloat(aux[Constants.VALUE_FIELD]);
                DebugUtils.debug("PARSED", "Z-> " + val);

                info.setZ(val.floatValue());
                break;

        }
    }

}
