package com.netty.item;

public class ItemFactory {

    public static final int housePaper = 1001;


    public static Item factory(int id){


        switch (id){
            case housePaper:

                return new HousePaper();

            default:
                break;

        }

        return null;
    }
}
