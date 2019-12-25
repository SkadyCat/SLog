package com.netty.OPStrategy;

import com.netty.Model.SendData;
import com.netty.server.DataModel;
import com.netty.server.IHandler;
import com.netty.server.NettyHandler;
import com.netty.server.udpserver.UDPHandler;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.net.InetSocketAddress;

public abstract class OPStreategyEX extends  OPStrategy {

    public IHandler handler;


    SendData data;

    @Override
    public void doSomething(IHandler handler,SendData value) {
        this.handler = handler;

        this.data = value;

        subOP(this.data.s);

    }


    public abstract void subOP(int _subCode);


}
