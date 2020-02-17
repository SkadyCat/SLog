package com.netty.item;

import com.netty.item.bag.SoilItem;
import com.netty.item.crop.plant.Plant1;

public class ItemFactory {

    public static final int housePaper = 1001;

    public static final int plant1 = 1101;
    public static final int plant2 = 1102;
    public static final int plant3 = 1103;
    public static final int plant4 = 1104;
    public static final int plant5 = 1105;
    public static final int plant6 = 1106;
    public static final int plant7 = 1107;
    public static final int soilItem = 2001;


    public static Item factory(int id){

        Item item = new Item();
        item.setId(id);

        return item;
      // switch (id){
      //     case housePaper:

      //         return new HousePaper();
      //     case plant1:
      //         return new Plant1();

      //     case soilItem:


      //         return new SoilItem();
      //     default:
      //         break;

      // }

      // return null;
    }
}
