package com.netty.DB;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.*;

public class DataBaseOP {


    public static final String dbName = "TaskSystem";
    public static final String sql_user_info_table = "select * from user_info";

    public static final String sql_user_info_where_user_acc = "select * from user_info where user_acc = ";
//
    //
    public static JSONArray request(String dataBase, String sqlValue) throws Exception {


        String URL="jdbc:sqlserver://47.106.227.238:1433;databaseName="+dataBase+"";
        String USER="sa";
        String PASSWORD="ljy2018.";
        //1.加载驱动程序
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //2.获得数据库链接
        Connection conn= DriverManager.getConnection(URL, USER, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(sqlValue);
        //4.处理数据库的返回结果(使用ResultSet类)

        JSONArray al = formatRsToJsonArray(rs);
        //关闭资源
        rs.close();
        st.close();
        conn.close();

        return al;
    }

    public static JSONObject requestSingle(String dataBase, String sqlValue) throws Exception {


        JSONArray array = request(dataBase,sqlValue);

        JSONObject obj = (JSONObject) array.get(0);


        return obj;
    }


    public static JSONArray formatRsToJsonArray(ResultSet rs) throws Exception{
        ResultSetMetaData md = rs.getMetaData();//获取列数据

        JSONArray jsonArray = new JSONArray();//存放返回的jsonOjbect数组
        while(rs.next()) {
            JSONObject jsonObject = new JSONObject();//将每一个结果集放入到jsonObject对象中
            for(int i=1;i<=md.getColumnCount();i++) {
                jsonObject.put(md.getColumnName(i), rs.getObject(i));//列值一一对应
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
