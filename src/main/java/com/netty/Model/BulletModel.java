package com.netty.Model;

public class BulletModel extends SendData {
    private String sender;
    private String monsterID;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMonsterID() {
        return monsterID;
    }

    public void setMonsterID(String monsterID) {
        this.monsterID = monsterID;
    }
}
