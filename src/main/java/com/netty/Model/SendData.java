package com.netty.Model;

import net.sf.json.JSONObject;

public class SendData extends Model {
    public int m;
    public int s;

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public JSONObject originData;
    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }


}
