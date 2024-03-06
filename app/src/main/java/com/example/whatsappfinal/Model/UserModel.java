package com.example.whatsappfinal.Model;

public class UserModel {
    private String name,msg,pic,userUid,number;
    private String email,password;

    public UserModel(){}

    public UserModel(String name, String msg, String pic, String userUid, String number) {
        this.name = name;
        this.msg = msg;
        this.pic = pic;
        this.userUid = userUid;
        this.number = number;
    }

    public UserModel(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public UserModel(String name, String pic, String userUid, String number) {
        this.name = name;
        this.pic = pic;
        this.userUid = userUid;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
