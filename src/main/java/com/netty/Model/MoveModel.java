package com.netty.Model;

public class MoveModel extends  SendData {

    private String userAcc;
    private  float x;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public  MoveModel(String userAcc, float x, float y, float z){

    this.userAcc = userAcc;
    this.x = x;
    this.y = y;
    this.z = z;
}
    public MoveModel(){}
    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc;
    }

    float y;
    float z;

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
