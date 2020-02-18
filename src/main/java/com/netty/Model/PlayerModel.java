package com.netty.Model;

import com.netty.OPStrategy.OP_0;
import com.netty.common.Vector3;
import com.netty.item.Item;
import com.netty.item.ItemFactory;
import com.netty.item.crop.plant.Plant1;
import com.netty.room.Room;
import com.netty.room.farminfo.FarmInfoFactory;
import com.netty.room.map.StaticItem;
import com.netty.room.map.StaticResInfo;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.*;

public class PlayerModel extends Model{
    public static Random random = new Random();
    private int loginStatu = -1;

    public int getLoginStatu() {
        return loginStatu;
    }

    public void setLoginStatu(int loginStatu) {
        this.loginStatu = loginStatu;
    }
    public void changeItemNum(int id,int num){

        System.out.println("出售id = "+id+"出售数量+"+num+"剩余数量+"+this.itemMap.get(id).getNum());

        if (this.itemMap.get(id).getNum()>=num){

            this.itemMap.get(id).setNum(this.itemMap.get(id).getNum() - num);
        }

        if (this.itemMap.get(id).getNum() <= 0){

            this.itemMap.get(id).setNum(0);

        }

    }
    public List<Integer> stealList = new ArrayList<>();
    public int changeItemNum(int id,int num,int type,int staticIndex){

        System.out.println("偷窃对象："+staticIndex);
        int tnum = 0;
        switch (type){

            case 1:
                tnum = Math.abs(random.nextInt()%30)+20;
                this.itemMap.get(id).setNum(this.itemMap.get(id).getNum()+tnum);
                break;

            case 0:
                if (!stealList.contains(staticIndex)){

                    stealList.add(staticIndex);
                    tnum = Math.abs(random.nextInt()%10);
                    this.itemMap.get(id).setNum(this.itemMap.get(id).getNum()+tnum);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("m",0);
                    jsonObject.put("s",52);
                    jsonObject.put("id",id);
                    jsonObject.put("num",tnum);
                    Room.singleSend(sender,jsonObject);
                }else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("m",0);
                    jsonObject.put("s",53);
                    jsonObject.put("id",id);

                    Room.singleSend(sender,jsonObject);

                }

                break;
        }

        Room.singleSend(sender,this.getBagInfo());

        return tnum;
    }
    public boolean shop(int value,int id){

        if (this.tscore<value){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("m",0);
            jsonObject.put("s",OP_0.shopReturnFail);

            Room.singleSend(this.sender,jsonObject);
            return false;
        }
        this.addItem(id);
        this.tscore -= value;
        return true;
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

    public JSONObject getUserStatuInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s", OP_0.initUserInfo);
        jsonObject.put("t",tscore);

        return jsonObject;

    }
    private int index;
    public  float x;
    public  float y;
    public  float z;
    public  float yr;
    public int tscore;


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

        this.tscore = 297;
        this.addItem(ItemFactory.housePaper);
        this.addItem(ItemFactory.plant1);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(ItemFactory.soilItem);
        this.addItem(2002);
        this.addItem(2003);
        this.addItem(2004);

        this.addItem(3001);
        this.addItem(3002);
        this.addItem(3003);
        this.addItem(3004);
        //StaticResInfo.addItem(new StaticItem(2001,0,0,0,this.userAcc));
    }

    public JSONObject getUserInfo(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userAcc",userAcc);
       // jsonObject.put("x",x);
       // jsonObject.put("y",y);
       // jsonObject.put("z",z);
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
