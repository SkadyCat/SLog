package com.netty.OPStrategy;

import com.netty.server.DataModel;
import com.netty.server.NettyHandler;

public abstract class OPStreategyEX extends  OPStrategy {

    public NettyHandler handler;

    DataModel data;

    @Override
    public void doSomething(NettyHandler handler,DataModel value) {
        this.handler = handler;

        this.data = value;

        subOP(this.data.subCode);

    }


    public abstract void subOP(int _subCode);


}
