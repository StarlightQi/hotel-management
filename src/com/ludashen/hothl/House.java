package com.ludashen.hothl;

import com.ludashen.control.Tool;

/**
 * @description:房子实体类，对应多表查询
 * hid 客房id 对应数据库   hid
 * hName 客房名字   hname
 * hDetails 客房详情    hdetails
 * hImg 客房图片    himg
 * hPrice 客房价格  hprice
 * users 客房预订的用户   uid
 * @author: 陆均琪
 * @Data: 2019-12-02 21:22
 */
public class House {
    private int hid;
    private String hName;
    private String hDetails;
    private String hImg;
    private int hPrice;


    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }


    public int gethPrice() {
        return hPrice;
    }

    public void sethPrice(int hPrice) {
        this.hPrice = hPrice;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public String gethDetails() {
        return hDetails;
    }

    public void sethDetails(String hDetails) {
        this.hDetails = hDetails;
    }

    public String gethImg() {
        return hImg;
    }

    public void sethImg(String hImg) {
        this.hImg = hImg;
    }

    public House(String hName, String hDetails, String hImg, int hPrice) {
        this.hName = hName;
        this.hDetails = hDetails;
        this.hImg = hImg;
        this.hPrice =hPrice;
    }
    public House(int hid,String hName, String hDetails, String hImg, int hPrice) {
        this.hid=hid;
        this.hName = hName;
        this.hDetails = hDetails;
        this.hImg = hImg;
        this.hPrice =hPrice;
    }



    @Override
    public String toString() {
        StringBuffer str=new StringBuffer();
        str.append("<html>");
        str.append("<h2>"+hName+"</h2>");
        str.append("<span color=\"red\">"+hPrice+"￥</span>");
        str.append("<p>详情："+hDetails+"</p><hr>");

        return str.toString();
    }
}
