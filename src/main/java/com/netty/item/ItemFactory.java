package com.netty.item;

public class ItemFactory {

    public static final int housePaper = 1001;

    public static final int plant1 = 1101;
    public static final int plant2 = 1102;
    public static final int plant3 = 1103;
    public static final int plant4 = 1104;
    public static final int plant5 = 1105;
    public static final int plant6 = 1106;
    public static final int plant7 = 1107;



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
