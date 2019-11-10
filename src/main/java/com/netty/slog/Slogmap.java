package com.netty.slog;

import com.netty.role.Role;
import com.netty.server.DataModel;
import com.netty.server.NettyHandler;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashMap;

public class Slogmap {

    public static HashMap<String, Role> playerMap = new HashMap<>();

    public DataModel collectAllPosition(){

        String roleList  = "";
        for (String key:playerMap.keySet()
             ) {
            roleList+= key+"#";
        }

        return  null;


    }

}
