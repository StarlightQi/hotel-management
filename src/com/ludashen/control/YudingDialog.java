package com.ludashen.control;

import com.ludashen.dao.ReservationDao;
import com.ludashen.dao.UserDao;
import com.ludashen.frame.UserFrame;
import com.ludashen.hothl.Users;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-05 23:15
 */
public class YudingDialog extends JDialog {
    private float money;
    private String uid;
    private int hid;
    public YudingDialog(int hid, String uid, float money){
        super(new JFrame());
        this.money=money;
        this.uid=uid;
        this.hid=hid;
        init();
    }

    private void init() {
        setResizable(false);
        this.add(mainPanel());
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/log.png")));
        setTitle("信息确认");
        setSize(300,300);
        setLocationRelativeTo(null);
    }

    private RPanel mainPanel(){

        RPanel p=new RPanel("/resource/bg1.jpg");
        p.setLayout(null);
        Date nowDate=new Date();
        JTextField tDingDate=Tool.calendarChoose(130,20,nowDate,Tool.dateToStr(nowDate),p);

        JLabel date=Tool.jLabel(50,20,"订房日期：",p);
        JLabel tip=Tool.jLabel(40,75,"订房需要交100元的押金",p,200);
        JLabel  balance=Tool.jLabel(40,110,"您当前余额为:"+money+"￥",p,200);
        RButton ding=Tool.rButton(100,200,"订房",p);

        ding.addActionListener(e->{
            long nd = 1000 * 24 * 60 * 60;
            long diff = Tool.strToDate(tDingDate.getText()).getTime() - new Date().getTime();
            long day = diff / nd;
            if(money>=100){
                if(day+1>0&&day+1<3){
                    if(ReservationDao.getUser(uid)) {
                        if (ReservationDao.reserve(hid, uid, tDingDate.getText())) {
                            JOptionPane.showMessageDialog(null, "订房成功");
                            this.dispose();
                        }
                    }else
                        JOptionPane.showMessageDialog(null,"抱歉一个用户暂时只能定一个房间");
                }else
                    JOptionPane.showMessageDialog(null,"订房的时间不能小于今天，也不能大于3天");
            }else
                JOptionPane.showMessageDialog(null,"订房失败，您的余额不足情充值");

        });

        return p;
    }

}
