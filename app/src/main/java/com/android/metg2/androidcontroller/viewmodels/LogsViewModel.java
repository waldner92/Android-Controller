package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.android.metg2.androidcontroller.repository.Repository;
import com.android.metg2.androidcontroller.utils.Constants;

/**
 * The viewModel of the Logs Activity. This class acts as an intermediate agent between the activity (fragment)
 * and the communication service. It implements the repository interface methods to obtain the logs from it. It
 * then pass the list of logs to the view.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class LogsViewModel extends ViewModel implements Repository.RepositoryCallbacks{

    /**
     * The repository interface to get the logs.
     */
    private Repository repository;

    /**
     * The observable string that the view monitors and contains the list of logs.
     */
    private MutableLiveData<String> logs;

    /**
     * The list of all logs.
     */
    private static String entireLogs;

    /**
     * The constructor method. It initializes the repository interface.
     */
    public LogsViewModel(){
        repository = new Repository(this);
    }

    /**
     * This method refreshes continously the list of logs to the view and asks the repository to
     * start the communication service.
     *
     * @param context Context The application context
     * @return LiveData<String> The list of logs
     */
    public LiveData<String> showLogs(Context context){

        //If it is the first call, logs will be null and we have to initialize it
        if(logs == null){

            logs = new MutableLiveData<>(); //initialize the observable variable
            if (entireLogs == null) entireLogs = Constants.LOGS_HEADER; //If the list of logs is empty (first call), fill it with the header
            logs.postValue(entireLogs); //update the observable variable with the list of logs
        }

        repository.startService(context); //ask the repository to start the service
        return logs; //return the observable variable
    }

    /**
     * This method the repository to stop the communication service.
     *
     * @param context Context The application context
     */
    public void stopLogs(Context context){

        com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in viewModel");
        repository.stopService(context); //ask the repository to stop the service
    }

    /*-------------------------- Repository Callbacks -----------------------------*/

    /**
     * This callback method is called when there is a new message. It updated the entire list of logs and
     * refreshes the observable variable.
     *
     * @param msg String The new message
     * @param time String the timestamp of the message
     */
    @Override
    public void onNewMessage(String msg, String time) {

        //entireLogs = entireLogs + log + "\n"; //update the list of logs
        //logs.postValue(entireLogs); //push the list of logs to the view
    }

    /**
     * This callback method is called when the communication has stopped. It does nothing right now,
     * but it is already defined for future implementations.
     */
    @Override
    public void onServiceStopped() {

        //DebugUtils.debug("Logs viewModel", "Communication service stoped");
    }
}
