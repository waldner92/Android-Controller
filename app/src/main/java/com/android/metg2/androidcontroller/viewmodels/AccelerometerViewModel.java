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
 * The viewModel of the Accelerometer Activity. It communicates with the repository to sent a message,
 * and gets the received ones from it to pass the new gathered values to the view.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 2.0
 */
public class AccelerometerViewModel extends ViewModel implements Repository.RepositoryCallbacks{

    /**
     * The repository interface to get the logs.
     */
    private Repository repository;

    /**
     * The observable object that the view monitors and contains all the info
     */
    private MutableLiveData<AccelerometerInfo> infoMutableLiveData;

    /**
     * The object that contains all the info related to the remote control challenge
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
     * This method refreshes continuously the accelerometer challenge info to the view and asks the repository to
     * start the communication service.
     *
     * @param context Context The application context
     * @return LiveData<AccelerometerInfo> The acceleromete challenge information values
     */
    public LiveData<AccelerometerInfo> showInfo(Context context){

        //If it is the first call, the object will be null and we have to initialize it
        if(infoMutableLiveData == null){

            infoMutableLiveData = new MutableLiveData<>(); //initialize the observable variable
            if (info == null) info = new AccelerometerInfo(); //If the object is empty (first call), initialize it
            infoMutableLiveData.postValue(info); //update the observable variable the new object
        }

        repository.startService(context); //ask the repository to start the service
        constructMessage();
        repository.sendMessage(message);
        return infoMutableLiveData; //return the observable variable
    }

    /**
     * This method asks the repository to stop the communication service.
     *
     * @param context Context The application context
     */
    public void stopAccelerometer(Context context){

        repository.sendMessage("type:stop,mode:none"); //send the stop message to the robot
        repository.stopService(context); //ask the repository to stop the service
    }

    /**
     * This method is called when the repository passes a new received message to the viewModel.
     * It parses the received message and passes the new info to the view.
     * @param message String The received message
     * @param time String Timestamp of the received message
     */
    @Override
    public void onNewMessage(String message, String time) {

        deconstructMessage(message);
        infoMutableLiveData.postValue(info);
    }

    /**
     * This callback method is called when the communication has stopped. It does nothing right now,
     * but it is already defined for future implementations.
     */
    @Override
    public void onServiceStopped() {

    }

    /**
     * This method constructs a message of type "accelerometer"
     */
    private void constructMessage() {

        message = "type:" + Constants.ACCEL_TYPE + ",mode:none";
    }

    /**
     * This method parses all the info from the received "accelerometer" message
     * @param msg The received message to be parsed
     */
    private void deconstructMessage(String msg) {

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
