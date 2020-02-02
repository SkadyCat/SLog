package com.netty.role;

//import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.netty.Model.UserModel;
import com.netty.room.MapInfo;
import com.netty.room.Room;
import com.netty.server.DataModel;
import com.netty.server.NettyHandler;
import com.netty.server.udpserver.UDPHandler;
import com.netty.server.udpserver.UDPServer;
import com.netty.view.IOBViewer;
import com.netty.view.IViewer;
import com.netty.view.ViewInfo;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.util.*;

public class STimer extends TimerTask implements IOBViewer {

    public static NettyHandler handler;
    int runTimes = 0;
    public static STimer Instance;
    int headJumpValue = 0;
    int headWaiting = 0;
    ViewInfo info = new ViewInfo();

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
    int headTimes;
    int waitingReback;
    boolean wait = false;

    public static void UDPBroadCast(byte[] info){
        for (InetSocketAddress key :list){

            UDPServer.handler.context.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(info), key));
        }
    }

    private static Queue<byte[]> sendInfo = new LinkedList<>();

    public static void addBroadCastInfo(byte[] info){

        sendInfo.offer(info);
    }

    @Override
    public void run() {

        info.code = 102;
        info.subCode = 0;
        broadCast(info);
        try {
            headJumpValue++;
            headTimes++;

            if (headJumpValue == 201) {

                Room.detectDeadConnect();
                headJumpValue = 0;
            }

            Room.updatePosition();
            String st = random.nextInt()+"";
           // System.out.println(new String(Room.getAllPlayerPosition()));
//            for (InetSocketAddress key :list){
//
//                UDPServer.handler.context.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(), key));
//
//            }


            sendInfo.offer(Room.getAllPlayerPosition());
            while (!sendInfo.isEmpty()){

                UDPBroadCast(sendInfo.poll());

                //System.out.println("广播消息");
            }


        }catch (Exception e){

            System.out.println("捕获到异常");

        }

    }

    public static void exacute() {

        Timer timer = new Timer("我的定时器");           // 创建一个定时器
        STimer myTimerTask = new STimer();
        timer.schedule(myTimerTask, 100, 30);    //10秒后执行，周期为2秒
        Instance = myTimerTask;
        //  handler.chan
    }
    private static List<IViewer> iViewerList = new ArrayList<>();
    @Override
    public void broadCast(ViewInfo info) {
        for (IViewer viewer:iViewerList
             ) {

            viewer.update(info);

        }
    }

    @Override
    public void addViewer(IViewer viewer) {
        iViewerList.add(viewer);
    }

    @Override
    public void removeViewer(IViewer viewer) {
        iViewerList.remove(viewer);
    }
}
