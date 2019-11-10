package com.netty.Tool;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SendModel extends ClientTool {
    public String strData;
    public ArrayList floatList;

    public  int strLen;
    public int floatLen;
    public byte[] data;
    public byte subCode;
    public  byte mainCode;

    public SendModel(){


    }


    public SendModel(byte mainCode, byte subCode, String strData, ArrayList<Float> floatList)
    {
        List<Byte> tempList = new ArrayList<Byte>();


        this.mainCode = mainCode;
        this.subCode = subCode;
        tempList.add(mainCode);
        tempList.add(subCode);



        byte[] strDataList = strData.getBytes();

        byte[] strLen = ByteUtil.getBytes(strDataList.length);
        for (byte b:strLen
             ) {
            tempList.add(b);
        }

        byte[] floatLen = ByteUtil.getBytes(floatList.size()*4);

        for (byte b:floatLen
        ) {
            tempList.add(b);
        }

        for (Byte b:strDataList
             ) {

            tempList.add(b);
        }


        for (int i = 0;i<floatList.size();i++)
        {
            float convertValue = floatList.get(i);
            byte[] bytes = ByteUtil.float2byte(convertValue);

            for ( byte b: bytes
                 ) {
                tempList.add(b);
            }

        }

        data = new byte[tempList.size()];

        for (int i =0;i<data.length;i++)
        {
            data[i] = tempList.get(i);
           // System.out.println(data[i]);
        }






    }

    public String floatListConvertString()
    {
        String value = "";
        for (Object fvalue: floatList
             ) {

            value += (float)fvalue+",";


        }
        return value;

    }
    @Override
    public String toString() {
        String value = "";
        value += "mainCode = "+mainCode+"\n";
        value += "subCode = "+subCode+"\n";
        value += "strData = "+strData+"\n";
        value += "floatList = "+floatListConvertString()+"\n";

        return value;
    }
}
