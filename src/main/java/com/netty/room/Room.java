package com.netty.room;

import com.netty.Model.PlayerModel;
import com.netty.Model.UserModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;

public class Room {
    private static HashMap<String, PlayerModel> userMap = new HashMap<>();

    public static void removePlayer(String userAcc)
    {
        userMap.remove(userAcc);

    }
    public static byte[] getAllPlayerPosition(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",5);

        JSONArray jsonArray = new JSONArray();

        for (String key : userMap.keySet()){

            userMap.get(key).updatePosition();


            JSONObject jsonObject2 = userMap.get(key).getPositionInfo();

            if (jsonObject2.get("userAcc")!= null){

                jsonArray.add(jsonObject2);
              //  System.out.println(jsonObject2.toString());
            }




        }
        jsonObject.put("value",jsonArray.toString());
       // System.out.println(jsonArray.toString());

        return jsonObject.toString().getBytes();

    }

    public static PlayerModel getUserModel(String userAcc){

        if (userMap.containsKey(userAcc)){

        }
        else {
            userMap.put(userAcc,new PlayerModel(userAcc));

        }
        return  userMap.get(userAcc);

    }

    public  static  void addPlayerModel(String userAcc){

        userMap.put(userAcc,new PlayerModel(userAcc));

    }


}
