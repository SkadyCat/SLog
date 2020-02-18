package com.netty.OPStrategy;

import com.netty.DB.UserInfoDBOP;
import com.netty.Model.*;
import com.netty.common.Vector3;
import com.netty.role.Role;
import com.netty.role.RoleMap;
import com.netty.role.STimer;
import com.netty.room.MapInfo;
import com.netty.room.Room;
import com.netty.room.farminfo.FarmInfoFactory;
import com.netty.room.map.MonsterResInfo;
import com.netty.room.map.StaticItem;
import com.netty.room.map.StaticResInfo;
import com.netty.server.DataModel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jdk.nashorn.internal.runtime.Debug;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.Date;

public class OP_0  extends SLogStrategy{
    public static  PlayerModel playerModel;

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
    JSONObject jsonObject;


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
                break;
            case 1:

                //System.out.println(data.originData);

            //  MoveModel model1 = (MoveModel) JSONObject.toBean(data.originData,MoveModel.class);
            //  //
            //  Room.getUserModel(model1.getUserAcc()).setDir(model1.getX(),model1.getY(),model1.getZ());

//                Room.getUserModel(model1.getUserAcc()).updatePosition();
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

                 System.out.println(data.originData.toString());
                 MapInfo.deHp(monsterId,deHpValue);

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
