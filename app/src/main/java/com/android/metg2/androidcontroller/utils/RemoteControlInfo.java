package com.android.metg2.androidcontroller.utils;

/**
 * This class contains the information of everithing needed for the Arduino's remote control. It is
 * used for the Remote Control challenge.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class RemoteControlInfo {

    /**
     * The current gear
     */
    private int gear;

    /**
     * The current speed
     */
    private int speed;

    /**
     * True if robot lights are on and false if they are off
     */
    private boolean lightsON;

    /**
     * True if the current driving mode is manual and false if it is automatic
     */
    private boolean isManual;

    /**
     * The received temperature
     */
    private float temperature;

    /**
     * True if the robot is accelerating and false if it is stopped
     */
    private boolean gas;

    /**
     * True if the robot detects collision danger with the ultrasonic sensor and false otherwise
     */
    private boolean ultraSonic;

    /**
     * True if the robot detects collision with the right bumper and false otherwise
     */
    private boolean rightBumper;

    /**
     * True if the robot detects collision with the left bumper and false otherwise
     */
    private boolean leftBumper;

    /**
     * The current shape being performed by the Arduino
     */
    private String  shape;

    /**
     * The current turning angle of the Arduino in packet-string format.
     */
    private String angle;

    /**
     * The class constructor. It initializes all variables.
     */
    public RemoteControlInfo() {

        this.gear = 0;
        this.speed = 0;
        this.lightsON = false;
        this.isManual = true;
        this.temperature = 10;
        this.gas = false;
        this.ultraSonic = false;
        this.rightBumper = false;
        this.leftBumper = false;
        this.shape = Constants.SHAPE_N;
        this.angle = Constants.ANGLE_N;
    }

    /**
     *
     * @return int The current gear
     */
    public int getGear() {
        return gear;
    }

    /**
     * @param gear int The gear value to be set
     */
    public void setGear(int gear) {
        this.gear = gear;
    }

    /**
     *
     * @return int The current speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed int The speed value to be set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     *
     * @return boolean True if the lights are turned on, false otherwise
     */
    public boolean isLightsON() {
        return lightsON;
    }

    /**
     *
     * @param lightsON boolean True to indicate that lights are turned on, false otherwise
     */
    public void setLightsON(boolean lightsON) {
        this.lightsON = lightsON;
    }

    /**
     *
     * @return boolean True if the current driving mode is manual, false otherwise
     */
    public boolean isManual() {
        return isManual;
    }

    /**
     *
     * @param manual boolean True to indicate that the current driving mode is manual, false otherwise
     */
    public void setManual(boolean manual) {
        isManual = manual;
    }

    /**
     *
     * @return float The current temperature
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     *
     * @param temperature float The temperature value to be set
     */
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    /**
     *
     * @return boolean True if the robot is accelerating, false if it is stopped
     */
    public boolean isGas() {
        return gas;
    }

    /**
     *
     * @param gas boolean True to indicate that the robot is accelerating, false otherwise
     */
    public void setGas(boolean gas) {
        this.gas = gas;
    }

    /**
     *
     * @return boolean True if the UltraSonic detects collision danger, false otherwise
     */
    public boolean isUltraSonic() {
        return ultraSonic;
    }

    /**
     *
     * @param ultraSonic boolean True to indicate that the UltraSonic detects collision danger, false otherwise
     */
    public void setUltraSonic(boolean ultraSonic) {
        this.ultraSonic = ultraSonic;
    }

    /**
     *
     * @return boolean True if the right bumper detects collision, false otherwise
     */
    public boolean isRightBumper() {
        return rightBumper;
    }

    /**
     *
     * @param rightBumper boolean True to indicate that the right bumper detects collision, false otherwise
     */
    public void setRightBumper(boolean rightBumper) {
        this.rightBumper = rightBumper;
    }

    /**
     *
     * @return boolean True if the left bumper detects collision, false otherwise
     */
    public boolean isLeftBumper() {
        return leftBumper;
    }

    /**
     *
     * @param leftBumper boolean True to indicate that the left bumper detects collision, false otherwise
     */
    public void setLeftBumper(boolean leftBumper) {
        this.leftBumper = leftBumper;
    }

    /**
     *
     * @return String The shape being performed
     */
    public String getShape() {
        return shape;
    }

    /**
     *
     * @param shape String The value to indicate the shape that the Arduino is performing
     */
    public void setShape(String shape) {
        this.shape = shape;
    }

    /**
     *
     * @return String The current turning angle of the Arduino
     */
    public String getAngle() {
        return angle;
    }

    /**
     *
     * @param angle String The value to indicate the current turning angle of the Arduino
     */
    public void setAngle(String angle) {
        this.angle = angle;
    }
}
