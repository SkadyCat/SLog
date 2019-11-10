package com.netty.Tool;

import java.io.*;
import java.util.ArrayList;

public class ClientTool {
    public static byte[] str2ByteArray(String value)
    {
        byte[] bytes = value.getBytes();

        return bytes;


    }

    public static int byte2int(byte[] dataByte)
    {
        int n = 0;
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(dataByte);
            DataInputStream dataInput = new DataInputStream(byteInput);
            n = dataInput.readInt();
            System.out.println("整数为： " + n);
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return n;

    }

    public static byte[] float2Byte(float value)
    {

        return  ByteUtil.getBytes(value);
    }

    public static String byte2String(byte[] byteList)
    {

        return  new String((byteList));
    }

    public static ArrayList<Float> byte2Float(byte[] byteList)
    {
        ArrayList<Float> floatList = new ArrayList<Float>();


        if (byteList.length==0)
        {

            return floatList;
        }


        ArrayList<Byte> tempList = new ArrayList<Byte>();
      //  System.out.println("floatList长度="+byteList.length);

        for (int i =0;i<byteList.length/4;i++)
        {
            byte[] tempData = new byte[4];
            for (int j =0;j<4;j++)
            {
                try{

                    tempData[j] = byteList[i*4+j];
                }catch (Exception e)
                {
                    System.out.println("异常Index = "+(i*4+j)+"<>"+byteList.length);

                }



            }
            float value = ByteUtil.getFloat(tempData );
            floatList.add(value);
        }
   //  for (int i = 0; i < byteList.length; i++)
   //  {


   //      tempList.add(byteList[i]);

   //      if (tempList.size() % 4 == 0)
   //      {
   //          Object[] tempByte =tempList.toArray();

   //          byte[] te = new byte[4];

   //          for (int j =0;i<4;j++)
   //          {
   //              te[j] = (byte)tempByte[j];
   //          }

   //          float value = ByteUtil.getFloat(te );
   //          floatList.add(value);


   //          tempList.clear();

   //      }

   //  }

        return floatList;
    }


    public static byte[] int2Byte(int value)
    {
        byte[] byteArray = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);
            dataOut.writeInt(value);
            byteArray = byteOut.toByteArray();
            for (byte b : byteArray) {
                System.out.println(" " + b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;

    }
    public static byte[] floatArray2ByteArray(ArrayList<Float> floatArray)
    {

        ArrayList<Byte> byteArray = new  ArrayList<Byte>();

        for (float bt : floatArray)
        {
            byte[] btValue = float2Byte(bt);

            for (byte tempBValue : btValue)
            {
                byteArray.add(tempBValue);
            }



        }

        byte[] byteList = new byte[byteArray.size()];

        for (int i =0;i<byteList.length;i++)
        {

            byteList[i] = byteArray.get(i);
        }

        return byteList;
    }

    public static SendModel analysis(byte[] valueList



    ) {

        SendModel model = new SendModel();


        model.mainCode = valueList[0];
        model.subCode = valueList[1];
        byte[] byteList = new byte[4];

        byteList[0] = valueList[2];
        byteList[1] = valueList[3];
        byteList[2] = valueList[4];
        byteList[3] = valueList[5];

        int strLen = ByteUtil.getInt(byteList);


        byteList[0] = valueList[6];
        byteList[1] = valueList[7];
        byteList[2] = valueList[8];
        byteList[3] = valueList[9];
        int floatLen = ByteUtil.getInt(byteList);



            try{
                byteList = new byte[strLen];

                for (int i = 10; i < strLen + 10; i++) {

                    byteList[i - 10] = valueList[i];

                }


            }catch (Exception e)
            {

                System.out.println("数据异常："+strLen+"<>"+valueList.length);
            }




            try{

                model.strData = new String(byteList);
                byteList = new byte[floatLen];
                for (int i = strLen + 10; i < strLen + 10 + floatLen; i++) {

                    byteList[i - strLen - 10] = valueList[i];



                }

                model.floatList = byte2Float(byteList);
            }catch (Exception e)
            {
                System.out.println("float数据转化异常！");

            }



        model.data = valueList;
        return model;


    }

//  public static byte[] sendByteList(byte mainCode,byte subCode,byte[] strData,byte[] floatData)
//  {

//      int strLen = strData.Length;
//      int floatLen = floatData.Length;

//      List<byte> byteArray = new List<byte>();


//      byteArray.Add(mainCode);
//      byteArray.Add(subCode);

//      Debug.Log(byteArray.Count);
//      byte[] strByteArray = int2Byte(strLen);
//      foreach (byte b in strByteArray)
//      {
//          byteArray.Add(b);
//      }
//      Debug.Log(byteArray.Count);
//      byte[] floatByteArray = int2Byte(floatLen);
//      foreach (byte b in floatByteArray)
//      {
//          byteArray.Add(b);
//      }
//      Debug.Log(byteArray.Count);

//      foreach (byte b in strData)
//      {
//          byteArray.Add(b);
//      }

//      Debug.Log(byteArray.Count);
//      foreach (byte b in floatData)
//      {
//          byteArray.Add(b);
//      }
//      Debug.Log(byteArray.Count);

//      return byteArray.ToArray();
//  }


}
