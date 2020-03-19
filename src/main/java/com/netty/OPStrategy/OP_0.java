package com.netty.OPStrategy;

import com.netty.DB.UserInfoDBOP;
import com.netty.Model.*;
import com.netty.common.Vector3;
import com.netty.role.Role;
import com.netty.role.RoleMap;
import com.netty.role.STimer;
import com.netty.room.MapInfo;
import com.netty.room.PVPRoom;
import com.netty.room.Room;
import com.netty.room.farminfo.FarmInfoFactory;
import com.netty.room.map.MonsterModel;
import com.netty.room.map.MonsterResInfo;
import com.netty.room.map.StaticItem;
import com.netty.room.map.StaticResInfo;
import com.netty.server.DataModel;
import com.netty.server.udpserver.UDPHandler;
import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jdk.nashorn.internal.runtime.Debug;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.net.InetSocketAddress;
import java.util.Date;

public class OP_0  extends SLogStrategy{
    public static  PlayerModel playerModel;
    public static PVPRoom roomModel;
    /**
     * 更新怪物血条值
     */
    public static final int updateMonsterHp = 7;

    public static final int bulletSender = 11;
    public static final int addFarm = 15;
    public static final int userItem = 16;
    public static final int initStaticRes = 25;
    public static final int addNewFarmStaticItem = 26;

    public static final int initMonsterRes = 30;
    public static  final int initUserInfo = 40;
    public static final int update_t_score = 41;
    public static final int shopOp = 42;
    public static final int shopReturnFail = 43;
    public static final int shopReturnSuccess = 44;
    public static final int shopCorp = 45;
    public static final int cropRipe = 50;
    public static final int stealCrop = 51;

    //仇恨操作
    public static final int hosOP = 71;
    //怪物攻击
    public static final int monsterAtk = 72;
    public static final int recoverHosAim = 72;
    //初始化poke的数量
    public static final int petInfo = 74;
    public static final int taskInfo = 75;
    public static final int addNewPet = 76;
    public static final int counterTask = 77;
    public static final int usePokeBall = 78;
    public static final int addPokeBall = 79;
    public static final int initPokeBall = 80;
    public static final int initRealWorld = 81;
    JSONObject jsonObject;
    public static final int confirmPVPRoom = 300;
    public static final int initPVPRoom = 301;

    public static final int roundEnd = 302;
    public static final int createPoke = 303;
    public static final int pokeAtk = 304;
    public static final int directAtk = 305;
    public static final int pvpEnd = 311;
    @Override
    public void subOP(int _subCode) {
        switch (_subCode){

            case 0:
                LoginModel model = (LoginModel) JSONObject.toBean(data.originData,LoginModel.class);

                System.out.println(model.user_acc+"<>"+model.user_pwd);
                playerModel =  Room.addPlayerModel(model.user_acc);
                playerModel.sender = sender;
                Room.BroadCast(Room.getAllUserInfo());
              //  Room.BroadCast(MapInfo.monsterBornInfo().toString().getBytes());
                Room.BroadCast(FarmInfoFactory.FarmAllInfo().toString().getBytes());
                Room.BroadCast(StaticResInfo.getStaticJson().toString().getBytes());
                Room.singleSend(sender,MonsterResInfo.getInitInfo().toString().getBytes());
                Room.singleSend(sender,playerModel.getBagInfo());
                Room.singleSend(sender,playerModel.getUserStatuInfo());
                Room.singleSend(sender,playerModel.getPetInfo());
                Room.singleSend(sender,playerModel.getTaskInfo());

                break;
            case 1:

                break;
            case 2:

                break;

            case 3:
                System.out.println("用户移除"+data.originData);
                int identity = new Integer(data.originData.getString("index"));
                Room.loginMap.put(identity,-1);
              //  Room.BroadCast(jsonObject.toString().getBytes());
              // if (Room.playerModelList.size()>identity){
              //     synchronized (Room.playerModelList){
              //
              //         Room.playerModelList.get(identity).setLoginStatu(-1);
              //     }

              //     //Room.BroadCast(data.originData.toString().getBytes());
              // }

             //JSONObject jsonObject = new JSONObject();
             //jsonObject.put("m",0);
             //jsonObject.put("s",20);
             //jsonObject.put("userAcc",data.originData.getString("user_acc"));
             //Room.removePlayer(data.originData.getString("user_acc"));



                break;


            case 5:

                System.out.println("断开连接！");
                break;

            case updateMonsterHp:
                 int monsterId =  new Integer(data.originData.getString("index"));
                 int deHpValue = data.originData.getInt("value");
                int pIndex = data.originData.getInt("pIndex");
                 System.out.println(data.originData.toString());
                 MonsterModel msm = MapInfo.deHp(monsterId,deHpValue);
                 if (msm.liveStatu ==0){
                     if (msm!= null){

                         msm.setHosPlayer(pIndex);

                     }
                     STimer.Instance.addViewer(msm);
                     Room.BroadCast(msm.getHosAim().toString().getBytes());
                 }

                break;

            case bulletSender:
                Room.BroadCast(data.originData.toString().getBytes());

                break;

            case addFarm:

                jsonObject = JSONObject.fromObject(data.originData);
                FarmData model2 = (FarmData) JSONObject.toBean(jsonObject,FarmData.class);

                FarmInfoFactory.getInstance().insertNewFarm(model2.getUserAcc(),
                        new Vector3(model2.getX(),model2.getY(),model2.getZ()),
                        model2.getRange()
                        );


                break;
            case  userItem:
               // System.out.println(data.originData);
                jsonObject = JSONObject.fromObject(data.originData);
                UserItemModel userItemModel = (UserItemModel) JSONObject.toBean(jsonObject,UserItemModel.class);
                playerModel = Room.getUserModel(userItemModel.getUserAcc());
                playerModel.useItem(userItemModel.getItemID());

                break;

            case  addNewFarmStaticItem:
                jsonObject = JSONObject.fromObject(data.originData);
                StaticItem staticItem = (StaticItem) JSONObject.toBean(jsonObject,StaticItem.class);
                staticItem =  StaticResInfo.addItem(staticItem);
                Room.BroadCast(staticItem);
                break;

            case  shopOp:
                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));

