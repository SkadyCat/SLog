package com.netty.OPStrategy;

import com.netty.DB.UserInfoDBOP;
import com.netty.Model.UserModel;
import com.netty.common.Vector3;
import com.netty.role.Role;
import com.netty.role.RoleMap;
import com.netty.server.DataModel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jdk.nashorn.internal.runtime.Debug;

public class OP_0  extends SLogStrategy{

    @Override
    public void subOP(int _subCode) {
        switch (_subCode){

            case 0:
                System.out.println("有玩家进入游戏");
                opRole = new Role(handler);
                RoleMap.roleMapHashMap.put(handler.getID(),opRole);
                RoleMap.resetuserList();
                handler.send(new DataModel((byte)0,(byte)0,handler.getID(),new float[]{1.0f}));

                break;

            case 2:
//
                if (data.strContent .equals("qbs")){
                    System.out.println("获取所有数据"+RoleMap.userList);
                    RoleMap.resetAllPosition();
                    handler.send(new DataModel((byte) 0,(byte)1,RoleMap.userList,RoleMap.posList));
                    // RoleMap.BroadCast(0,1,RoleMap.userList,RoleMap.posList);


                }

               // System.out.println();

                break;
            case 1:

//                //System.out.println("更新方向信息");
                opRole = RoleMap.roleMapHashMap.get(handler.getID());
                opRole.dir = new Vector3(data.floatList[0],data.floatList[1],data.floatList[2]);
//                //System.out.println(opRole.id +">>"+opRole.dir.toString());
                opRole.move(opRole.dir);
                //RoleMap.resetAllPosition();
               // System.out.println(opRole.position);
                handler.send(new DataModel((byte)0,(byte)2,opRole.id,new float[]{opRole.position.x,
                        opRole.position.y,opRole.position.z}));
                break;
            case 123:
//                System.out.println(data.strContent);

                break;
        }
    }
}
