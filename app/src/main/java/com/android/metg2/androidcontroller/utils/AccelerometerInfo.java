package com.android.metg2.androidcontroller.utils;

/**
 * This class contains the three G force values (Gx, Gy and Gz) of the Arduino's accelerometer. It is
 * used for the Accelerometer challenge.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class AccelerometerInfo {

    /**
     * The Gx value of the accelerometer
     */
    private Float x;

    /**
     * The Gy value of the accelerometer
     */
    private Float y;

    /**
     * The Gz value of the accelerometer
     */
    private Float z;

    /**
     * The class constructor. It initializes all variables to 0.
     */
    public AccelerometerInfo() {

        x = new Float(0);
        y = new Float(0);
        z = new Float(0);
    }

    /**
     * Accelerometer's Gx getter
     * @return Float the Gx value of the accelerometer
     */
    public Float getX() {
        return x;
    }

    /**
     * Accelerometer's Gx setter
     * @param x Float the Gx value of the accelerometer should be between -1 and 1
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * Accelerometer's Gy getter
     * @return Float the Gy value of the accelerometer
     */
    public Float getY() {
        return y;
    }

    /**
     * Accelerometer's Gy setter
     * @param y Float the Gy value of the accelerometer should be between -1 and 1
     */
    public void setY(Float y) {
        this.y = y;
    }

    /**
     * Accelerometer's Gz getter
     * @return Float the Gz value of the accelerometer
     */
    public Float getZ() {
        return z;
    }

    /**
     * Accelerometer's Gz setter
     * @param z Float the Gz value of the accelerometer should be between -1 and 1
     */
    public void setZ(Float z) {
        this.z = z;
    }
}

