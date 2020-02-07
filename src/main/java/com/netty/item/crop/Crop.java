package com.netty.item.crop;

import com.netty.item.Item;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Crop extends Item {

    private int num;

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public void setNum(int num) {
        this.num = num;
    }

    private float bornTime;
    private float matureTime;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final String animal = "animal";
    public  static final  String plant = "plant";
    public  static final  String outItem = "outItem";
    public float getBornTime() {
        return bornTime;
    }

    public void setBornTime(float bornTime) {
        this.bornTime = bornTime;
    }
    private List<OutItem> outItemList = new ArrayList<>();

    public void addOutItem(OutItem item){

        this.outItemList.add(item);
    }
    public void  getOutItem(){



    }


    public float getMatureTime() {
        return matureTime;
    }

    public void setMatureTime(float matureTime) {
        this.matureTime = matureTime;
    }

    @Override
    public void init() {

    }
}
