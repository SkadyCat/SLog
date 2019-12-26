package com.netty.room;

import com.netty.Model.PlayerModel;
import com.netty.Model.UserModel;
import com.netty.server.udpserver.UDPServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;

public class Room {
    private static HashMap<String, PlayerModel> userMap = new HashMap<>();
    private static final int move = 5;

    private static final int playerEnter = 10;
    public  static  void
     updatePosition(){

        for (String key : userMap.keySet()) {

            userMap.get(key).updatePosition();
        }
    }
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
    public static byte[] getAllUserInfo(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",playerEnter);

        JSONArray jsonArray = new JSONArray();

        for (String key : userMap.keySet()){




            JSONObject jsonObject2 = userMap.get(key).getUserInfo();

            if (jsonObject2.get("userAcc")!= null){

                jsonArray.add(jsonObject2);
                //  System.out.println(jsonObject2.toString());
            }
        }
        jsonObject.put("value",jsonArray.toString());
         System.out.println(jsonArray.toString());

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
    public  static  PlayerModel addPlayerModel(String userAcc){
        PlayerModel model = new PlayerModel(userAcc);
        userMap.put(userAcc,model);

        return  model;

    }


    public static void BroadCast(byte[] value){

        for (PlayerModel model : userMap.values()){

            UDPServer.send(value,model.sender);

        }


    }
}
