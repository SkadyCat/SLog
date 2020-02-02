package com.netty.OPStrategy;

import com.netty.DB.UserInfoDBOP;
import com.netty.Model.LoginModel;
import com.netty.Model.MoveModel;
import com.netty.Model.PlayerModel;
import com.netty.Model.UserModel;
import com.netty.common.Vector3;
import com.netty.role.Role;
import com.netty.role.RoleMap;
import com.netty.role.STimer;
import com.netty.room.MapInfo;
import com.netty.room.Room;
import com.netty.server.DataModel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jdk.nashorn.internal.runtime.Debug;
import net.sf.json.JSON;
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
    @Override
    public void subOP(int _subCode) {
        switch (_subCode){

            case 0:
                LoginModel model = (LoginModel) JSONObject.toBean(data.originData,LoginModel.class);

                System.out.println(model.user_acc+"<>"+model.user_pwd);

                playerModel =  Room.addPlayerModel(model.user_acc);
                playerModel.sender = sender;
                Room.BroadCast(Room.getAllUserInfo());
                Room.BroadCast(MapInfo.monsterBornInfo().toString().getBytes());
                break;
            case 1:

                //System.out.println(data.originData);

                MoveModel model1 = (MoveModel) JSONObject.toBean(data.originData,MoveModel.class);
                //
                Room.getUserModel(model1.getUserAcc()).setDir(model1.getX(),model1.getY(),model1.getZ());
                break;
            case 2:

                break;

            case 3:
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("m",0);
                jsonObject.put("s",3);
                jsonObject.put("userAcc",data.originData.getString("user_acc"));
                Room.removePlayer(data.originData.getString("user_acc"));
                STimer.removeUser(sender);
                Room.BroadCast(jsonObject.toString().getBytes());

                break;


            case 5:

                System.out.println("断开连接！");
                break;

            case updateMonsterHp:
                 String monsterId =  data.originData.getString("userAcc");
                 int deHpValue = data.originData.getInt("value");

                 System.out.println(data.originData.toString());
                 MapInfo.deHp(monsterId,deHpValue);

                break;

            case bulletSender:
                Room.BroadCast(data.originData.toString().getBytes());

                break;
            case 123:
//                System.out.println(data.strContent);

                //STimer.UDPBroadCast(MapInfo.getMonsterStatuInfo());

                break;
            case 200:

                //playerModel.setHeadJumpType(100);
                // System.out.println(playerModel.userAcc + "该角色有反馈！");

                    Room.getUserModel((data.originData.getString("user_acc"))).headTime = new Date();
                   break;
        }
    }
}
