package com.netty.role;

import com.netty.common.Vector3;
import com.netty.item.Item;
import com.netty.item.ItemFactory;
import com.netty.room.map.StatuInfo;
import com.netty.server.IHandler;
import com.netty.server.NettyHandler;
import io.netty.channel.socket.DatagramPacket;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;

public class Role implements IRole {

    public Vector3 position;
    public IHandler handler;
    public StatuInfo statuInfo;

    public String id;
    public Vector3 dir;
    private HashMap<Integer, Item> itemMap = new HashMap<>();

    public void  addItem(int id){

        itemMap.put(id, ItemFactory.factory(id));
    }
    public float yr;
    public DatagramPacket dap;

    public JSONObject getBagInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m",0);
        jsonObject.put("s",14);
        JSONArray jsonArray = new JSONArray();
        for (Item item:itemMap.values()
             ) {
            jsonArray.add(Item.getJSON(item));

        }
        jsonObject.put("value",jsonArray);

        return jsonObject;

    }
    public Role(IHandler hd){

        this.position = new Vector3();
        this.dir = new Vector3();
        handler = hd;
        id = UUID.randomUUID().toString();
        statuInfo = new StatuInfo();
        this.addItem(ItemFactory.housePaper);
    }

    @Override
    public void move(Vector3 dir) {

        position.add(dir);
    }


}
