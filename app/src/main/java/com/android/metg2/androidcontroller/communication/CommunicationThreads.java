package com.android.metg2.androidcontroller.communication;


import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

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
        if (txThread.isAlive()) {
            txThread.interrupt();
        }

        if (rxThread.isAlive()) {
            rxThread.interrupt();
        }
    }

    /**
     * This class is the reception Thread in charge of receiving UDP datagrams from the Arduino
     */
    private static class ReceptionThread extends Thread {

        @Override
        public void run(){

            DebugUtils.debug(Constants.RX_THREAD_TAG, "Reception thread is running");
            this.setName(Constants.RX_THREAD_TAG);
            receiveMessage();
        }


        /**
         * This method is a loop that receives the UDP datagrams from the Arduino
         */
        private void receiveMessage(){

            while (!this.isInterrupted()) {

                Message rxMsg = new Message();
                byte[] rxBuffer = new byte[Constants.BUFFER_SYZE];
                DebugUtils.debug(Constants.RX_THREAD_TAG, "ReceptionThread receiving method is running");

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
                Log.i("Packet received: ", message);
                System.out.println(message);
                if(message.length() > 0){
                    rxMsg.obj = message;
                    serviceCallbakcs.rxMessageValue(rxMsg);//return the message to the Repository
                }
            }
        }
    }


    /**
     * This methods sends the UDP datagrams to the arduino
     */
    private static class TransmitionThread extends Thread {


        @Override
        public void run (){

            DebugUtils.debug(Constants.TX_THREAD_TAG, "Transmission thread is running");
            this.setName(Constants.TX_THREAD_TAG);
            this.sendMessage();
        }

        /**
         * This method is a loop that sends the UDP datagrams to the Arduino
         */
        private void sendMessage() {

            while (!this.isInterrupted()) {
                try {
                    if (datagramToSend != null) {
                        DebugUtils.debug(Constants.TX_THREAD_TAG,"TransmiterThread run method is running.");
                        byte[] message = datagramToSend.getBytes();
                        DebugUtils.debug("Packet sent: ", datagramToSend);
                        DatagramPacket packet = new DatagramPacket(message, message.length, getByName(Constants.SERVER_IP), Constants.SERVER_PORT);
                        DatagramSocket dsocket = new DatagramSocket();
                        dsocket.send(packet);
                        dsocket.close();
                        Thread.sleep(Constants.FramePeriod);

                        //Construct the next packet (still not implemented)

                    } else {
                        DebugUtils.debug("Packet sent: ", "is null");
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
