package com.netty.server;

import com.netty.Tool.SendModel;
import io.netty.channel.socket.DatagramPacket;

import javax.xml.crypto.Data;
import java.net.InetSocketAddress;

public interface IHandler {

    String getID();
    void send(DataModel model);

    void process(byte[] data);

    void send(DataModel model, InetSocketAddress dap);

    void process(byte[] data, DatagramPacket dap);


}
