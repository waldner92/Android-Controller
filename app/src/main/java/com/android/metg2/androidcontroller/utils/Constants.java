package com.android.metg2.androidcontroller.utils;

/**
 * This class gathers all constants used in the app.
 */
public class Constants {

    /*----------------------------SPLASH SCREEN--------------------------------*/
    /**
     * Duration of the Splash screen in milliseconds.
     */
    public final static int SPLASH_SCREEN_DELAY = 2300;
    /*-------------------------------------------------------------------------*/

    /*----------------------------LOGS ACTIVITY--------------------------------*/
    /**
     * Header of the logs textView.
     */
    public final static String LOGS_HEADER = "List of Logs:\n";
    /*-------------------------------------------------------------------------*/

    /*----------------------------COMMUNICATION--------------------------------*/
    /**
     * The time in between two consecutive sent frames, in milliseconds.
     */
    public final static long FRAME_PERIOD = 5000;

    /**
     * The IP from the Arduino (the server).
     */
    public final static String SERVER_IP = "192.168.0.158";

    /**
     * The UDP port where the Arduino (the server) is listening connections from.
     */
    public final static int SERVER_PORT =  2390;

    /**
     * The size if the reception buffer (to get the packet from the socket), in bytes.
     */
    public final static int BUFFER_SYZE = 10000;

    /**
     * The local UDP port where the app will send packets from and will listen for responses.
     */
    public final static int LOCAL_PORT =  10101;
    /*-------------------------------------------------------------------------*/

    /*-------------------------RESPONSE PACKET FIELDS--------------------------*/
    //Type:rc,Aut:1,RBump:0,LBump:0,ColUs:1,gear:+2,ledsOn:0,sh:N,ang:00,temp:28.38
    public final static int TYPE_FIELD = 0;
    public final static int MANUAL_FIELD = 1;
    public final static int TEMPERATURE_FIELD = 1;
    public final static int BUMP_FIELD = 1;
    public final static int X_FIELD = 1;
    public final static int Y_FIELD = 2;
    public final static int Z_FIELD = 3;
    public final static int RIGHTB_FIELD = 2;
    public final static int LEFTTB_FIELD = 3;
    public final static int USONIC_FIELD = 4;
    public final static int GEAR_FIELD = 5;
    public final static int LIGHTS_FIELD = 6;
    public final static int SHAPE_FIELD = 7;
    public final static int ANGLE_FIELD = 8;
    public final static int TEMP_FIELD = 9;

    public final static int VALUE_FIELD = 1;
    /*-------------------------------------------------------------------------*/

    /*----------------------------PACKET VALUES--------------------------------*/
    /**
     * Indicates that the message type is Temperature.
     */
    public final static String TEMP_TYPE = "temp";

    /**
     * Indicates that the message type is Bumper.
     */
    public final static String BUMP_TYPE = "bump";

    /**
     * Indicates that the message type is Accelerometer.
     */
    public final static String ACCEL_TYPE = "acc";

    /**
     * Indicates that the message type is Maze.
     */
    public final static String MAZE_TYPE = "ma";

    /**
     * Indicates that the message type is Remote Control.
     */
    public final static String RC_TYPE = "rc";

    /**
     * Indicates that the bumper detected is Right
     */
    public final static String BUMP_RIGHT = "r";

    /**
     * Indicates that the bumper detected is Right
     */
    public final static String BUMP_LEFT = "l";

    /**
     * Indicates that the driving mode must be manual.
     */
    public final static String RC_MAN = "man";

    /**
     * Indicates that the driving mode must be automatic.
     */
    public final static String RC_AUT = "auto";

    /**
     * Indicates that the robot lights must be turned on.
     */
    public final static String RC_LIGTHS_ON = "1";

    /**
     * Indicates that the robot lights must be turned off.
     */
    public final static String RC_LIGTHS_OFF = "0";

    /**
     * Indicates that the robot's gear must be 0.
     */
    public final static String RC_GEAR_0 = "+0";

    /**
     * Indicates that the robot's gear must be 1.
     */
    public final static String RC_GEAR_1 = "+1";

    /**
     * Indicates that the robot's gear must be 2.
     */
    public final static String RC_GEAR_2 = "+2";

