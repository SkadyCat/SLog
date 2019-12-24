package com.netty.role;

import com.netty.common.Vector3;
import com.netty.server.IHandler;
import com.netty.server.NettyHandler;
import io.netty.channel.socket.DatagramPacket;


import java.util.UUID;
import java.util.Vector;

public class Role implements IRole {

    public Vector3 position;
    public IHandler handler;
    public String id;
    public Vector3 dir;
    public DatagramPacket dap;

    public Role(IHandler hd){

        this.position = new Vector3();
        this.dir = new Vector3();
        handler = hd;
        id = UUID.randomUUID().toString();
    }

    @Override
    public void move(Vector3 dir) {

        position.add(dir);
    }


}
