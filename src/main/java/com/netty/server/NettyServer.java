package com.netty.server;


import com.netty.role.STimer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyServer {

	public int readerIdleTimeSeconds = 10;
	public int writerIdleTimeSeconds = 10;
	public int allIdleTimeSeconds = 30;
	
	public void start() {
		STimer.exacute();
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(parentGroup, childGroup);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childOption(ChannelOption.SO_RCVBUF, 1024*20);
		bootstrap.childOption(ChannelOption.SO_SNDBUF, 1024*20);
		//bootstrap.childOption(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK,new WriteBuffer)
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
			
			@Override
			protected void initChannel(NioSocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();

				pipeline.addLast("idle", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
				pipeline.addLast("decoder", new NettyDecoder());
				pipeline.addLast("handler", new NettyHandler());
				pipeline.addLast("encoder", new NettyEncoder());
			}
			
		});
		try {
			ChannelFuture future = bootstrap.bind(9001).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
	}

	public static void mains(String[] args) {
		new NettyServer().start();

	}

}
