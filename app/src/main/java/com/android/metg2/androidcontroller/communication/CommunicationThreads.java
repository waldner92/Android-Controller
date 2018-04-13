package com.android.metg2.androidcontroller.communication;


import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.metg2.androidcontroller.activities.MainMenuActivity;
import com.android.metg2.androidcontroller.activities.SplashActivity;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import static com.android.metg2.androidcontroller.communication.CommunicationService.sendAccelerometerMessage;
import static com.android.metg2.androidcontroller.communication.CommunicationService.sendMazeMessage;
import static com.android.metg2.androidcontroller.communication.CommunicationService.sendRemoteControlMessage;
import static com.android.metg2.androidcontroller.communication.CommunicationService.serviceCallbakcs;
import static com.android.metg2.androidcontroller.communication.CommunicationService.socket;
import static java.net.InetAddress.getByName;

/**
 * This is a package-protected class that has the transmission and reception threads to communicate
 * with the Arduino.
 *
 * Each thread is a static class and can only be accessed from the CommunicationService
 * service class (the interface to controls these threads).
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
class CommunicationThreads {

    private static TransmitionThread txThread;
    private static ReceptionThread rxThread;
    static String datagramToSend;

    /**
     * This method starts both communication threads. It can only be called from
     * the CommunicationService service class
     */
    static void runCommunicationThreads() {
        txThread = new TransmitionThread();
        rxThread = new ReceptionThread();
        txThread.start();
        rxThread.start();
    }

    /**
     * This method stops both communication threads
     */
    static void stopCommunicationThreads() {

        com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Entered here in CommThreads");
        if (txThread.isAlive()) {
            com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Interrupting txThread");
            txThread.mustRun(false);
        }

        if (rxThread.isAlive()) {
            com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","Interrupting rxThread");
            rxThread.mustRun(false);
        }
    }

    /**
     * This class is the reception Thread in charge of receiving UDP datagrams from the Arduino
     */
    private static class ReceptionThread extends Thread {

        private boolean mustRun = false;

        public void mustRun(boolean r){

            mustRun = r;
            DebugUtils.debug("RX_THREAD", "Run modified to " + r);
        }

        @Override
        public void run(){

            DebugUtils.debug(Constants.RX_THREAD_TAG, "Reception thread is running");
            this.setName(Constants.RX_THREAD_TAG);
            mustRun = true;
            receiveMessage();
        }


        /**
         * This method is a loop that receives the UDP datagrams from the Arduino
         */
        private void receiveMessage(){

            int msg_type = 0;

            while (mustRun) {

                //Message rxMsg = new Message();
                byte[] rxBuffer = new byte[Constants.BUFFER_SYZE];
                //DebugUtils.debug(Constants.RX_THREAD_TAG, "ReceptionThread receiving method is running");

                if (socket == null || socket.isClosed()){
                    try {
                        socket = new DatagramSocket(Constants.LOCAL_PORT);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                }
                DatagramPacket packet = new DatagramPacket(rxBuffer, rxBuffer.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final String message = new String(packet.getData()).trim();
                DebugUtils.debug("Packet received", message);
                if(message.length() > 0){
                    //rxMsg.obj = message;
                    msg_type = (msg_type+1) % 3;
                    switch (msg_type){

                        case 0: sendRemoteControlMessage(Constants.RC_MAN, Constants.RC_LIGTHS_OFF, Constants.RC_GEAR_0, Constants.SHAPE_N, Constants.ANGLE_N);
                                break;
                        case 1: sendAccelerometerMessage(Constants.ACC_STOP);
                                break;
                        case 2: sendMazeMessage(Constants.MAZE_STOP);
                                break;
                    }
                    serviceCallbakcs.rxMessageValue(message);//return the message to the Repository
                }
            }
            com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","rxThread interrupted");

        }
    }


    /**
     * This methods sends the UDP datagrams to the arduino
     */
    private static class TransmitionThread extends Thread {

        private boolean mustRun = false;

        public void mustRun(boolean r){

            mustRun = r;
            DebugUtils.debug("TX_THREAD", "Run modified to " + r);
        }


        @Override
        public void run (){

            DebugUtils.debug(Constants.TX_THREAD_TAG, "Transmission thread is running");
            this.setName(Constants.TX_THREAD_TAG);
            mustRun = true;
            this.sendMessage();

        }

        /**
         * This method is a loop that sends the UDP datagrams to the Arduino
         */
        private void sendMessage() {

            /*Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {


                }
            };*/
            int i = 0;
            while (mustRun) {
                try {

                    if (datagramToSend != null) {
                        //DebugUtils.debug(Constants.TX_THREAD_TAG,"TransmiterThread run method is running.");
                        byte[] message = datagramToSend.getBytes();
                        DebugUtils.debug("TX_THREAD Packet sent", datagramToSend);
                        DatagramPacket packet = new DatagramPacket(message, message.length, getByName(Constants.SERVER_IP), Constants.SERVER_PORT);
                        DatagramSocket dsocket = new DatagramSocket();
                        dsocket.send(packet);
                        dsocket.close();
                        if (serviceCallbakcs != null) serviceCallbakcs.txMessageValue(datagramToSend);


                        //Create a new timer, assign it to the Timer Task and set the delay
                        ;
                        //timer.schedule(timerTask, Constants.FramePeriod);
                        Thread.sleep(Constants.FramePeriod);

                        //Construct the next packet (still not implemented)

                    } else {
                        DebugUtils.debug("Packet sent: ", "is null");
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            com.android.metg2.androidcontroller.utils.DebugUtils.debug("BACK","txThread interrupted and run is " + mustRun);
        }


    }

}
