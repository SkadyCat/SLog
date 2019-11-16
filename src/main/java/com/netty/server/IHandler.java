package com.netty.server;

import com.netty.Tool.SendModel;

import javax.xml.crypto.Data;

public interface IHandler {

    String getID();
    void send(DataModel model);

    void process(byte[] data);


}
