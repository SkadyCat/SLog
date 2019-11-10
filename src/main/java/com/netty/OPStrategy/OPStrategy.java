package com.netty.OPStrategy;

import com.netty.Tool.SendModel;
import com.netty.server.DataModel;
import com.netty.server.NettyHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;

public abstract class OPStrategy {

    public abstract void doSomething(NettyHandler handler, DataModel value);

}
