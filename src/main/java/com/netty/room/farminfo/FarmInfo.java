package com.netty.room.farminfo;

import com.netty.common.Vector3;

public class FarmInfo {


    public FarmInfo(Vector3 vector3,String userAcc,float range){

        this.x = vector3.x;
        this.y = vector3.y;
        this.z = vector3.z;
        this.userAcc = userAcc;
        this.range  = range;
    }
    private float z;
    private float x;
    private float y;

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

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

    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    private String userAcc;
    private float range;


}
