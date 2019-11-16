package com.netty.OPStrategy;

import com.netty.server.DataModel;
import com.netty.server.IHandler;
import com.netty.server.NettyHandler;
import com.netty.server.udpserver.UDPHandler;
import org.omg.CORBA.PUBLIC_MEMBER;

public abstract class OPStreategyEX extends  OPStrategy {

    public IHandler handler;


    DataModel data;

    @Override
    public void doSomething(IHandler handler,DataModel value) {
        this.handler = handler;

        this.data = value;

        subOP(this.data.subCode);

    }


    public abstract void subOP(int _subCode);


}
