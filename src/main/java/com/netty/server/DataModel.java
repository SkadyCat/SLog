package com.netty.server;

import com.netty.Tool.ByteUtil;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    public byte[] bytes;
    public byte mainCode;
    public byte subCode;
    public String strContent;
    public float[] floatList;
    public int modelStatus = 0;
    public DataModel(byte[] bytes){

        if (bytes.length<10)
        {
            modelStatus = -1;
            return;
        }

        this.bytes = bytes;
        byte[] strLen =new byte[4];
        this.mainCode = bytes[0];
        this.subCode = bytes[1];
        strLen[0] = bytes[2];
        strLen[1] = bytes[3];
        strLen[2] = bytes[4];
        strLen[3] = bytes[5];
        int va = NettyDecoder.bytesToInt(strLen,0);
        if (va<0){

            va = 0;
        }
        byte[] floatLen = new byte[4];
        floatLen[0] = bytes[6];
        floatLen[1] = bytes[7];
        floatLen[2] = bytes[8];
        floatLen[3] = bytes[9];
        int floatLenValue = NettyDecoder.bytesToInt(floatLen,0);
        byte[] strContent = new byte[va];
        byte[] tempFloat = new byte[4];

        if (bytes.length<10+va){

            modelStatus = -1;
            System.out.println("异常帧，不执行byte长度 =:"+bytes.length+"<字符长度 = >"+
                    va+"float长度 = "+"----"+new String(bytes));
            return;
        }

        int floatNum = floatLenValue/4;
        if (floatNum<0){

            floatNum = 0;
        }
        float[] floatArray =new float[floatNum];
        for (int i =0;i<floatNum;i++){

            for (int j =0;j<4;j++){

                tempFloat[j] = bytes[10+va+4*i+j];
            }
            floatArray[i] = NettyDecoder.getFloat(tempFloat);
        }


        for (int i = 0;i<va;i++){

            strContent[i] = bytes[10+i];
        }

        this.strContent = new String(strContent);
        this.floatList = floatArray;


    }

    public String getFloatStr(){

        String k = "";
        for (float jk:floatList
             ) {
            k+= jk+"<>";

        }
        return  k;
    }

    public static byte[] frameHead()
    {

        byte[] b = new byte[10];
        b[0] = (byte)123;
        b[1] = (byte)56;
        b[2] = (byte)34;
        b[3] = (byte)0;
        b[4] = (byte)0;
        b[5] = (byte)0;
        b[6] = (byte)33;
        b[7] = (byte)1;
        b[8] = (byte)2;
        b[9] = (byte)89;
        return b;
    }
    public static byte[] frameTail(){

        byte[] b = new byte[10];
        b[0] = (byte)254;
        b[1] = (byte)189;
        b[2] = (byte)32;
        b[3] = (byte)0;
        b[4] = (byte)0;
        b[5] = (byte)6;
        b[6] = (byte)33;
        b[7] = (byte)1;
        b[8] = (byte)2;
        b[9] = (byte)33;
        return b;
    }

    public static boolean equal(int offset,byte[] origin,byte[] aim) {
        boolean reb = true;

        if (offset>origin.length -10)
            return false;
        for (int i = 0; i < aim.length; i++) {

            if (aim[i] != origin[offset + i]) {
                return false;
            }
        }
        return reb;
    }
    public DataModel(byte mainCode, byte subCode, String strData,float[] floatList)
    {
        List<Byte> tempList = new ArrayList<Byte>();


        this.mainCode = mainCode;
        this.subCode = subCode;

        byte[] nb = frameHead();

        for (byte fb : nb){

            tempList.add(fb);

         }
        tempList.add(mainCode);
        tempList.add(subCode);

        byte[] strDataList = strData.getBytes();

        byte[] strLen = ByteUtil.getBytes(strDataList.length);
        for (byte b:strLen
        ) {
            tempList.add(b);
        }

        byte[] floatLen = ByteUtil.getBytes(floatList.length*4);

        for (byte b:floatLen
        ) {
            tempList.add(b);
        }

        for (Byte b:strDataList
        ) {

            tempList.add(b);
        }


        for (int i = 0;i<floatList.length;i++)
        {
            float convertValue = floatList[i];
            byte[] bytes = ByteUtil.float2byte(convertValue);

            for ( byte b: bytes
            ) {
                tempList.add(b);
            }

        }

        bytes = new byte[tempList.size()];

        for (int i =0;i<bytes.length;i++)
        {
            bytes[i] = tempList.get(i);
            // System.out.println(data[i]);
        }






    }

    public  DataModel(byte mainCode,byte subCode,String strData){



        List<Byte> tempList = new ArrayList<Byte>();


        this.mainCode = mainCode;
        this.subCode = subCode;
        this.floatList = new float[]{0};
        byte[] nb = frameHead();

        for (byte fb : nb){

            tempList.add(fb);

        }
        tempList.add(mainCode);
        tempList.add(subCode);



        byte[] strDataList = strData.getBytes();

        byte[] strLen = ByteUtil.getBytes(strDataList.length);
        for (byte b:strLen
        ) {
            tempList.add(b);
        }

        byte[] floatLen = ByteUtil.getBytes(floatList.length*4);

        for (byte b:floatLen
        ) {
            tempList.add(b);
        }

        for (Byte b:strDataList
        ) {

            tempList.add(b);
        }


        for (int i = 0;i<floatList.length;i++)
        {
            float convertValue = floatList[i];
            byte[] bytes = ByteUtil.float2byte(convertValue);

            for ( byte b: bytes
            ) {
                tempList.add(b);
            }

        }

        bytes = new byte[tempList.size()];

        for (int i =0;i<bytes.length;i++)
        {
            bytes[i] = tempList.get(i);
            // System.out.println(data[i]);
        }





    }
}
