package com.ludashen.hothl;

import java.util.Date;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-06 17:14
 */
public class History {
    private int id;
    private int hid;
    private String udi;
    private Date dtime;//订单的时间
    private Date ttime;//原本退单的时间
    private Date ctime;//现在操作订单的时间
    private Boolean result;//订单的操作结果，true正常，false 超时
    private String reason;//产生结果的原因
    private int deduct;

    public int getDeduct() {
        return deduct;
    }

    public void setDeduct(int deduct) {
        this.deduct = deduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getUdi() {
        return udi;
    }

    public void setUdi(String udi) {
        this.udi = udi;
    }

    public Date getDtime() {
        return dtime;
    }

    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    public Date getTtime() {
        return ttime;
    }

    public void setTtime(Date ttime) {
        this.ttime = ttime;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public History(int id, int hid, String udi, Date dtime, Date ttime, Date ctime, Boolean result, String reason) {
        this.id = id;
        this.hid = hid;
        this.udi = udi;
        this.dtime = dtime;
        this.ttime = ttime;
        this.ctime = ctime;
        this.result = result;
        this.reason = reason;
    }

    public History(int hid, String udi, Boolean result, String reason, int deduct) {
        this.hid = hid;
        this.udi = udi;
        this.result = result;
        this.reason = reason;
        this.deduct = deduct;
    }

    public History(int id, int hid, String udi, Date dtime, Date ttime, Date ctime, Boolean result, String reason, int deduct) {
        this.id = id;
        this.hid = hid;
        this.udi = udi;
        this.dtime = dtime;
        this.ttime = ttime;
        this.ctime = ctime;
        this.result = result;
        this.reason = reason;
        this.deduct = deduct;
    }
}
