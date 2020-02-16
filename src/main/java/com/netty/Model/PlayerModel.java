package com.netty.Model;

import com.netty.common.Vector3;
import com.netty.item.Item;
import com.netty.item.ItemFactory;
import com.netty.item.crop.plant.Plant1;
import com.netty.room.farminfo.FarmInfoFactory;
import com.netty.room.map.StaticItem;
import com.netty.room.map.StaticResInfo;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlayerModel extends Model{

    private int loginStatu = -1;

    public int getLoginStatu() {
        return loginStatu;
    }

    public void setLoginStatu(int loginStatu) {
        this.loginStatu = loginStatu;
    }

    public static byte[] float2byte(float f) {

        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> ( i * 8));
        }

    //// 翻转数组
    //int len = b.length;
    //// 建立一个与源数组元素类型相同的数组
    //byte[] dest = new byte[len];
    //// 为了防止修改源数组，将源数组拷贝一份副本
    //System.arraycopy(b, 0, dest, 0, len);
    //byte temp;
    //// 将顺位第i个与倒数第i个交换
    //for (int i = 0; i < len / 2; ++i) {
    //    temp = dest[i];
    //    dest[i] = dest[len - i - 1];
    //    dest[len - i - 1] = temp;
    //}

        return b;

    }
    public String userAcc = "";

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public byte[] getPos(){
        List<Byte> byteList = new ArrayList<>();
        byteList.add((byte)this.getIndex());
        byte[] xb = float2byte(x);
        byte[] yb = float2byte(y);
        byte[] zb = float2byte(z);
        String s = "";
        for (int i =0;i<4;i++){

            byteList.add(xb[i]);
            s+= xb[i]+"<>";
        }
        //System.out.println(xb[0]);
        for (int i =0;i<4;i++){

            byteList.add(yb[i]);

        }
        for (int i =0;i<4;i++){

            byteList.add(zb[i]);
            s+=zb[i]+"<>";
        }
        byte[] reByte = new byte[byteList.size()];
        for(int i =0;i<byteList.size();i++){
            reByte[i] = byteList.get(i);

        }

        return reByte;

    }
    private int index;
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

        initPlayerRes();
    }
    public void  initPlayerRes(){

        this.addItem(ItemFactory.housePaper);
        this.addItem(ItemFactory.plant1);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        //StaticResInfo.addItem(new StaticItem(2001,0,0,0,this.userAcc));
    }

    public JSONObject getUserInfo(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userAcc",userAcc);
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("z",z);
        jsonObject.put("index",index);
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