                boolean shopOpStatu = playerModel.shop(
                        new Integer(data.originData.getString("purchValue")),
                        new Integer(data.originData.getString("id"))
                        );

                if (shopOpStatu == true){

                    jsonObject = new JSONObject();
                    jsonObject.put("m",0);
                    jsonObject.put("s",update_t_score);
                    jsonObject.put("t",playerModel.tscore);
                    Room.singleSend(sender,jsonObject);
                    Room.singleSend(sender,playerModel.getBagInfo());
                }

                break;
            case  45:
                jsonObject = JSONObject.fromObject(data.originData);
                JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("value"));
                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));
                for (int i =0;i<jsonArray.size();i++
                     ) {
                   JSONObject j3 = jsonArray.getJSONObject(i);

                   playerModel.changeItemNum(j3.getInt("id"),j3.getInt("num"));

                }
                playerModel.tscore += jsonObject.getInt("allValue");
                jsonObject = new JSONObject();
                jsonObject.put("m",0);
                jsonObject.put("s",update_t_score);
                jsonObject.put("t",playerModel.tscore);
                Room.singleSend(sender,jsonObject);
                break;

            case  cropRipe:

                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));

                int staticIndex = data.originData.getInt("staticIndex");
                int cropType = data.originData.getInt("type");
                int cropID =  data.originData.getInt("id");
                int changeNum = playerModel.changeItemNum(cropID,0,cropType,staticIndex);

                if (cropType == 1){
                    StaticResInfo.deleteStaticItem(staticIndex,changeNum);
                }

                break;

            case 61:
                System.out.println("重新输出怪物");
                Room.singleSend(sender,MonsterResInfo.getInitInfo().toString().getBytes());
                break;
            case addNewPet:
                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));
                int petID = new Integer(data.originData.getString("id"));
                jsonObject = playerModel.addPet(petID);
                Room.singleSend(sender,jsonObject.toString().getBytes());


                break;

            case counterTask:
                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));
                Room.singleSend(sender,playerModel.counterTask());
                break;

            case 78:
                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));

               Room.singleSend( sender,playerModel.usePokeBall().toString().getBytes());
                break;

            case addPokeBall:
                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));
                playerModel.tscore -=2;
                playerModel.pokeBallNum+=1;
                Room.singleSend( sender,playerModel.getPokeBallNum().toString().getBytes());
                break;

            case initRealWorld:
                Room.singleSend( sender,playerModel.getPokeBallNum().toString().getBytes());
                Room.singleSend( sender,playerModel.getUserStatuInfo());
                break;


            case initPVPRoom:
                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));
                Room.addPVPQueue(playerModel);

                break;

            case roundEnd:

                roomModel = PVPRoom.getRoom(new Integer(data.originData.getString("room")));
                roomModel.rondEnd();

                break;
            case  createPoke:
                roomModel = PVPRoom.getRoom(new Integer(data.originData.getString("room")));
                roomModel.broadCast(data.originData);

                break;

            case pokeAtk:
                roomModel = PVPRoom.getRoom(new Integer(data.originData.getString("room")));
                roomModel.broadCast(data.originData);
                break;
            case confirmPVPRoom:
                roomModel = PVPRoom.getRoom(new Integer(data.originData.getString("room")));
                roomModel.counter++;

                break;

            case directAtk:
                roomModel = PVPRoom.getRoom(new Integer(data.originData.getString("room")));
                roomModel.broadCast(data.originData);
                break;


            case pvpEnd:
                roomModel = PVPRoom.getRoom(new Integer(data.originData.getString("room")));

                roomModel.broadCast(data.originData);
                break;
            case 99:

               // Room.removePlayer(data.originData.getString("user_acc"));
                break;
            case 123:
//                System.out.println(data.strContent);

                //STimer.UDPBroadCast(MapInfo.getMonsterStatuInfo());

                break;
            case 200:

                //playerModel.setHeadJumpType(100);
                // System.out.println(playerModel.userAcc + "该角色有反馈！");

                playerModel = Room.playerModelList.get(new Integer(data.originData.getString("index")));
                playerModel.headTime = new Date();
                break;
        }
    }
}
