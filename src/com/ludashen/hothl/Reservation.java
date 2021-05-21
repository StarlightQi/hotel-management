package com.ludashen.hothl;

import com.ludashen.control.Tool;

import java.util.Date;
import java.util.TimeZone;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-06 8:40
 */
public class Reservation {
    private House house;
    private Users users;
    private Date sDate;
    private Date dDate;


    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getsDate() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");

        TimeZone.setDefault(timeZone);

        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }

    public Reservation(House house, Users users, Date sDate, Date dDate) {
        this.house = house;
        this.users = users;
        this.sDate = sDate;
        this.dDate = dDate;
    }

    @Override
    public String toString() {
        StringBuffer str=new StringBuffer();
        str.append("<html>");
        str.append("<h2>"+house.gethName()+"</h2>");
        str.append("<span color=\"red\">"+house.gethPrice()+"￥</span>");
        str.append("<p>详情："+house.gethDetails()+"</p><hr><font color='#FF9933'>");
        str.append(house.gethName()+"：<br>预定的客户账号:"+users.getuId()+"<br>其名字为："+users.getuName());
        str.append("<br>手机号码是："+users.getuPhone()+"<br>性别是："+ Tool.toSex(users.getUsex()));
        str.append("<br>订房时间为："+getsDate());
        str.append("<br>退房日期为："+getsDate());
        str.append("</font></html>");

        return str.toString();
    }
}
