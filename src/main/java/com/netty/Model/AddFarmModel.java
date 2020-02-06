package com.netty.Model;

public class AddFarmModel extends SendData {

    float x;
    float y;
    float z;
    String userAcc;

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    float range;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc;
    }
}
