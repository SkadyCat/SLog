package com.netty.item.crop.plant;

import com.netty.item.crop.Plant;

public class Plant1 extends Plant {

    @Override
    public void init() {
        super.init();
        setItem_name("红果");
        setId(1101);
        setMatureTime(10);
        //setDescribe("");
    }
}
