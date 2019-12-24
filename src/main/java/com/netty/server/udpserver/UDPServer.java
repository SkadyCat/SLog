package com.netty.server.udpserver;

import com.netty.role.STimer;
import com.netty.server.NettyDecoder;
import com.netty.server.NettyEncoder;
import com.netty.server.NettyHandler;
import com.netty.server.NettyServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.haproxy.HAProxyCommand;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.Executors;

public class UDPServer {

    public static UDPHandler handler;
    public void run(int port) throws Exception{
        STimer.exacute();
        EventLoopGroup group = new NioEventLoopGroup();

        EventLoopGroup childGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        //由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
        handler = new UDPHandler();
        handler.setID();

      b.group(group).channel(NioDatagramChannel.class)
              .option(ChannelOption.SO_BROADCAST, true)//支持广播
              .handler(handler);//ChineseProverbServerHandler是业务处理类
      b.bind(port).sync().channel().closeFuture().await();

//----------------------------------------


    }

    public static void main(String[] args) throws Exception {
        new UDPServer().run(9001);
        //STimer.EX
    }

}
