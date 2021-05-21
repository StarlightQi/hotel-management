package com.ludashen.panel;

import com.ludashen.dao.ReservationDao;
import com.ludashen.dao.UserDao;

import javax.swing.*;
import java.awt.*;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-07 22:25
 */
public class StatisticsPanel extends JPanel implements Runnable {

    int u = 0,d = 0;
//    int user = UserDao.count();
    int user = 100;
//    int ding = ReservationDao.count();
    int ding = 200;
    public StatisticsPanel() {
        super();
        this.setSize(250, 300);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int w = getWidth();
        int h = getHeight();

        g.setColor(Color.BLACK);
        //画坐标轴
        g.drawLine(10, 10, 10, h - 30);
        g.drawLine(10, h - 30, w - 20, h - 30);

        g.setColor(Color.ORANGE);
        g.fillRect(20, h - 30 - u, 30, u);
        g.drawString(u + "人", 20, h - 30 - u - 10);
        g.drawString("用户量", 15, h - 5);

        g.setColor(Color.GREEN);
        g.fillRect(20 + 100, h - 30 - d, 30, d);
        g.drawString(d + "份", 20 + 100, h - 30 - d - 10);
        g.drawString("订单量", 15 + 100, h - 5);

    }

    @Override
    public void run() {
        for (int i=0;i<=user;i++){
            u=i;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }

        for(int i=0;i<=ding;i++){
            d=i;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }



    public static void main(String[] args) {
        JFrame f=new JFrame();
        f.setSize(500,500);
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
        StatisticsPanel statisticsPanel = new StatisticsPanel();
        f.add(statisticsPanel);
        Thread panelThread = new Thread(statisticsPanel);
        panelThread.start();
    }

}


