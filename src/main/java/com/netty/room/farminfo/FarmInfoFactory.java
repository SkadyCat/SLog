package com.netty.room.farminfo;

import com.netty.Model.FarmData;
import com.netty.Model.SendData;
import com.netty.common.Vector3;
import com.netty.room.CallBackJson;
import com.netty.room.Room;
import com.netty.room.database.WriteDB;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class FarmInfoFactory extends WriteDB {

    private static HashMap<String, FarmInfo> farmDic = new HashMap<>();

    private static FarmInfoFactory Instance;

    public static FarmInfoFactory getInstance(){
        if (Instance == null){

            Instance = new FarmInfoFactory();
        }
        return Instance;
    }
    public static JSONObject FarmAllInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",13);
        JSONArray jsonArray = new JSONArray();

        for (FarmInfo info: farmDic.values()
             ) {
            JSONObject jso2 = JSONObject.fromObject(info);
            jso2.put("m",0);
            jso2.put("s",15);
            jsonArray.add(jso2);

        }
        jsonObject.put("value",jsonArray);

        return jsonObject;

    }
    public FarmInfo insertNewFarm(String userAcc, Vector3 pos,float range){

        FarmInfo info = new FarmInfo(pos,userAcc,range);
        farmDic.put(userAcc,info);
        FarmData data = new FarmData();
        data.setData(userAcc,pos,range);
        Room.BroadCast(data);
        return info;


    }

    @Override
    public void write() {
        super.write();


    }


}
