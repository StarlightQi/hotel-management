package com.ludashen.hothl;

import java.util.Date;

/**
 * @description:  用户信息实体类
 * uId  用户id    uid
 * uName 用户名字   uName
 * uPassword 用户密码   uPassword
 * uPhone 用户电话  uPhone
 * uBirthday 用户生日   uBirthday
 * Usex 用户性别   uSex
 * @author: 陆均琪
 * @Data: 2019-12-02 21:21
 */
public class Users {
    private String uId;
    private String uName;
    private String uPassword;
    private String uPhone;
    private Date uBirthday;
    private Boolean uSex;
    private String head;
    private int money;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public Date getuBirthday() {
        return uBirthday;
    }

    public void setuBirthday(Date uBirthday) {
        this.uBirthday = uBirthday;
    }

    public Boolean getUsex() {
        return uSex;
    }

    public void setUsex(Boolean uSex) {
        uSex = uSex;
    }

    public Users(String uid, String uName,String uPassword, String uPhone, Date uBirthday, Boolean usex,String head,int money) {
        this.uId = uid;
        this.uName=uName;
        this.uPassword = uPassword;
        this.uPhone = uPhone;
        this.uBirthday = uBirthday;
        uSex = usex;
        this.head = head;
        this.money = money;
    }

    public Users(){
    }


    @Override
    public String toString() {
        return "Users{" +
                "uName='" + uName + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uBirthday=" + uBirthday +
                ", Usex=" + uSex +
                '}';
    }
}
