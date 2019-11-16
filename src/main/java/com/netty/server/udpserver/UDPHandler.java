package com.netty.server.udpserver;

import com.netty.OPStrategy.OPStrategy;
import com.netty.OPStrategy.OP_0;
import com.netty.role.RoleMap;
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

import javax.xml.crypto.Data;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.UUID;

public class UDPHandler extends
    SimpleChannelInboundHandler<DatagramPacket> implements IHandler {

    public String ID;
    public ChannelHandlerContext context;
    DatagramPacket dataBag;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        ID = UUID.randomUUID().toString();
        System.out.println(this.ID);
        context = ctx;



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

            strategy.doSomething(this,model);
        }


    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        byte[] data = new byte[1024];
        datagramPacket.content().getBytes(0,data);
        dataBag = datagramPacket;
        process(data);
       // context.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(cache.bytes), datagramPacket.sender()));
        System.out.println("向客户端写入数据");
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
