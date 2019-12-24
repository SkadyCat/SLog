package com.netty.role;

//import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.netty.server.DataModel;
import com.netty.server.NettyHandler;
import com.netty.server.udpserver.UDPHandler;
import com.netty.server.udpserver.UDPServer;

import java.net.InetSocketAddress;
import java.util.*;

public class STimer extends TimerTask {

    public static NettyHandler handler;
    int runTimes = 0;

    public static List<InetSocketAddress> list = new ArrayList<>();
    public static void addUser2List(InetSocketAddress value){

        if (list.contains(value))
        {
            return;

        }else {

            list.add(value);
        }

    }
    static  Random random = new Random();
    @Override
    public void run() {
//        System.out.println("时间在运行！");
      // for (Role ro:RoleMap.roleMapHashMap.values()
      //      ) {

      //     ro.position.add(ro.dir);

      // }

      // if (RoleMap.roleMapHashMap.size()>0){
      //     //System.out.println(RoleMap.getPoseList());
      //     RoleMap.resetAllPosition();
      //
      //     RoleMap.BroadCast(0,2,RoleMap.userList,RoleMap.posList);

      // }
       String st = random.nextInt()+"";
        for (InetSocketAddress key :list){

            UDPServer.handler.send(new DataModel((byte)0,(byte)10,st,new float[]{1.0f}),key);
        }

    }

    public static void exacute() {

        Timer timer = new Timer("我的定时器");           // 创建一个定时器
        STimer myTimerTask = new STimer();
        timer.schedule(myTimerTask, 100, 2000);    //10秒后执行，周期为2秒
        //  handler.chan
    }
}
