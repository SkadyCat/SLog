package com.netty.role;

//import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.netty.server.DataModel;
import com.netty.server.NettyHandler;

        import java.util.Timer;
import java.util.TimerTask;

public class STimer extends TimerTask {

    public static NettyHandler handler;
    int runTimes = 0;
    @Override
    public void run() {
//        System.out.println("时间在运行！");
        for (Role ro:RoleMap.roleMapHashMap.values()
             ) {

            ro.position.add(ro.dir);

        }

        if (RoleMap.roleMapHashMap.size()>0){
           //System.out.println(RoleMap.getPoseList());
           //System.out.println(RoleMap.userList);
            RoleMap.resetAllPosition();

            for (Role rl :RoleMap.roleMapHashMap.values()){

                rl.handler.send(new DataModel((byte) 0,(byte)2,"all roleMap",new float[]{1.3f,1.5f,1.7f}));
            }
           // RoleMap.BroadCast(0,2,RoleMap.userList,RoleMap.posList);

        }


    }

    public static void exacute() {

        Timer timer = new Timer("我的定时器");           // 创建一个定时器
        STimer myTimerTask = new STimer();
        timer.schedule(myTimerTask, 100, 200);    //10秒后执行，周期为2秒
        //  handler.chan
    }
}
