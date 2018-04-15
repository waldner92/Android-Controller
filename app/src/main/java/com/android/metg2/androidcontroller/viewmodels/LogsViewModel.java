package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.android.metg2.androidcontroller.repository.Repository;
import com.android.metg2.androidcontroller.utils.Constants;

/**
 * Created by Adri on 8/4/18.
 */

public class LogsViewModel extends ViewModel implements Repository.RepositoryCallbacks{

    //our repository
    private Repository repository;

    //observable integer variable
    private MutableLiveData<String> logs;

    //all logs
    private static String entireLogs;

    public LogsViewModel(){
        repository = new Repository(this);
    }

    public LiveData<String> showLogs(Context context){
        if(logs == null){
            //init observable variable
            logs = new MutableLiveData<>();
            if (entireLogs == null) entireLogs = Constants.LOGS_HEADER;
            logs.postValue(entireLogs);
        }
        //tell the repository to start the service
        repository.startService(context);
        //return the observable variable
        return logs;
    }

    public void stopLogs(Context context){
        //tell the repository to stop the service
        com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in viewModel");
        repository.stopService(context);
    }

    /*-------------------------- Repository Callbacks -----------------------------*/
    @Override
    public void onNewMessage(String msg) {
        //push message to the view
        entireLogs = entireLogs + msg + "\n";
        logs.postValue(entireLogs);
    }

    @Override
    public void onServiceStopped() {

        //Toast.makeText(this, "Communication service stopped", Toast.LENGTH_LONG).show();
    }
}
