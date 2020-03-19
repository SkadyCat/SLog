package com.netty.Model;

public class HosModel extends SendData {


    public HosModel(int mIndex, int pIndex,int m,int s) {
        this.mIndex = mIndex;
        this.pIndex = pIndex;
        this.m = m;
        this.s = s;
    }
    public HosModel(){}

    private int mIndex;
    private int pIndex;

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public int getpIndex() {
        return pIndex;
    }

    public void setpIndex(int pIndex) {
        this.pIndex = pIndex;
    }
}
