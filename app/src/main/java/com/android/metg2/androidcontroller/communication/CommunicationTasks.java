package com.android.metg2.androidcontroller.communication;


import android.os.AsyncTask;

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
 * This is a package-protected class that has the transmission and reception tasks to communicate
 * with the Arduino. Each AsynTask is a static class and can only be accessed from the CommunicationService
 * service class (the interface to control these tasks).
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 2.0
 */
class CommunicationTasks {

    /**
     * The transmission AsynTask
     */
    private static TransmissionTask txTask;

    /**
     * The reception AsynTask
     */
    private static ReceptionTask rxTask;

    /**
     * The datagram string to be send
     */
    static String datagramToSend;

    /**
     * Indicates if the transmission AsynTask must continue running
     */
    private static boolean tx_run;

    /**
     * Indicates if the reception AsynTask must continue running
     */
    private static boolean rx_run;

    /**
     * This starts the reception task. It can only be called from
     * the CommunicationService service class.
     */
    static void runReceptionTask() {

        rxTask = new ReceptionTask();
        rx_run = true; //Enable rx task
        rxTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //Start the rx task in parallel to the tx task
    }

    /**
     * This method starts the transmission task. It can only be called from
     * the CommunicationService service class.
     */
    static void runTransmitionTask() {

        txTask = new TransmissionTask();
        tx_run = true; //Enable tx task
        txTask.execute(); //Start the tx task
    }

    /**
     * This method stops both communication tasks
     */
    static void stopCommunicationTasks() {


        if (!txTask.isCancelled()) {

            txTask.cancel(false); //Stop the tx task
        }

        if (!rxTask.isCancelled()) {

            rxTask.cancel(false); //Stop the rx task
        }

        tx_run = false; //Disable tx task
        rx_run = false; //Diasble the rx task
        datagramToSend = null; //"Delete" the datagram to string so the next time that the service is started it does send anything until the datagram is set again
    }

    /**
     * This class is the reception AsynTask in charge of receiving UDP datagrams from the Arduino.
     *
     * @author Adria Acero, Adria Mallorqui, Jordi Miro
     * @version 2.0
     */
    private static class ReceptionTask extends AsyncTask<Void, Void, Void> {

        /**
         * doInBackground method from the reception task. It tries to get the received message from
         * the socket. Finally, it informs to the repository about the received message.
         *
         * @param voids Void
         * @return Void null
         */
        @Override
        protected Void doInBackground(Void... voids) {

            if (!isCancelled() && rx_run) {

                byte[] rxBuffer = new byte[Constants.BUFFER_SYZE]; //buffer to store the received message

                if (socket == null || socket.isClosed()) { //Create the socket using the specified local port if it has been previously closed

                    try {
                        socket = new DatagramSocket(Constants.LOCAL_PORT);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                }

                DatagramPacket packet = new DatagramPacket(rxBuffer, rxBuffer.length); //UDP packet to get the datagram from the socket

                try {
                    DebugUtils.debug("RX_TASK", "Waiting for a new packet --------------------------------------------------------------------------------------------");
                    socket.receive(packet); //get the datagram from the socket
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final String message = new String(packet.getData()).trim(); //Get the data string from the datagram
                DebugUtils.debug("Packet received", message);

                if (message.length() > 0) {

                    if (serviceCallbakcs != null) serviceCallbakcs.rxMessageValue(message); //Return the message to the Repository
                }
            }
            return null;
        }

        /**
         * onPostExecute method from the reception task. If the task must keep running (service not
         * stopped), it calls a new reception task again.
         *
         * @param aVoid void
         */
        @Override
        protected void onPostExecute(Void aVoid) {

            if (rx_run) {
                rxTask = new ReceptionTask();
                rxTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //Start the rx task in parallel to the tx task
            }
        }
    }


    /**
     * This class is the transmission AsynTask in charge of sending UDP datagrams to the Arduino.
     *
     * @author Adria Acero, Adria Mallorqui, Jordi Miro
     * @version 2.0
     */
    private static class TransmissionTask extends AsyncTask<Void, Void, Void> {

        /**
         * doInBackground method from the transmission task. It tries to send the corresponding message
         * using the socket. Finally, it informs to the repository about the sent message.
         *
         * @param voids Void
         * @return Void null
         */
        @Override
        protected Void doInBackground(Void... voids) {

            if (!isCancelled() && tx_run){

                try {
                    if (datagramToSend != null) { //Only send a message if there is something to be send

                        byte[] message = datagramToSend.getBytes(); //Parse the datagram string into bytes
                        DebugUtils.debug("TX_TASK Packet sent", datagramToSend);

                        //create the packet to be send using the message and set the destination to the server's port and IP
                        DatagramPacket packet = new DatagramPacket(message, message.length, getByName(Constants.SERVER_IP), Constants.SERVER_PORT);
                        DatagramSocket dsocket = new DatagramSocket(); //create the socket tu send the packet
                        dsocket.send(packet); //send the packet
                        dsocket.close(); //close the socket

                        if (serviceCallbakcs != null) serviceCallbakcs.txMessageValue(datagramToSend); //Return the sent message to the repository

                    } else {

                        DebugUtils.debug("Packet sent: ", "is null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * onPostExecute method from the transmission task. It deactivates the flag indicating that
         * the task has concluded.
         *
         * @param aVoid void
         */
        @Override
        protected void onPostExecute(Void aVoid) {

            tx_run = false;
        }

    }

}
