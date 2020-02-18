package com.netty.room.map;

import com.netty.Model.SendData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaticItem extends SendData {

    int id;
    float x;
    float y;
    float z;

    String beginTime;

    String userAcc;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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
    public StaticItem(){

    }
    public StaticItem(int id, float x, float y, float z, String userAcc){

        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.userAcc = userAcc;

    }





}
