package com.netty.room.database;

import java.util.ArrayList;
import java.util.List;

public class WriteDB implements IWriteDB {
    public static List<WriteDB> writeList = new ArrayList<>();
    public WriteDB(){

        System.out.println("初始化WriteDB");
        Awake();
    }


    @Override
    public void Awake() {
        writeList.add(this);
    }

    @Override
    public void write() {

    }


}
