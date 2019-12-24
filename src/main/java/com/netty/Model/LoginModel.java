package com.netty.Model;

public class LoginModel extends  SendData {

    public  String user_acc;
    public  String user_pwd;

    public String getUser_acc() {
        return user_acc;
    }

    public void setUser_acc(String user_acc) {
        this.user_acc = user_acc;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }
}
