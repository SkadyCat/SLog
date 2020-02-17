package com.netty.room.map;

import com.netty.common.Vector3;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

public class MonsterModel extends MapBaseInfo {
    public String monsterID;
    public Vector3 position;
    public Vector3 dir;
    public StatuInfo statuInfo;
    public int type;
    public int index;
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
