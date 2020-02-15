package com.netty.item.bag;

import com.netty.item.Item;

public class SoilItem extends Item {
    @Override
    public void init() {
        setId(2001);
        setDescribe("耕地券，可以在自己农场范围内开辟出一块用来种植植物的耕地");
        setItem_name("耕地券");
        setNum(1);
    }
}
