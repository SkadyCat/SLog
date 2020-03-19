package com.netty.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.CORBA.MARSHAL;

import java.util.Random;
import java.util.Vector;

public class Vector3 {
    public static Random random = new Random();
    public float x;
    public float y;
    public float z;
    public JSONObject getJSON(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x",(int)(x*100));
        jsonObject.put("y",(int)(y*100));
        jsonObject.put("z",(int)(z*100));
        return jsonObject;
    }
    public Vector3(float x,float y,float z){

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vector3){

        this.x = vector3.x;
        this.y = vector3.y;
        this.z = vector3.z;
    }
    public  Vector3(){}
    public Vector3 add(Vector3 v1){

        this.x += v1.x;
        this.y += v1.y;
        this.z += v1.z;
        return this;
    }
    public Vector3 random(){

        this.x = random.nextFloat() - 0.5f;
        this.y = 0;
        this.z = random.nextFloat()- 0.5f;
        return this;
    }
    public Vector3 random(float bs){

        this.x = (random.nextFloat() - 0.5f)*bs;
        this.y = 0;
        this.z = (random.nextFloat() - 0.5f)*bs;
        return this;
    }
    public float sqrt(float v){

        return (float) Math.sqrt(v);
    }
    public float square(float v){

        return (float)Math.pow(v,2);
    }
    public Vector3 normal(){
       float v=  square(x)+sqrt(y)+square(z);
       v =sqrt(v);

       this.x = x/v;
       this.y = y/v;
       this.z = z/v;
       return this;

    }
    public Vector3 mul(float f){

        this.x *= f;
        this.y *= f;
        this.z *= f;
        return this;
    }

    public Vector3 sub(Vector3 ve){

        this.x -= ve.x;
        this.y -= ve.y;
        this.z -= ve.z;
        return this;
    }

    public Vector3 clone(){

        Vector3 vector3 = new Vector3();
        vector3.x = this.x;
        vector3.y = this.y;
        vector3.z = this.z;

        return vector3;
    }
    public static float Distance(Vector3 originPos,Vector3 aimPos){

        Vector3 v1 = originPos.clone();
        Vector3 v2 = aimPos.clone();


        Vector3 vec = v1.sub(v2);

        float dis = vec.square(vec.x)+vec.square(vec.y)+vec.square(vec.z);
        dis = vec.sqrt(dis);
        return dis;




    }

    @Override
    public String toString() {
        String rV = "";
        rV = "(x:"+x+",y:"+y+",z:"+z+")";
        return rV;
    }
}
