package com.android.metg2.androidcontroller.communication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;

import java.net.DatagramSocket;
import java.net.SocketException;

import static com.android.metg2.androidcontroller.communication.CommunicationTasks.runCommunicationTasks;
import static com.android.metg2.androidcontroller.communication.CommunicationTasks.stopCommunicationTasks;

/**
 * This class is the communication service. It is the interface to interact with the Communication
 * tasks (AsynTasks) to send and receive UDP messages. It also interacts with the Repository class
 * thanks to its callback methods.
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class CommunicationService  extends Service{

    /**
     * Indicates if the Coommunication Service is running (true) or not (false).
     */
    public static Boolean isRunning = false;

    /**
     * The socket used to exchange messages with the Arduino.
     */
    static DatagramSocket socket;

    /**
     * The service binder
     */
    private IBinder binder = new CommunicationServiceBinder();

    /**
     * Interface that communicates the service with the class that starts/stops the service itself
     * (in this case, the Repository Class).
     */
    static CommunicationServiceInterface serviceCallbakcs;

    /**
     * This method returns the communication channel to the service
     *
     * @param intent Intent The Intent that was used to bind to this service
     * @return IBinder Return an IBinder through which clients can call on to the service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * onCreate method from the service.
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * This method starts the transmission and reception tasks to communicate with the Arduino.
     *
     * @param intent Intent The Intent supplied to starService(Intent)
     * @param flags int Additional data about this start request
     * @param startId int A unique integer representing this specific request to start
     * @return int Indicates what semantics the system should use for the service's current started state
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            socket = new DatagramSocket(Constants.LOCAL_PORT); //Create the socket using the specified local port
        } catch (SocketException e) {
            e.printStackTrace();
        }

        runCommunicationTasks(); //Start the transmission and reception AsynTasks
        isRunning = true; //The service is now running
        Toast.makeText(this, "Communication with Arduino established", Toast.LENGTH_LONG).show(); //notify it to the view
        sendRemoteControlMessage(Constants.RC_MAN, Constants.RC_LIGTHS_OFF, Constants.RC_GEAR_0, Constants.SHAPE_N, Constants.ANGLE_N); //Set the first message to be send
        return Service.START_STICKY;
    }

    /**
     * onDestroy method from the service. It stops the transmission and reception tasks.
     */
    @Override
    public void onDestroy() {

        com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in CommService");
        stopCommunicationTasks(); //Stop the transmission and reception AsynTasks
        super.onDestroy();
        isRunning = false; //The service is now stopped
    }

    /**
     * This method tells to the transmission task what to send
     * @param datagram String datagram to send.
     */
    public static void sendDatagram(String datagram){

        CommunicationTasks.datagramToSend = datagram;
        DebugUtils.debug("Datagram:", datagram);
    }

    /*-------------------------- Service Binder -----------------------------*/

    /**
     * This class is the service binder
     *
     * @author Adria Acero, Adria Mallorqui, Jordi Miro
     * @version 1.0
     */
    public class CommunicationServiceBinder extends Binder {

        /**
         * This metho sets the interface to allow the communication (callbacks) with the repository.
         * @param callback The service interface
         */
        public void setInterface(CommunicationService.CommunicationServiceInterface callback){

            serviceCallbakcs = callback;
        }
    }

    /*--------------------------------------------------------------------------*/
    /*-------------------------- Service Interface -----------------------------*/

    /**
     * This interface specifies the two callback methods to communicate with the repository.
     */
    public interface CommunicationServiceInterface{
        void rxMessageValue(String message);
        void txMessageValue(String txMessage);
    }

    /*--------------------------------------------------------------------------*/
    /*---------------------------Message constructors---------------------------*/

    /**
     * This method constructs the string (packet) used for the remote control
     *
     * @param mode String The mode (automatic or manual)
     * @param lights String The lights control (turn on or turn off)
     * @param gear String The gear (-3...3)
     * @param shape String The shape to draw (circle, triangle or square)
     * @param angle String The rotation angle
     */
    public static void sendRemoteControlMessage(String mode, String lights, String gear, String shape, String angle){

        sendDatagram("type:" + Constants.RC_TYPE + ",mode:" + mode + ",lights:" + lights + ",gear:" + gear + ",shape:" + shape + ",angle:" + angle);
    }

    /**
     * This method constructs the string (packet) used for the acceleromer challenge
     *
     * @param mode Reserved field (it is not defined yet)
     */
    public static void sendAccelerometerMessage(String mode){

        sendDatagram("type:" + Constants.ACCEL_TYPE + ",mode:" + mode);
    }

    /**
     * This method constructs the string (packet) used for the maze challenge
     *
     * @param mode String Specifies if the Arduino must explore or play the solution
     */
    public static void sendMazeMessage(String mode){

        sendDatagram("type:" + Constants.MAZE_TYPE + ",mode:" + mode);
    }
}
