package com.netty.server.udpserver;

import com.netty.Model.SendData;
import com.netty.OPStrategy.OPStrategy;
import com.netty.OPStrategy.OP_0;
import com.netty.OPStrategy.OP_10;
import com.netty.role.RoleMap;
import com.netty.role.STimer;
import com.netty.server.DataModel;
import com.netty.server.IHandler;
import com.netty.server.NettyDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;
import net.sf.json.JSONObject;

import javax.xml.crypto.Data;
import java.awt.font.TextHitInfo;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Random;
import java.util.UUID;

public class UDPHandler extends
        SimpleChannelInboundHandler<DatagramPacket> implements IHandler {

    public String ID;
    public ChannelHandlerContext context;
    public static Random random = new Random();
    DatagramPacket dataBag;

    public String getRandom(int len){

        String empl = "";

        for (int i =0;i<len;i++){

            empl+= Math.abs((random.nextInt()%10))+"";
        }

        return  empl;

    }
    public void setID(){

        ID = getRandom(5);
        System.out.println("...."+this.ID);
    }


    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {


        System.out.println("连接,,,,,,,,,,,"+this.ID);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        ID = getRandom(5);
        System.out.println("..<>.."+this.ID);
        context = ctx;



    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("UDP通道已经连接");
        super.channelActive(ctx);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    DataModel cache;
    @Override
    public void  process(byte[] data){


        DataModel model = new DataModel(data);
        if (model.modelStatus == -1)
            return;
        cache = model;
        //DataModel model2 = new DataModel((byte)5,(byte)5,"89898989",new float[]{1.312f,123.2f});
        OPStrategy strategy = null;
        switch (model.mainCode){

            case 0:
                strategy = new OP_0();
                break;

            default:
                break;
        }
        if(strategy != null){

           // strategy.doSomething(this,model);
        }


    }

    @Override
    public void send(DataModel model, InetSocketAddress dap) {
        context.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(model.bytes), dap));

    }

    @Override
    public void process(byte[] data, DatagramPacket dap) {

        String v1 = new String(data);


        JSONObject sendData = JSONObject.fromObject(v1);


        int mainCode = new Integer(sendData.get("m").toString());
        int subCode = new Integer(sendData.get("s").toString());
//        System.out.println("->process "+sendData.toString());
        if (mainCode%100 == 0){

           // System.out.println(sendData.toString());
        }
        SendData sd = new SendData();
        sd.m = mainCode;
        sd.s = subCode;
        sd.originData = sendData;
        //DataModel model2 = new DataModel((byte)5,(byte)5,"89898989",new float[]{1.312f,123.2f});
        OPStrategy strategy = null;
        switch (sd.m){

            case 0:
                strategy = new OP_0();
                break;

            case 10:
                strategy = new OP_10();
                break;

            default:
                break;
        }
        if(strategy != null){
            strategy.sender = dap.sender();
            strategy.doSomething(this,sd);
        }



    }
   final byte[] data = new byte[1024*2];

    public void  clearDataCahce(){

        for (byte b:data
             ) {

            b = 0;
        }
    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {

        clearDataCahce();
        datagramPacket.content().getBytes(0,data);
        process(data,datagramPacket);
        STimer.addUser2List(datagramPacket.sender());

    }

    @Override
    public String getID() {


        return this.ID;
    }

    @Override
    public void send(DataModel model) {

//        if (model.floatList!= null){
//            System.out.println("发送数据"+model.getFloatStr());
//        }
//        System.out.println("调用发送数据接口！");


        context.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(model.bytes), dataBag.sender()));

    }


}
