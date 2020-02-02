package com.netty.room.map;

import net.sf.json.JSONObject;

public class StatuInfo {

    private int maxhp;
    private int hp;
    private int maxmp;
    private int mp;
    private int def;
    private int atk;
    private int str;
    private int agi;
    private int inte;
    private String id;
    public JSONObject getJson(){


       JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
       jsonObject.put("maxhp",maxhp);
       jsonObject.put("hp",hp);

       return jsonObject;
    }
    public boolean  dehp(int value){
        this.hp -= value;

        if (this.hp<=0){

            return true;
        }
        return  false;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public int getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxmp() {
        return maxmp;
    }

    public void setMaxmp(int maxmp) {
        this.maxmp = maxmp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getAgi() {
        return agi;
    }

    public void setAgi(int agi) {
        this.agi = agi;
    }

    public int getInte() {
        return inte;
    }

    public void setInte(int inte) {
        this.inte = inte;
    }
}
