package com.netty.Model;

import com.netty.OPStrategy.OP_0;
import com.netty.common.Vector3;
import com.netty.room.farminfo.FarmInfo;

public class FarmData extends SendData {


    public FarmData(){
        m = 0;
        s = OP_0.addFarm;


    }

    public FarmData setData(String userAcc, Vector3 pos,float range){

        this.userAcc  = userAcc;
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        this.range = range;

        return this;
    }
    private String userAcc;
    private float range;
    private float x;
    private float y;
    private float z;

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
}
