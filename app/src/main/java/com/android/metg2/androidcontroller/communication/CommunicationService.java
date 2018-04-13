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
import java.net.InetAddress;
import java.net.SocketException;

import static com.android.metg2.androidcontroller.communication.CommunicationThreads.runCommunicationThreads;
import static com.android.metg2.androidcontroller.communication.CommunicationThreads.stopCommunicationThreads;

/**
 * This is the communication service.
 * It is the interface to interact with the Communication threads to send and receive UDP messages.
 * It also interacts with the Repository class
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class CommunicationService  extends Service{

    public static Boolean isRunning = false;
    static DatagramSocket socket;
    static InetAddress localAddress;

    //service binder
    private IBinder binder = new CommunicationServiceBinder();
    //interface that communicates the service with the class that starts/stops the service
    //in this case the Repository
    static CommunicationServiceInterface serviceCallbakcs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * This method starts the transmission and reception threads to communicate with the Arduino.
     *
     * @param intent Intent
     * @param flags int
     * @param startId int
     * @return int
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //localAddress = InetAddress.getByName(Constants.LOCAL_IP);
            //socket = new DatagramSocket(Constants.LOCAL_PORT, localAddress);
            socket = new DatagramSocket(Constants.LOCAL_PORT);

        //} catch (UnknownHostException | SocketException e) {
        } catch (SocketException e) {
            e.printStackTrace();
        }
        runCommunicationThreads();
        isRunning = true;
        Toast.makeText(this, "Communication with Arduino established", Toast.LENGTH_LONG).show();
        sendRemoteControlMessage(Constants.RC_MAN, Constants.RC_LIGTHS_OFF, Constants.RC_GEAR_0, Constants.SHAPE_N, Constants.ANGLE_N);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in CommService");
        stopCommunicationThreads();
        super.onDestroy();
        isRunning = false;
    }

    /**
     * This method tells to the transmission thread what to send
     * @param datagram String datagram to send.
     */
    public static void sendDatagram(String datagram){
        CommunicationThreads.datagramToSend = datagram;
        DebugUtils.debug("Datagram:", datagram);
        //serviceCallbakcs.txMessageValue(datagram);
    }

    /*-------------------------- Service Binder -----------------------------*/
    public class CommunicationServiceBinder extends Binder {

        //set the interface to allow to push received messages from service to the repository
        public void setInterface(CommunicationService.CommunicationServiceInterface callback){
            serviceCallbakcs = callback;
        }
    }
    /*--------------------------------------------------------------------------*/
    /*-------------------------- Service Interface -----------------------------*/
    public interface CommunicationServiceInterface{
        void rxMessageValue(String message);
        void txMessageValue(String txMessage);
    }
    /*--------------------------------------------------------------------------*/

    /*-------------------Message constructors-------------------*/
    public static void sendRemoteControlMessage(String mode, String lights, String gear, String shape, String angle){

        sendDatagram("type:" + Constants.RC_TYPE + ",mode:" + mode + ",lights:" + lights + ",gear:" + gear + ",shape:" + shape + ",angle:" + angle);
    }
    public static void sendAccelerometerMessage(String mode){

        sendDatagram("type:" + Constants.ACCEL_TYPE + ",mode:" + mode);
    }

    public static void sendMazeMessage(String mode){

        sendDatagram("type:" + Constants.MAZE_TYPE + ",mode:" + mode);
    }

}
