package com.android.metg2.androidcontroller.utils;

/**
 * This class gathers all (or most of the) constants used in the app.
 */

public class Constants {


    /**
     * Duration of the Splash screen in milliseconds.
     */
    public final static int SPLASH_SCREEN_DELAY = 2300;

    /**
     * Duration of the Splash screen in milliseconds.
     */
    public final static String LOGS_HEADER = "List of Logs:\n";

    public final static long FramePeriod = 5000;
    public final static String SERVER_IP = "192.168.0.155";//"192.168.1.147";
    public final static int SERVER_PORT =  10101;
    public final static int BUFFER_SYZE = 10000;
    public final static String LOCAL_IP = "192.168.0.158";//"192.168.1.126";
    public final static int LOCAL_PORT =  10102;
    public final static String RX_THREAD_TAG = "ReceptionThread";
    public final static String TX_THREAD_TAG = "TrasmitionThread";



    public final static String RC_TYPE = "rc";
    public final static String ACCEL_TYPE = "ac";
    public final static String MAZE_TYPE = "ma";
    public final static String RC_MAN = "man";
    public final static String RC_AUT = "aut";
    public final static String RC_LIGTHS_ON = "1";
    public final static String RC_LIGTHS_OFF = "0";
    public final static String RC_GEAR_0 = "0";
    public final static String RC_GEAR_1 = "1";
    public final static String RC_GEAR_2 = "2";
    public final static String RC_GEAR_3 = "3";
    public final static String SHAPE_T = "T";
    public final static String SHAPE_S = "S";
    public final static String SHAPE_C = "C";
    public final static String SHAPE_N = "N";
    public final static String ANGLE_N = "00";
    public final static String ANGLE_R1 = "11";
    public final static String ANGLE_R2 = "12";
    public final static String ANGLE_L1 = "21";
    public final static String ANGLE_L2 = "22";;
    public final static String ACC_STOP = "0";
    public final static String ACC_START = "1";
    public final static String MAZE_STOP = "0";
    public final static String MAZE_RIDE_1 = "1";
    public final static String MAZE_RIDE_2 = "2";

    public final static int MAX_GEAR = 3;
    public final static int MIN_GEAR = -3;

    public final static int CELL_NONE = 0;
    public final static int CELL_UNEXPLORED = 1;
    public final static int CELL_EXPLORING = 2;
    public final static int CELL_CURRENT = 3;
    public final static int CELL_EXPLORED = 4;
    public final static int CELL_SOLUTION = 5;
    public final static int CELL_DISCARDED = 6;
    public final static int WALL_TRUE = 7;
    public final static int WALL_FALSE = 8;



}
