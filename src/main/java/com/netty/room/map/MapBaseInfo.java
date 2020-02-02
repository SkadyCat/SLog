package com.netty.room.map;

import com.netty.role.Role;
import com.netty.server.IHandler;

public abstract class MapBaseInfo {


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
