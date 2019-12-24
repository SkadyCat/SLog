package com.netty.server.udpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class UDPMultiPortServer {



        private EventLoopGroup bossGroup = new NioEventLoopGroup();
        private EventLoopGroup workerGroup = new NioEventLoopGroup();

        private ServerBootstrap bootstrap = new ServerBootstrap();
        private ChannelFuture[] ChannelFutures = null;
        private int beginPort = 0;
        private int endPort = 0;

        public UDPMultiPortServer(int beginPort, int endPort) {
            this.beginPort = beginPort;
            this.endPort = endPort;
        }

        public static void main(String[] args) {
            UDPMultiPortServer server = new UDPMultiPortServer(6000, 8000);
            server.start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            server.stopServerChannel(6000);

        }

        public void start() {
            System.out.println("server starting....");
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);

            bootstrap.childHandler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new UDPHandler());
                }
            });

            if (ChannelFutures == null) {
                ChannelFutures = new ChannelFuture[endPort - beginPort + 1];
            }

            //多个端口绑定
            for (int i = beginPort; i <= endPort; i++) {
                final int port = i;
                ChannelFuture channelFuture = bootstrap.bind(port);
                ChannelFutures[i - beginPort] = channelFuture;
                channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("Started success,port:" + port);
                        } else {
                            System.out.println("Started Failed,port:" + port);
                        }
                    }
                });
            }
//            int indexs = 0;
//            for (int i = 0; i <= endPort - beginPort; i++) {
//                final Channel channel = ChannelFutures[i].channel();
//                indexs = i;
//                final int finalIndexs = indexs;
//                channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
//                    @Override
//                    public void operationComplete(Future<? super Void> future) {
//                        System.out.println("channel close !");
//                        channel.close();
//                        ChannelFutures[finalIndexs] = null;
//                    }
//                });
//            }

        }

        public void stopAll() {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("stoped");
        }

        //关闭单个端口的NioServerSocketChannel
        public void stopServerChannel(int port) {
            int i = port - beginPort;
            if (0 <= i && i <= ChannelFutures.length) {
                if (ChannelFutures != null) {
                    ChannelFutures[i].channel().close();
                }
                System.out.println("stoped " + port);
            }

        }

    }

