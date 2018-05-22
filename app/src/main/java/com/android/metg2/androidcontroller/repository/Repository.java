package com.android.metg2.androidcontroller.repository;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.android.metg2.androidcontroller.communication.CommunicationService;
import com.android.metg2.androidcontroller.utils.DebugUtils;

import java.util.Calendar;

import static com.android.metg2.androidcontroller.communication.CommunicationService.sendDatagram;

/**
 * This class acts as an intermediate agent between the viewModels and the communication service. It
 * implements the communication service interface to obtain data from it (the messages). It also acts as
 * interface itself and uses callback functions to pass data to the viewModels.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 2.0
 */
public class Repository implements CommunicationService.CommunicationServiceInterface {

    /**
     * Interface that implements all the callbacks from the repository to the class that implements
     * the interface (in this case the viewModels)
     */
    private RepositoryCallbacks repositoryCallback;

    /**
     * Indicates if the communication service is bound to the repository (true) or not (false)
     */
    private boolean serviceIsBound;

    /**
     * Repository constructor. It gets the interface from the viewModel to interact with it.
     * @param callbacks RespositoryCallbacks The callback interface from the viewModel
     */
    public Repository(RepositoryCallbacks callbacks) {

        this.repositoryCallback = callbacks; //get the interface from the viewmodel so we can push values to the viewmodel
    }

    /**
     * This function initializes a new instance of the communication service and binds it to the repository
     * @param context Context The application context
     */
    public void startService(Context context) {

        Intent intent = new Intent(context, CommunicationService.class); //get the intent from the service
        context.startService(intent); //start the service
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE); //bind the service
    }

    /**
     * This function tries to stop the communication service and notifies it to the viewModel
     * @param context Context The application context
     */
    public void stopService(Context context) {

        //If th service is bound, unbind it
        if (serviceIsBound) {

            context.unbindService(serviceConnection);
            serviceIsBound = false;
        }

        Intent intent = new Intent(context, CommunicationService.class); //get the intent from the service
        context.stopService(intent); //stop the service
        repositoryCallback.onServiceStopped(); //push to the viewModel that the service has been stopped
    }

    /**
     * The service connection interface for monitoring the state of the communication service
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        /**
         * This method is called when a connection to the Service has been established, with the
         * IBinder of the communication channel to the Service.
         * @param name ComponentName The concrete component name of the service that has been connected.
         * @param service IBinder The IBinder of the Service's communication channel, which we can now make calls on.
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            CommunicationService.CommunicationServiceBinder binder = (CommunicationService.CommunicationServiceBinder) service;
            binder.setInterface(Repository.this); //set the interface to the service so we can listen to the service callbacks
            serviceIsBound = true; //now the service is bound
        }

        /**
         * This method is called when a connection to the Service has been lost. This typically happens when the
         * process hosting the service has crashed or been killed. This does not remove the
         * ServiceConnection itself.
         * @param name The concrete component name of the service whose connection has been lost.
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

            serviceIsBound = false; //now the service is unbound
        }
    };

    /*------------------------- Service interface callbakcs -----------------------*/

    /**
     * Callback method that gets the received message from the communication service and passes it to all
     * the viewModels.
     * @param msg Message The received message
     */
    @Override
    public void rxMessageValue(String msg) {

        String time = Calendar.getInstance().getTime().toString(); //Get the current date and time
        repositoryCallback.onNewMessage(msg, time); //Pass the received message to the view model
        //repositoryCallback.onNewMessage(time + " Received from Arduino: " + msg); //construct the log and push it to the Logs viewModel
    }

    /**
     * Callback method that gets the sent message to the communication service and prepares
     * a log to inform to the Logs viewModel. Currently, this function is disabled (commented).
     * @param msg Message The sent message
     */
    @Override
    public void txMessageValue(String msg) {

        //String time = Calendar.getInstance().getTime().toString(); //Get the current date and time
        //repositoryCallback.onNewMessage(time + " Sent to Arduino: " + msg); //construct the log and push it to the Logs viewModel
    }

    /**
     * This function passes the new message to be sent to the Communication Service
     * @param msg String the message to be sent
     */
    public void sendMessage(String msg){

        sendDatagram(msg);
    }
    /*-----------------------------------------------------------------------------*/
    /*-------------------------- Repository Interface -----------------------------*/
    /**
     * This interface specifies the two callback methods to communicate with the viewModels.
     */
    public interface RepositoryCallbacks {

        void onNewMessage(String message, String time);
        void onServiceStopped();
    }
}
