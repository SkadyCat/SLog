package com.netty.room.map;

import com.netty.OPStrategy.OP_0;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticResInfo {

    private static final int soil = 2001;
    private static final List<StaticItem> staticItemList = new ArrayList<>();


    public static void addItem(StaticItem item){
        staticItemList.add(item);
    }

    public static JSONObject getStaticJson(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("m", 0);
        jsonObject.put("s",OP_0.initStaticRes);
        JSONArray jsonArray = new JSONArray();

        for(int i = 0;i<staticItemList.size();i++){

            jsonArray.add(JSONObject.fromObject(staticItemList.get(i)));


        }
        jsonObject.put("value",jsonArray);
        return jsonObject;
    }


}
