package com.ludashen.hothl;

/**
 * @description: 客房信息实体类
 * id 信息id  对应数据库   id
 * name 信息名字    name
 * funtion 作用   funtion
 * house 对应的房子  hnames=》hid
 * @author: 陆均琪
 * @Data: 2019-12-04 15:20
 */
public class RoomInfo {
    private int id;
    private String name;
    private String funtion;
    private House house;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFuntion() {
        return funtion;
    }

    public void setFuntion(String funtion) {
        this.funtion = funtion;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public RoomInfo(int id,String name, String funtion) {
        this.id=id;
        this.name = name;
        this.funtion = funtion;
    }
}
