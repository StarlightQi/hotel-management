package com.ludashen.control;

import javax.swing.*;
import java.awt.*;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-08 11:18
 */
public class RTextField extends JTextField {
    public RTextField() {
        super();
        setBorder(null);// 取消边框
        setOpaque(false);// 设置控件透明
        setFont(new Font("",1,20));
        setForeground(Color.ORANGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        g.setColor(new Color(0x65FFF3FC, true));
        g.fillRoundRect(0,0,w-1,h-1,20,20);
    }
}
