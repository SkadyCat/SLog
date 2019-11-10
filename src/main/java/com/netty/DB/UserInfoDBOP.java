package com.netty.DB;

import com.netty.Model.UserModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDBOP extends DataBaseOP {




    public static List<UserModel> getUserInfoModelList(String userID) throws Exception {
        List<UserModel> list = new ArrayList<UserModel>();

        JSONArray array = request(dbName,sql_user_info_table);
        for(int i=0;i<array.size();i++){
            // 遍历 jsonarray 数组，把每一个对象转成 json 对象
            JSONObject job = array.getJSONObject(i);
            UserModel model = (UserModel) JSONObject.toBean(job,UserModel.class);
            list.add(model);
        }



        return list;

    }
    public static UserModel getUserInfoModelListSingle(String userID) throws Exception {
       JSONObject model =requestSingle(dbName,sql_user_info_where_user_acc+"'"+userID+"'");
       UserModel md = (UserModel) JSONObject.toBean(model,UserModel.class);

        return md;

    }
}
