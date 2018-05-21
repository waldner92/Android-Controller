package com.android.metg2.androidcontroller.utils;

/**
 *
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

    private String  shape;

    private String angle;

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLightsON() {
        return lightsON;
    }

    public void setLightsON(boolean lightsON) {
        this.lightsON = lightsON;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean manual) {
        isManual = manual;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean isGas() {
        return gas;
    }

    public void setGas(boolean gas) {
        this.gas = gas;
    }

    public boolean isUltraSonic() {
        return ultraSonic;
    }

    public void setUltraSonic(boolean ultraSonic) {
        this.ultraSonic = ultraSonic;
    }

    public boolean isRightBumper() {
        return rightBumper;
    }

    public void setRightBumper(boolean rightBumper) {
        this.rightBumper = rightBumper;
    }

    public boolean isLeftBumper() {
        return leftBumper;
    }

    public void setLeftBumper(boolean leftBumper) {
        this.leftBumper = leftBumper;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

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
}
