package com.netty.role;

import com.netty.common.Vector3;
import com.netty.server.DataModel;

import java.util.HashMap;

public class RoleMap {

    public static String userList = "";
    public static  float[] posList = new float[3];
    public static HashMap<String,Role> roleMapHashMap = new HashMap<>();


    public static void  BroadCast(int mainCode,int subCode,String sendStr,float[] fl){


        DataModel sdModel = new DataModel((byte)mainCode,(byte)subCode,sendStr,fl);
        for (Role rl:roleMapHashMap.values()
             ) {

            rl.handler.send(sdModel,rl.dap.sender());
//            if (rl.handler.context.channel().isWritable() == true){
//                rl.handler.context.channel().writeAndFlush(sdModel.bytes);
//            }


        }

    }

    public static void resetuserList(){
        userList = "";
        System.out.println("resetUserList"+roleMapHashMap.size()*3);
        posList = new float[roleMapHashMap.size()*3];
        for (Role ro:roleMapHashMap.values()
        ) {
            userList += ro.id + "#";

        }

        System.out.println(userList);
    }
    public static void resetAllPosition(){



        int index = 0;
        if (posList.length<roleMapHashMap.size()*3){

            posList = new float[roleMapHashMap.size()*3];

        }
        for (Role ro:roleMapHashMap.values()
             ) {

            posList[3*index+0] = ro.position.x;
            posList[3*index+1] = ro.position.y;
            posList[3*index+2] = ro.position.z;
            index +=1;
            //ro.dir = new Vector3();

        }
    }

}
