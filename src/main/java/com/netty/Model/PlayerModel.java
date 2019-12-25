package com.netty.Model;

import com.netty.common.Vector3;
import net.sf.json.JSONObject;

public class PlayerModel extends Model{

    public String userAcc = "";
    public  float x;
    public  float y;
    public  float z;
    public Vector3 dir = new Vector3();

    public PlayerModel(String userAcc){


        this.userAcc = userAcc;
    }
    public PlayerModel setDir(float x,float y,float z){

        dir.x = x;
        dir.y = y;
        dir.z = z;

        return this;
    }

    public  void  updatePosition(){

        this.x += dir.x;
        this.y  += dir.y;
        this.z += dir.z;


    }
    public JSONObject getPositionInfo(){


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userAcc",userAcc);
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("z",z);

        return jsonObject;

    }

}