    /**
     * Indicates that the robot's gear must be 3.
     */
    public final static String RC_GEAR_3 = "+3";

    /**
     * Indicates that the robot's gear must be 1 (backwards).
     */
    public final static String RC_GEAR_B1 = "-1";

    /**
     * Indicates that the robot's gear must be 2 (backwards).
     */
    public final static String RC_GEAR_B2 = "-2";

    /**
     * Indicates that the robot's gear must be 3 (backwards).
     */
    public final static String RC_GEAR_B3 = "-3";

    /**
     * Indicates that the robot must draw a triangle.
     */
    public final static String SHAPE_T = "T";

    /**
     * Indicates that the robot must draw a square.
     */
    public final static String SHAPE_S = "S";

    /**
     * Indicates that the robot must draw a circle.
     */
    public final static String SHAPE_C = "C";

    /**
     * Indicates that the does not have to draw any shape.
     */
    public final static String SHAPE_N = "N";

    /**
     * Indicates that the robot must go straight forward.
     */
    public final static String ANGLE_N = "00";

    /**
     * Indicates that the robot must turn right (soft mode).
     */
    public final static String ANGLE_R1 = "11";

    /**
     * Indicates that the robot must turn right (hard mode).
     */
    public final static String ANGLE_R2 = "12";

    /**
     * Indicates that the robot must turn left (soft mode).
     */
    public final static String ANGLE_L1 = "21";

    /**
     * Indicates that the robot must turn left (hard mode).
     */
    public final static String ANGLE_L2 = "22";;

    /**
     * Indicates that the robot must stop).
     */
    public final static String ACC_STOP = "0";

    /**
     * Indicates that the robot must accelerate.
     */
    public final static String ACC_START = "1";

    /**
     * Indicates that the robot does not have to move in the maze challenge.
     */
    public final static String MAZE_STOP = "0";

    /**
     * Indicates that the robot must explore the maze solution.
     */
    public final static String MAZE_RIDE_1 = "1";

    /**
     * Indicates that the robot must reproduce the maze solution.
     */
    public final static String MAZE_RIDE_2 = "2";
    /*-------------------------------------------------------------------------*/

    /*----------------------REMOTE CONTROL ACTIVITY----------------------------*/
    /**
     * The maximum gear at which the robot can drive.
     */
    public final static int MAX_GEAR = 3;

    /**
     * The minimum gear at which the robot can drive.
     */
    public final static int MIN_GEAR = -3;

    /**
     * Identifies a circle shape
     */
    public final static String CIRCLE = "Circle";

    /**
     * Identifies a square shape
     */
    public final static String SQUARE = "Square";

    /**
     * Identifies a triangle shape
     */
    public final static String TRIANGLE = "Triangle";

    /**
     * Duration of the collision bumper state (in red) in millisecond
     */
    public final static int BUMPER_SCREEN_DELAY = 1000;
    /*-------------------------------------------------------------------------*/

    /*-----------------------------MAZE ACTIVITY-------------------------------*/
    /**
     * Indicates that the cell is not processed.
     */
    public final static int CELL_NONE = 0;

    /**
     * Indicates that the cell is not explored yet.
     */
    public final static int CELL_UNEXPLORED = 1;

    /**
     * Indicates that the cell is being explored.
     */
    public final static int CELL_EXPLORING = 2;

    /**
     * Indicates that the cell is the current robot's position.
     */
    public final static int CELL_CURRENT = 3;

    /**
     * Indicates that the cell is already explored.
     */
    public final static int CELL_EXPLORED = 4;

    /**
     * Indicates that the cell is part of the maze solution.
     */
    public final static int CELL_SOLUTION = 5;

    /**
     * Indicates that the cell is not part of the maze solution (it has been explored and discarded).
     */
    public final static int CELL_DISCARDED = 6;

    /**
     * Indicates that in that cell edge there is a wall.
     */
    public final static int WALL_TRUE = 7;

    /**
     * Indicates that that cell edge is empty (there is not a wall).
     */
    public final static int WALL_FALSE = 8;
    /*-------------------------------------------------------------------------*/
}
