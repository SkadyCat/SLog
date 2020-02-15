package com.netty.room;

import com.netty.Model.PlayerModel;
import com.netty.Model.SendData;
import com.netty.Model.UserModel;
import com.netty.role.STimer;
import com.netty.server.udpserver.UDPServer;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.xml.datatype.Duration;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Room {
    private static HashMap<String, PlayerModel> userMap = new HashMap<>();
    private static final int move = 5;

    private static final int playerEnter = 10;
    public  static  void
     updatePosition(){

        for (String key : userMap.keySet()) {

            userMap.get(key).updatePosition();

//            System.out.println("用户状态："+userMap.get(key).getLoginStatu());
        }
    }
    public static void removePlayer(String userAcc)
    {
        if (userMap.containsKey(userAcc)){

            userMap.get(userAcc).setLoginStatu(-1);

        }

    }

    public static void singleSend(InetSocketAddress address, byte[] data){


        UDPServer.send(data,address);
    }
    public static void singleSend(InetSocketAddress address, JSONObject jsonObject){
        System.out.println(jsonObject.toString());
        UDPServer.send(jsonObject.toString().getBytes(),address);
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

    public static int loginNum(){

        int sum = 0;
        for (PlayerModel mod:playerModelList){

            if (mod.getLoginStatu() == 1){

                sum ++;
            }
        }
        return sum;
    }
    public static byte[] getAllPosition(){
        int loginSum = loginNum();
        byte[] bytes = new byte[loginSum*13+1];
        bytes[0] = 111;
        int index = 1;

        for (PlayerModel md:playerModelList
             ) {

            if (md.getLoginStatu() == 1){

                byte[] posByte = md.getPos();
                for(int i =0;i<posByte.length;i++){

                    bytes[index] = posByte[i];


                    index++;
                }
            }

        }
        if (bytes.length>1){
           // System.out.println(""+bytes[1]);
        }


        return bytes;
    }
    public static byte[] getAllUserInfo(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",playerEnter);

        JSONArray jsonArray = new JSONArray();

        for (String key : userMap.keySet()){

            if (userMap.get(key).getLoginStatu() == 1){

                JSONObject jsonObject2 = userMap.get(key).getUserInfo();

                if (jsonObject2.get("userAcc")!= null){

                    jsonArray.add(jsonObject2);
                    //  System.out.println(jsonObject2.toString());
                }

            }


        }
         jsonObject.put("value",jsonArray.toString());
         System.out.println("getAll->"+jsonArray.toString());

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

    public static List<PlayerModel> playerModelList = new ArrayList<>();
    public  static  PlayerModel addPlayerModel(String userAcc){

        if (userMap.containsKey(userAcc)){

        }else {
            PlayerModel model = new PlayerModel(userAcc);
            model.setIndex(playerModelList.size());
            playerModelList.add(model);
            System.out.println("添加"+model.userAcc+"的用户进入，对应id = "+model.getIndex());
            userMap.put(userAcc,model);
        }

        userMap.get(userAcc).setLoginStatu(1);
        userMap.get(userAcc).headTime = new Date();
        return  userMap.get(userAcc);

    }
    public static byte[] jumpModel(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("m",0);
        jsonObject.put("s",200);

        return jsonObject.toString().getBytes();

    }
    public static void resetHeadJumpValue(){
        for (String key : userMap.keySet()) {

            userMap.get(key).setHeadJumpType(0);
        }

    }

    public static void detectDeadConnect(){

        Date date = new Date();
        JSONArray jsonArray = new JSONArray();
        List<String> deadList = new ArrayList<>();
        for(PlayerModel md:userMap.values()
             ) {

            long diff = date.getTime() - md.headTime.getTime();
            float sValue = diff/1000;

//            System.out.println(md.userAcc+"有那么多时间没理我了："+sValue);
            if (sValue>30)
            {
//                System.out.println(md.userAcc+"是僵尸号"+sValue);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userAcc",md.userAcc);
                jsonArray.add(jsonObject);
                deadList.add(md.userAcc);

            }
            if (jsonArray.size()>0){

                JSONObject jsonObject = new JSONObject();


                jsonObject.put("m",0);
                jsonObject.put("s",201);
                jsonObject.put("value",jsonArray.toString());
                BroadCast(jsonObject.toString().getBytes());


            }



        }
        for (String key:deadList
        ) {
            STimer.removeUser(userMap.get(key).sender);
            userMap.get(key).setLoginStatu(-1);

        }

    }
    public static void removeDeadConnect(){
        List<String> detectAnswer = new ArrayList<>();
        for (String key : userMap.keySet()) {

         if (userMap.get(key).getHeadJumpType() == 0){

             detectAnswer.add(key);

         }
        }
        JSONArray deadList = new JSONArray();
        for (String v:detectAnswer
             ) {

            STimer.removeUser(userMap.get(v).sender);
            userMap.remove(v);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userAcc", v);
            deadList.add(jsonObject);
            System.out.println("僵尸角色："+jsonObject.toString());
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",4);
        jsonObject.put("value",deadList.toString());

        Room.BroadCast(jsonObject.toString().getBytes());

    }
    public static void BroadCast(byte[] value){
       // System.out.println(userMap.size()+"广播内容："+new String(value));
        for (PlayerModel model : userMap.values()){

            if (model.getLoginStatu() == 1){

                UDPServer.send(value,model.sender);
            }


        }


    }

    public static void BroadCast(SendData data) {
        // System.out.println(userMap.size()+"广播内容："+new String(value));
        JSONObject jsonObject = JSONObject.fromObject(data);
        try {
            BroadCast(jsonObject.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
