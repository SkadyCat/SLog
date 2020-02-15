package com.netty.room.map;

import com.netty.Model.SendData;

public class StaticItem extends SendData {

    int id;
    float x;
    float y;
    float z;
    String userAcc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc;
    }

    public StaticItem(int id, float x, float y, float z, String userAcc){

        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.userAcc = userAcc;

    }





}
