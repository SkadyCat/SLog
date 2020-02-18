package com.netty.room.map;

import com.netty.Model.SendData;
import com.netty.OPStrategy.OP_0;
import com.netty.room.Room;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StaticResInfo {

    private static final int soil = 2001;
    private static final HashMap<Integer,StaticItem> staticItemList = new HashMap<>();
    public static int staticCounter = 0;

    public static void deleteStaticItem(int staticIndex,int num){


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",60);
        jsonObject.put("index",staticIndex);
        jsonObject.put("num",num);

        jsonObject.put("id",staticItemList.get(staticIndex).id);
        staticItemList.remove(staticIndex);
        Room.BroadCast(jsonObject.toString().getBytes());

    }
    public static StaticItem addItem(StaticItem item){
        item.index = staticCounter;
        Date preTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        item.setBeginTime( formatter.format(preTime));
        staticItemList.put(staticCounter,item);
        staticCounter++;
        return item;
    }

    public static JSONObject getStaticJson(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("m", 0);
        jsonObject.put("s",OP_0.initStaticRes);
        JSONArray jsonArray = new JSONArray();

        for (StaticItem item:staticItemList.values()
             ) {
            jsonArray.add(JSONObject.fromObject(item));
        }

        jsonObject.put("value",jsonArray);
        return jsonObject;
    }


}
