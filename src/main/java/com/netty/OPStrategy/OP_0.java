package com.netty.OPStrategy;

import com.netty.DB.UserInfoDBOP;
import com.netty.Model.LoginModel;
import com.netty.Model.UserModel;
import com.netty.common.Vector3;
import com.netty.role.Role;
import com.netty.role.RoleMap;
import com.netty.server.DataModel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jdk.nashorn.internal.runtime.Debug;
import net.sf.json.JSONObject;

public class OP_0  extends SLogStrategy{

    @Override
    public void subOP(int _subCode) {
        switch (_subCode){

            case 0:
                LoginModel model = (LoginModel) JSONObject.toBean(data.originData,LoginModel.class);

                System.out.println(model.user_acc+"<>"+model.user_pwd);

                break;

            case 2:

                break;
            case 1:

                break;

            case 5:

                System.out.println("断开连接！");
                break;
            case 123:
//                System.out.println(data.strContent);

                break;
        }
    }
}
