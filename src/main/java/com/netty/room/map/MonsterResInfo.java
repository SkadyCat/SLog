package com.netty.room.map;

import com.netty.OPStrategy.OP_0;
import com.netty.common.Vector3;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterResInfo {

    public static List<MonsterModel> monsterModels = new ArrayList<>();
    public  static Random random = new Random();

    public static void init(){


        for (int i =0;i<100;i++){
            MonsterModel monsterModel = new MonsterModel();
            monsterModel.init();
            monsterModel.index = monsterModels.size();
            monsterModels.add(monsterModel);
        }
    }
    public static JSONObject getInitInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",OP_0.initMonsterRes);
        JSONArray jsonArray = new JSONArray();
        for (int i =0;i<monsterModels.size();i++){
            //System.out.println(monsterModels.get(i).index);
            if (random.nextInt()%4 == 0){

                jsonArray.add(monsterModels.get(i).getInitInfo());
            }


        }
        jsonObject.put("value",jsonArray);

        return jsonObject;

    }
    public static void updatePosition(){

        for (MonsterModel model:monsterModels
             ) {
            model.updatePosition();
        }

    }

    public static byte[] getMonsterPosition(){
        byte[] byteList = new byte[monsterModels.size()*8+1];
        byteList[0] = 109;
        int index =1;
        for (MonsterModel md:monsterModels
             ) {
            byte[] bt = md.getPosition();
            for(int i =0;i<bt.length;i++){
                byteList[index] = bt[i];
                index = index+1;
            }

        }
        //System.out.println(byteList.length);
        return byteList;

    }
}
