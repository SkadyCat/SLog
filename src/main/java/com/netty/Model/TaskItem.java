package com.netty.Model;

import java.util.Random;

public class TaskItem {

    private static Random random = new Random();
    private int id;
    private int num;
    public TaskItem(){

        id =Math.abs(random.nextInt()%7)+201;
        num = Math.abs(random.nextInt()%10+5);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
