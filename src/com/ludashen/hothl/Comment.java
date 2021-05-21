package com.ludashen.hothl;

import java.util.Date;

/**
 * @description: 应为时间与能力原因和设计要求，占时不用多表
 * @author: 陆均琪
 * @Data: 2019-12-07 15:09
 */
public class Comment {
    private int id;
    private String uid;
    private String hid;
    private String comment;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment(int id, String uid, String hid, String comment, Date date) {
        this.id = id;
        this.uid = uid;
        this.hid = hid;
        this.comment = comment;
        this.date = date;
    }

    public Comment(String uid, String hid, String comment) {
        this.uid = uid;
        this.hid = hid;
        this.comment = comment;
    }
}
