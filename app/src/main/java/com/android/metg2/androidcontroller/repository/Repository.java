package com.android.metg2.androidcontroller.repository;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.android.metg2.androidcontroller.communication.CommunicationService;
import com.android.metg2.androidcontroller.utils.DebugUtils;

import java.util.Calendar;

/**
 * Created by Adri on 8/4/18.
 */

public class Repository implements CommunicationService.CommunicationServiceInterface {

    //interface that implements all the callbacks from the repository to the class that implements the interface
    //in this case the ViewModel
    private RepositoryCallbacks repositoryCallback;

    private boolean serviceIsBound;

    public Repository(RepositoryCallbacks callbacks) {
        //get the interface from the viewmodel so we can push values to the viewmodel
        this.repositoryCallback = callbacks;
    }

    public void startService(Context context) {
        Intent intent = new Intent(context, CommunicationService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    public void stopService(Context context) {
        com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in Respository");
        if (serviceIsBound) {
            com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered in if");
            context.unbindService(serviceConnection);
            serviceIsBound = false;
        }
        Intent intent = new Intent(context, CommunicationService.class);
        context.stopService(intent);
        //push to the viewmodel that the service has been stopped
        repositoryCallback.onServiceStopped();
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CommunicationService.CommunicationServiceBinder binder
                    = (CommunicationService.CommunicationServiceBinder) service;
            //set the interface to the service so we can listen to the service callbacks
            binder.setInterface(Repository.this);
            serviceIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceIsBound = false;
        }
    };

    /*------------------------- Service interface callbakcs -----------------------*/
    @Override
    public void rxMessageValue(String msg) {
        //push seconds value to the viewmodel
        //String time = (String) LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String time = Calendar.getInstance().getTime().toString();
        DebugUtils.debug("PACKET RECEPTION", time);
        DebugUtils.debug("PACKET RECEPTION", msg);
        repositoryCallback.onNewMessage(time + " Received from Arduino: " + msg);
    }

    @Override
    public void txMessageValue(String msg) {
        //push seconds value to the viewmodel
        //String time = (String) LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String time = Calendar.getInstance().getTime().toString();
        repositoryCallback.onNewMessage(time + " Send to Arduino: " + msg);
    }
    /*-------------------------- Repository Interface -----------------------------*/
    public interface RepositoryCallbacks {

        void onNewMessage(String message);
        void onServiceStopped();
    }
}
