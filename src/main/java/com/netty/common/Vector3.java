package com.netty.common;

import java.util.Vector;

public class Vector3 {
    public float x;
    public float y;
    public float z;
    public Vector3(float x,float y,float z){

        this.x = x;
        this.y = y;
        this.z = z;
    }
    public  Vector3(){}
    public Vector3 add(Vector3 v1){

        this.x += v1.x;
        this.y += v1.y;
        this.z += v1.z;
        return this;
    }

    @Override
    public String toString() {
        String rV = "";
        rV = "(x:"+x+",y:"+y+",z:"+z+")";
        return rV;
    }
}
