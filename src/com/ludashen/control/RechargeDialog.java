package com.ludashen.control;

import com.ludashen.dao.UserDao;

import javax.swing.*;
import java.awt.*;

/**
 * @description:模拟支付界面
 * @author: 陆均琪
 * @Data: 2019-12-07 20:24
 */
public class RechargeDialog extends JDialog {
    private String uid;
    private float money;
    private JLabel label=new JLabel();
    private JPanel p;
    public RechargeDialog(String uid,float money)  {
        setResizable(false);
        setTitle("支付");
        this.uid=uid;
        this.money=money;
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/log.png")));
        init();
    }
    private void init() {
        p=new JPanel();
        p.add(label);
        new Thread(() -> {
            //创立一个线程图片播放
            try {
                for (int i = 1; i < 4; i++) {
                label.setIcon(new ImageIcon((Image) new ImageIcon(getClass().getResource("/resource/pay/pay"+i+".png")).getImage().getScaledInstance(300, 300,Image.SCALE_DEFAULT )));
                Thread.sleep(3000);
                if(i==3){
                    UserDao.setMoney(uid,money);
                    Thread.sleep(2000);
                    dispose();
                }
                }
            }catch (Exception e){
            }
        }).start();
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.add(label);
    }
}
