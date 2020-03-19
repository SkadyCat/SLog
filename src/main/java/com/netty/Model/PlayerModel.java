package com.netty.Model;

import com.netty.OPStrategy.OP_0;
import com.netty.common.Vector3;
import com.netty.item.Card;
import com.netty.item.Item;
import com.netty.item.ItemFactory;
import com.netty.item.crop.plant.Plant1;
import com.netty.room.Room;
import com.netty.room.farminfo.FarmInfoFactory;
import com.netty.room.map.StaticItem;
import com.netty.room.map.StaticResInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.awt.font.TextHitInfo;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerModel extends Model{
    public static Random random = new Random();
    private int loginStatu = -1;

    public int getLoginStatu() {
        return loginStatu;
    }
    public Vector3 getPosition(){

        return new Vector3(x,y,z);
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
    public List<Card> cardList = new ArrayList<>();
    public List<Integer> stealList = new ArrayList<>();
    public List<TaskItem> taskList = new ArrayList<>();
    public HashMap<Integer,TaskItem>  petMap =new HashMap<>();
    public int Counter = 0;
    public int pokeBallNum;

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
    public int pvpHp;
    public int getIndex() {
        return index;
    }

    public JSONObject getRealWorldUpdateInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",84);
        jsonObject.put("tscore",tscore);
        jsonObject.put("pokeball",pokeBallNum);

        return jsonObject;
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

    public JSONObject getPVPInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("index",this.index);
        jsonObject.put("userAcc",this.userAcc);
        return jsonObject;

    }
    public JSONObject getUserStatuInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s", OP_0.initUserInfo);
        jsonObject.put("t",tscore);
        jsonObject.put("index",this.index);

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
    public String taskBeginTime = "";
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

    public JSONObject getPokeBallNum(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",OP_0.initPokeBall);
        jsonObject.put("value",pokeBallNum);
        jsonObject.put("tscore",tscore);
        return jsonObject;
    }

    public JSONObject usePokeBall(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",OP_0.usePokeBall);
        if (this.pokeBallNum <= 0){

            jsonObject.put("value","none");
        }else {
        this.pokeBallNum-=1;
            jsonObject.put("value",this.pokeBallNum);
        }


        return jsonObject;

    }
    public JSONObject getMonsterInfo(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("m",1);
        jsonObject.put("s",0);
        JSONArray jsonArray = new JSONArray();
        for (int i =0;i<cardList.size();i++){

            jsonArray.add(jsonObject.fromObject(cardList.get(i)));

        }
        jsonObject.put("value",jsonArray);

        return jsonObject;

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

        updateTask();
        this.pokeBallNum = 20;
        //StaticResInfo.addItem(new StaticItem(2001,0,0,0,this.userAcc));
    }
    public JSONObject counterTask(){

        JSONObject jsonObject = new JSONObject();
        HashMap<Integer,TaskItem> taskItems = new HashMap<>();

        for (TaskItem item:petMap.values()
             ) {
            TaskItem Titem = new TaskItem();
            Titem.setNum(item.getNum());
            Titem.setId(item.getId());
            taskItems.put(Titem.getId(),Titem);
        }

        for (TaskItem item:taskList
             ) {
            if (!taskItems.containsKey(item.getId())){

                jsonObject.put("m",0);
                jsonObject.put("s",OP_0.counterTask);
                jsonObject.put("value","-1");
                return jsonObject;
            }
            int rest =  taskItems.get(item.getId()).getNum() - item.getNum();

            if (rest<0){
                jsonObject.put("m",0);
                jsonObject.put("s",OP_0.counterTask);
                jsonObject.put("value","-1");
                return jsonObject;
            }
        }
        for (TaskItem item:taskList
        ) {
            int rest =  petMap.get(item.getId()).getNum() - item.getNum();
            petMap.get(item.getId()).setNum(rest);
        }
        Room.singleSend(sender,getPetInfo());

        this.tscore += Counter;
        Room.singleSend(sender,getUserStatuInfo().toString().getBytes());
        jsonObject.put("m",0);
        jsonObject.put("s",OP_0.counterTask);
        jsonObject.put("value","1");
        return jsonObject;



    }
    public void  addPet(int id,int num){

        if (petMap.containsKey(id)){
            petMap.get(id).setNum(petMap.get(id).getNum()+num);
        }
        else {
            TaskItem item = new TaskItem();
            item.setId(id);
            petMap.put(id, item);
            petMap.get(id).setNum(num);
        }
    }
    public JSONObject addPet(int id){

        JSONObject jsonObject = new JSONObject();
        if (petMap.containsKey(id)){

            petMap.get(id).setNum(petMap.get(id).getNum()+1);

        }else {

            petMap.put(id,new TaskItem());
            petMap.get(id).setNum(1);
        }
        jsonObject.put("m",0);
        jsonObject.put("s",OP_0.addNewPet);
        jsonObject.put("id",id);
        jsonObject.put("num",petMap.get(id).getNum());
        return jsonObject;

    }
    public void  updateTask(){

        taskList.clear();
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        taskBeginTime = df.format(day);

        for (int i =0;i<5;i++){

            TaskItem taskItem = new TaskItem();
            taskList.add(taskItem);

        }
        for (int i = 201;i<208;i++){

            addPet(i,15);
        }
        Counter = random.nextInt()%200+300;
    }
    public JSONObject getTaskInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",OP_0.taskInfo);
        JSONArray tempArray  = new JSONArray();
        jsonObject.put("Counter",Counter);
        jsonObject.put("Time",taskBeginTime);
        for (int i =0;i<taskList.size();i++){

            tempArray.add(JSONObject.fromObject(taskList.get(i)));
        }
        jsonObject.put("value", tempArray);

        return jsonObject;
    }
    public JSONObject getPetInfo(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",OP_0.petInfo);
        JSONArray tempArray  = new JSONArray();

        for (TaskItem item : petMap.values()){

            tempArray.add(JSONObject.fromObject(item));

        }
        jsonObject.put("value", tempArray);

        return jsonObject;
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
