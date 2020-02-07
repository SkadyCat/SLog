package com.netty.Model;

import com.netty.common.Vector3;
import com.netty.item.Item;
import com.netty.item.ItemFactory;
import com.netty.room.farminfo.FarmInfoFactory;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;

public class PlayerModel extends Model{

    private int loginStatu = -1;

    public int getLoginStatu() {
        return loginStatu;
    }

    public void setLoginStatu(int loginStatu) {
        this.loginStatu = loginStatu;
    }

    public String userAcc = "";
    public  float x;
    public  float y;
    public  float z;
    public  float yr;
    public Vector3 dir = new Vector3();
    public Date headTime = new Date();
    public boolean  useItem(int id){
        if (itemMap.containsKey(id)){
            itemMap.get(id).useNum();
            System.out.println("useItem");
            return true;
        }
        return false;
    }

    private HashMap<Integer, Item> itemMap = new HashMap<>();

    private HashMap<Integer,Item> cropItem = new HashMap<>();
    public void  addItem(int id){
        if (itemMap.containsKey(id)){

            itemMap.get(id).addNum();
            return;
        }
        itemMap.put(id, ItemFactory.factory(id));
    }
    public JSONObject getBagInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",14);
        JSONArray jsonArray = new JSONArray();
        for (Item item:itemMap.values()
        ) {
            jsonArray.add(JSONObject.fromObject(item));

        }
        jsonObject.put("value",jsonArray);

        return jsonObject;

    }

    public int getHeadJumpType() {
        return headJumpType;
    }

    public void setHeadJumpType(int headJumpType) {
        this.headJumpType = headJumpType;
    }

    private int headJumpType;
    public InetSocketAddress sender;
    public PlayerModel(String userAcc){


        this.userAcc = userAcc;
        this.addItem(ItemFactory.housePaper);
    }
    public JSONObject getUserInfo(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userAcc",userAcc);
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("z",z);
        return jsonObject;

    }
    public PlayerModel setDir(float x,float y,float z){

        dir.x = x;
        dir.y = y;
        dir.z = z;

        return this;
    }

    public  void  updatePosition(){

        this.x += dir.x;
        this.y  += dir.y;
        this.z += dir.z;


    }
    public JSONObject getPositionInfo(){


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userAcc",userAcc);
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("z",z);

        return jsonObject;

    }

}
