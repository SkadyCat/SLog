package com.netty.role;

//import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.netty.room.Room;
import com.netty.server.DataModel;
import com.netty.server.NettyHandler;
import com.netty.server.udpserver.UDPHandler;
import com.netty.server.udpserver.UDPServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.util.*;

public class STimer extends TimerTask {

    public static NettyHandler handler;
    int runTimes = 0;

    int headJumpValue = 0;
    int headWaiting = 0;
    public void  headJump(){

        System.out.println("心跳一次");


    }
    public static List<InetSocketAddress> list = new ArrayList<>();
    public static void removeUser(InetSocketAddress value){

        list.remove(value);
    }
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
        headJumpValue++;
        if (headJumpValue>=100){

            if (headJumpValue == 100){

                Room.BroadCast(Room.jumpModel());
                Room.resetHeadJumpValue();
                System.out.println("检查心跳");
            }
            if (headJumpValue>200){


                headJumpValue = 0;
               // Room.removeDeadConnect();


            }

        }
        Room.updatePosition();
       String st = random.nextInt()+"";
        for (InetSocketAddress key :list){

            UDPServer.handler.context.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(Room.getAllPlayerPosition()), key));

        }

    }

    public static void exacute() {

        Timer timer = new Timer("我的定时器");           // 创建一个定时器
        STimer myTimerTask = new STimer();
        timer.schedule(myTimerTask, 100, 30);    //10秒后执行，周期为2秒
        //  handler.chan
    }
}
