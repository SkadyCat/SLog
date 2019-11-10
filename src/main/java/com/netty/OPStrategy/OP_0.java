package com.netty.OPStrategy;

import com.netty.DB.UserInfoDBOP;
import com.netty.Model.UserModel;
import com.netty.common.Vector3;
import com.netty.role.Role;
import com.netty.role.RoleMap;
import com.netty.server.DataModel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class OP_0  extends SLogStrategy{

    @Override
    public void subOP(int _subCode) {
        switch (_subCode){

            case 0:
                System.out.println("有玩家进入游戏");
                opRole = new Role(handler);
                RoleMap.roleMapHashMap.put(handler.ID,opRole);
                handler.context.channel().writeAndFlush(new DataModel((byte)0,(byte)0,handler.ID,new float[]{1.0f}).bytes);

                break;

            case 2:
                System.out.println("获取所有数据"+RoleMap.userList);

                RoleMap.resetAllPosition();
                RoleMap.BroadCast(0,1,RoleMap.userList,RoleMap.posList);
                break;
            case 1:

                //System.out.println("更新方向信息");
                opRole = RoleMap.roleMapHashMap.get(handler.ID);
                opRole.dir = new Vector3(data.floatList[0],data.floatList[1],data.floatList[2]);
                System.out.println(opRole.id +">>"+opRole.dir.toString());

                break;
            case 123:
                System.out.println(data.strContent);

                break;
        }
    }
}
