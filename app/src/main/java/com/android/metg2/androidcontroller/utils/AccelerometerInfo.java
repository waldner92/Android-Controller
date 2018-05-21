package com.android.metg2.androidcontroller.utils;

/**
 * .
 */

public class AccelerometerInfo {

    private Float x;
    private Float y;
    private Float z;

    public AccelerometerInfo() {

        x = new Float(0);
        y = new Float(0);
        z = new Float(0);
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        this.z = z;
    }
}

