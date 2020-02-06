package com.netty.item;

public class HousePaper extends Item {
    @Override
    public void init() {
        this.setId(1001);
        this.setItem_name("房契");
        this.setDescribe("你可以通过它购买一处农场");

    }
}
