package com.netty.room;

import com.netty.OPStrategy.OP_0;
import com.netty.common.Vector3;
import com.netty.role.STimer;
import com.netty.room.map.MonsterModel;
import com.netty.room.map.MonsterResInfo;
import com.netty.view.IViewer;
import com.netty.view.ViewInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

public class MapInfo implements IViewer {

    public  static  Random random = new Random();
    public static MapInfo Instance;

    public static void mapInit(){

        System.out.println("mapInit");
        initMonsterInfo();
        MonsterResInfo.init();
        int index = 0;
        for (MonsterModel info:monsterInfoList.values()
             ) {
            info.updatePosition();
            info.setId(index+"");
            index++;
        }
        Instance = new MapInfo();
        STimer.Instance.addViewer(Instance);

    }
    private  static HashMap<String, MonsterModel> monsterInfoList = new HashMap<>();


    public static  void  initMonsterInfo(){
        for (int i = 0;i<30;i++){
            MonsterModel monsterModel = new MonsterModel(i+"");

            monsterModel.init();
           // System.out.println(monsterInfo.statuInfo.getId());
            monsterInfoList.put(monsterModel.statuInfo.getId(), monsterModel);

        }
    }

    public static void sOut(){
        System.out.println("一次Sout---------------------");
        for (int i = 0;i<30;i++){

            System.out.println(monsterInfoList.get(i).getMonsterPosInfo());
        }

    }

    public static JSONObject monsterPosInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",6);
        JSONArray jsonArray = new JSONArray();

        for (MonsterModel info : monsterInfoList.values()){

            if (random.nextInt()%4 == 0) {

                info.updatePosition();

            }
            jsonArray.add(info.getMonsterPosInfo());

        }
//        System.out.println("位置长度："+jsonArray.size());
        jsonObject.put("value",jsonArray);
        return jsonObject;
    }

    public static JSONObject monsterBornInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",9);
        JSONArray jsonArray = new JSONArray();
        System.out.println("生成所有信息");
        for (MonsterModel info : monsterInfoList.values()){

            jsonArray.add(info.getMonsterPosInfo());
        }

        jsonObject.put("value",jsonArray);
        return jsonObject;
    }
    public static void dead(String monsterID){



        monsterInfoList.remove(monsterID);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",8);
        jsonObject.put("userAcc",monsterID);
        System.out.println("死亡对象："+monsterID);
        STimer.UDPBroadCast(jsonObject.toString().getBytes());
    }

    public static void dead(int index){



        MonsterResInfo.monsterModels.get(index).position.add(new Vector3(100,0,100));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",8);
        jsonObject.put("index",index);
        jsonObject.put("x",MonsterResInfo.monsterModels.get(index).position.x);
        jsonObject.put("z",MonsterResInfo.monsterModels.get(index).position.z);


        MonsterResInfo.monsterModels.get(index).statuInfo.setHp(MonsterResInfo.monsterModels.get(index).statuInfo.getMaxhp());

         Room.BroadCast(jsonObject.toString().getBytes());
    }
    public static void deHp(String monsterID,int hp){
        MonsterModel info =  monsterInfoList.get(monsterID);
        if (info == null){

            System.out.println("monster为空！"+monsterID);
            return;
        }

        boolean deadStatu = info.statuInfo.dehp(hp);


       if (deadStatu == true){
          // dead(monsterID);
       }else {
           JSONObject jsonObject = info.statuInfo.getJson();
           STimer.UDPBroadCast(getMonsterStatuInfo(jsonObject).toString().getBytes());

       }

    }

    public static void deHp(int monsterID,int hp){
        MonsterModel info =  MonsterResInfo.monsterModels.get(monsterID);
        if (info == null){

            System.out.println("monster为空！"+monsterID);
            return;
        }

        boolean deadStatu = info.statuInfo.dehp(hp);


        if (deadStatu == true){
            dead(monsterID);
        }else {
            JSONObject jsonObject = info.statuInfo.getJson();
            STimer.UDPBroadCast(info.getStatuInfo().toString().getBytes());

        }

    }
    public static JSONObject getMonsterStatuInfo(JSONObject jv){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s", OP_0.updateMonsterHp);

        jsonObject.put("id",jv.getString("id"));
        jsonObject.put("hp",jv.getString("hp"));
        jsonObject.put("maxhp",jv.getString("maxhp"));
        return jsonObject;


    }
    public void addMonster(String monsterID){



    }

    public void  removeMosnter(String monsterID){


    }

    int tim1;

    @Override
    public void update(ViewInfo info) {
        switch (info.code){

            case 102:

                tim1++;

                if (tim1%100 == 0){

                    MonsterResInfo.updatePosition();
                    Room.BroadCast(MonsterResInfo.getMonsterPosition());
                }

                // if (tim1 == 400){
               //     for (MonsterInfo i2:monsterInfoList.values()
               //     ) {
               //         i2.updateDir();
               //     }
               //     tim1 = 0;
               // }
               // for (MonsterInfo i2:monsterInfoList.values()
               // ) {
               //
               // }
                break;


            default:

                break;
        }
    }
}
