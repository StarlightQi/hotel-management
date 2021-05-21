package com.ludashen.hothl;

/**
 * @description: 管理员实体类
 * id 管理员id 对应数据库 id
 * name 管理员名字   name
 * passWord 管理员密码   password
 * remarks 管理员备注    remarks
 * power 管理员权限  power
 * @author: 陆均琪
 * @Data: 2019-12-02 21:21
 */
public class Admin {
    private int id;
    private String name;
    private String passWord;
    private String remarks;
    private int power;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Admin(int id, String name, String passWord, String remarks, int power) {
        this.id = id;
        this.name = name;
        this.passWord = passWord;
        this.remarks = remarks;
        this.power = power;
    }
}
