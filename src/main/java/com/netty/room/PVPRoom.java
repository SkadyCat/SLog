package com.netty.room;

import com.netty.Model.PlayerModel;
import com.netty.OPStrategy.OP_0;
import com.netty.role.STimer;
import com.netty.view.IViewer;
import com.netty.view.ViewInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

public class PVPRoom implements IViewer {
    private List<PlayerModel> playerModelList = new ArrayList<>();
    public static Random random = new Random();
    public int room;
    private int round;
    public int counter;

    //100表示正在等待确认

    private int isConfirmStatu = 0;

    //表示等待时间
    private float confirmTime;
//    Long timeNow=nowDate.getTimeInMillis();
//    Long timeOld=oldDate.getTimeInMillis();
//    Long time = (timeNow-timeOld);//相差毫秒数
    public JSONObject rondEnd(){
        round++;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",302);
        jsonObject.put("round",round%2);
        broadCast(jsonObject);
        return jsonObject;
    }
    public void  broadCast(JSONObject jsonObject){

        for (PlayerModel model:playerModelList
             ) {

            Room.singleSend(model.sender,jsonObject);
        }
        
    }
    public void addPlayerModel(PlayerModel m){
        playerModelList.add(m);

    }
    public static PVPRoom getRoom(int id){

        if (PVPRoomMap.containsKey(id)){

            return PVPRoomMap.get(id);
        }
        return null;

    }
    public void realeaseRoom(){

        System.out.println("本房间呗释放掉了");
        broadCast(releaseInfo());
        STimer.Instance.removeViewer(this);
        PVPRoomMap.remove(this.room);
    }
    public JSONObject getConfirmRoomInfo(){
        JSONObject jsonObject = new JSONObject();
        isConfirmStatu = 100;

        STimer.Instance.addViewer(this);

        jsonObject.put("m",0);
        jsonObject.put("s", OP_0.confirmPVPRoom);
        JSONArray jsonArray = new JSONArray();
        int randomValue = random.nextInt()%1000;
        for (PlayerModel model : playerModelList){
            JSONObject json = model.getPVPInfo();
            json.put("camp",Math.abs(randomValue%2));
            jsonArray.add(json);
            randomValue++;
        }
        jsonObject.put("room",room);
        jsonObject.put("value",jsonArray);

        return jsonObject;


    }
    public JSONObject releaseInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",310);

        return jsonObject;
    }
    public JSONObject getRoomCreateInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s", OP_0.initPVPRoom);
        JSONArray jsonArray = new JSONArray();
        int randomValue = random.nextInt()%1000;
        for (PlayerModel model : playerModelList){
            JSONObject json = model.getPVPInfo();
            json.put("camp",Math.abs(randomValue%2));
            jsonArray.add(json);
            randomValue++;
        }
        jsonObject.put("room",room);
        jsonObject.put("value",jsonArray);

        return jsonObject;
    }

    public static int pointer;
    public static HashMap<Integer,PVPRoom> PVPRoomMap = new HashMap<>();

    public static void confirmPVPRoom(PlayerModel m1,PlayerModel m2){

        PVPRoom room = new PVPRoom();
        room.addPlayerModel(m1);
        room.addPlayerModel(m2);
        room.room = pointer;
        PVPRoomMap.put(pointer,room);
        pointer++;
        room.broadCast(room.getConfirmRoomInfo());


    }

    public static void createPVPRoom(PlayerModel m1,PlayerModel m2){

        PVPRoom room = new PVPRoom();
        room.addPlayerModel(m1);
        room.addPlayerModel(m2);
        room.room = pointer;
        PVPRoomMap.put(pointer,room);
        pointer++;
        room.broadCast(room.getRoomCreateInfo());


    }

    @Override
    public void update(ViewInfo info) {

        switch (info.code){
            case 102:
                if (isConfirmStatu == 100){
                    if (counter == 2){
                        isConfirmStatu = 0;
                        broadCast(getRoomCreateInfo());
                    }
                    confirmTime+= 1;
                    if (confirmTime*15/1000>30){

                        realeaseRoom();
                    }

                }

                break;


        }
    }
}
