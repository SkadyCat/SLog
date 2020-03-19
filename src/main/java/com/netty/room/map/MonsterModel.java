package com.netty.room.map;

import com.netty.Model.HosModel;
import com.netty.Model.MonsterAtkModel;
import com.netty.Model.PlayerModel;
import com.netty.OPStrategy.OP_0;
import com.netty.common.Vector3;
import com.netty.role.STimer;
import com.netty.room.Room;
import com.netty.view.IViewer;
import com.netty.view.ViewInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Date;

public class MonsterModel extends MapBaseInfo implements  IViewer {
    public String monsterID;
    public Vector3 position;
    public Vector3 dir;
    public StatuInfo statuInfo;
    public int type;
    public int index;
    private int hosPlayer;
    //100死亡，0，存活
    public int liveStatu;
    public Date deadTime;
    public int getHosPlayer() {
        return hosPlayer;
    }

    public void setHosPlayer(int hosPlayer) {

        if (this.hosPlayer== -1){
            this.hosPlayer = hosPlayer;

        }

    }

    public static byte[] float2byte(float f) {

        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> ( i * 8));
        }


        return b;

    }

    /**
     *
     * @return 返回仇恨对象
     */
    public JSONObject getHosAim(){

        HosModel hosModel = new HosModel(this.index,this.hosPlayer,0,OP_0.hosOP);

        return JSONObject.fromObject(hosModel);

    }

    public JSONObject recoverHosAim(){
        HosModel hosModel = new HosModel(this.index,this.hosPlayer,0,OP_0.recoverHosAim);
        return JSONObject.fromObject(hosModel);
    }
    public JSONObject getStatuInfo(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",7);
        jsonObject.put("index",index);
        jsonObject.put("hp",statuInfo.getHp());
        jsonObject.put("maxhp",statuInfo.getMaxhp());
        return jsonObject;
    }
    public JSONObject getInitInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("index",index);
        jsonObject.put("x",position.x);
        jsonObject.put("z",position.z);
        jsonObject.put("type",type);
        return jsonObject;

    }
    public void  onDead(){

        System.out.println("怪物死亡"+this.hosPlayer);
        STimer.Instance.removeViewer(this);
        this.hosPlayer = -1;
        deadTime = new Date();
        liveStatu = 100;
    }


    public byte[] getPosition(){

        byte[] byteList = new byte[8];

        byte[] xb = float2byte(position.x);
        byte[] zb = float2byte(position.z);
        for (int i =0;i<4;i++){

            byteList[i] = xb[i];

        }
        for(int i = 0;i<4;i++){

            byteList[i+4] = zb[i];
        }
        return byteList;


    }
    public JSONObject getMonsterAtkModel(){
        MonsterAtkModel ATK = new MonsterAtkModel();
        ATK.m = 0;
        ATK.s = OP_0.monsterAtk;
        ATK.pIndex = hosPlayer;
        ATK.mIndex = this.index;
        return JSONObject.fromObject(ATK);
    }
    public MonsterModel(){

        statuInfo = new StatuInfo();

        statuInfo.setHp(100);
        statuInfo.setMaxhp(100);
        statuInfo.setAgi(10);
    }
    public MonsterModel(String id){

        statuInfo = new StatuInfo();

        statuInfo.setHp(100);
        statuInfo.setMaxhp(100);
        statuInfo.setAgi(10);
        statuInfo.setId(id);

    }

    /**
     * 获取某个距离下的所有怪物信息
     * @return
     */
    public static JSONArray getDisMonster(Vector3 aimPos,float dis){


        // float dis1 = Vector3.Distance(this.position,aimPos);
        //
        //return dis + dis2;
        return  null;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  void  updatePos(float x, float y, float z){

        this.position.x += x;
        this.position.y += y;
        this.position.z += z;

    }

    private int liveTime = 0;
    public void updateDir(){


        this.dir = this.dir.random();
        this.dir = this.dir.normal();
        this.dir.mul(2);
       // System.out.println(this.dir.toString());



    }

    public void updatePosition(){

            updateDir();
            this.position.add(this.dir);




    }

    @Override
    public void update() {

        liveTime++;
        updateDir();
        updatePosition();



    }

    @Override
    public void init() {
        this.dir = new Vector3();
        this.dir.random();
        this.dir.normal();
        this.dir.mul(0.05f);
        this.position = new Vector3();
        this.position.random();
        this.position.normal();
        this.position.mul(50);
        this.liveStatu = 0;

    }

    @Override
    public void end() {

    }

    public JSONObject getMonsterPosInfo(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userAcc",id);
        jsonObject.put("x",(int)(position.x*100));
        jsonObject.put("y",(int)(position.y*100));
        jsonObject.put("z",(int)(position.z*100));

        return  jsonObject;
    }

    int atkFrequce;
    @Override
    public void update(ViewInfo info) {
        if (this.hosPlayer== -1){

            STimer.Instance.removeViewer(this);
            return;
        }
        if (this.liveStatu == 100){
            STimer.Instance.removeViewer(this);
        }


        PlayerModel plm = Room.playerModelList.get(hosPlayer);
        if (Vector3.Distance(this.position,plm.getPosition())>22){
            this.hosPlayer = -1;
            STimer.Instance.removeViewer(this);

            Room.BroadCast(recoverHosAim().toString().getBytes());
        }
        if (info.subCode == 200){
            atkFrequce+= 1;

            if (atkFrequce>100){

                atkFrequce = 0;
                Room.BroadCast(getMonsterAtkModel().toString().getBytes());
            }

        }


    }
}
