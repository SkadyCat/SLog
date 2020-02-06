package com.netty.room.map;

import com.netty.role.Role;
import com.netty.server.IHandler;

import java.util.Random;

public abstract class MapBaseInfo {

    public static Random random = new Random();
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;

    public abstract void update();
    public abstract void init();
    public abstract void end();
}
