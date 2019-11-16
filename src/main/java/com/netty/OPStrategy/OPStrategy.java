package com.netty.OPStrategy;

import com.netty.Tool.SendModel;
import com.netty.server.DataModel;
import com.netty.server.IHandler;
import com.netty.server.NettyHandler;
import com.netty.server.udpserver.UDPHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;

public abstract class OPStrategy {


    public abstract void doSomething(IHandler handler, DataModel value);
}
