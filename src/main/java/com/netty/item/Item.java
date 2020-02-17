package com.netty.item;

import net.sf.json.JSONObject;
import org.omg.CORBA.PRIVATE_MEMBER;

public class Item {

    private int num;

    public int getNum() {
        return num;
    }
    public  void  addNum(){

        this.num += 1;
    }
    public void  useNum(){
        if(this.num>0){
            this.num -= 1;

        }
    }
    public void setNum(int num) {
        this.num = num;
    }

    public Item(){
        init();
        addNum();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    private int id;
    private String item_name;
    private String describe;

    public void init(){


    }

    public static JSONObject getJSON(Item item){

        return JSONObject.fromObject(item);

    }

}
