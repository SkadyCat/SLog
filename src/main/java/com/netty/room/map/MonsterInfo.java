package com.netty.room.map;

import com.netty.common.Vector3;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

public class MonsterInfo extends MapBaseInfo {
    public String monsterID;
    public Vector3 position;
    public Vector3 dir;
    public StatuInfo statuInfo;
    public MonsterInfo(){

        statuInfo = new StatuInfo();

        statuInfo.setHp(100);
        statuInfo.setMaxhp(100);
        statuInfo.setAgi(10);
    }
    public MonsterInfo(String id){

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
        this.dir.mul(0.05f);
       // System.out.println(this.dir.toString());



    }

    public void updatePosition(){
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
        this.position.mul(2);
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




}
