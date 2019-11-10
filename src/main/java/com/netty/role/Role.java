package com.netty.role;

import com.netty.common.Vector3;
import com.netty.server.NettyHandler;

import java.util.Vector;

public class Role implements IRole {

    public Vector3 position;
    public NettyHandler handler;
    public String id;
    public Vector3 dir;


    public Role(NettyHandler hd){

        this.position = new Vector3();
        this.dir = new Vector3();
        handler = hd;
        id = handler.ID;
    }
    @Override
    public void move(Vector3 dir) {

        position.add(dir);
    }


}
