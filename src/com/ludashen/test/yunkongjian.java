package com.ludashen.test;

import com.ludashen.control.RPanel;
import com.ludashen.control.Tool;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class yunkongjian extends JPasswordField {
    private Image image; // 定义图像对象
    public yunkongjian() {
        super();
        Dimension size = getPreferredSize();
        size.width =100;
        size.height =50;
        setPreferredSize(size);
        setBorder(null);// 取消边框
        setOpaque(false);// 设置控件透明
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        g2.setColor(new Color(0x5B7C70FF, true));
        g2.fillRoundRect(0,0,w-1,h-1,20,20);
    }


    public static void main(String[] args) {
        JTextField button = new yunkongjian();
        JFrame frame = new JFrame("圆形按钮");
        frame.getContentPane().setLayout(new FlowLayout());
        JPanel p=new JPanel();
        p.add(button);
//        Tool.circleButton(1,1,100,"../resource/Lmain.png",p);
        frame.getContentPane().add(p);
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
