package com.ludashen.control;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;


/**
 * @description:绘制圆形按钮
 * @author: 陆均琪
 * @Data: 2019-12-07 0:28
 */
public class CircleButton extends JButton {
    private Image image; // 定义图像对象
    public CircleButton(int sz,String path) {
        super();
        Dimension size = getPreferredSize();
        size.width = size.height =sz;
        setPreferredSize(size);
        image = new ImageIcon("image\\head\\"+path).getImage();
        setContentAreaFilled(false);
        this.setBorderPainted(false); // 不绘制边框
        this.setFocusPainted(false); // 不绘制焦点状态
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillOval(0, 0, getSize().width+1 , getSize().height +1);
        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, width, height);
            g2.setClip(shape);
            g2.drawImage(image, 0, 0, width, height, this);
            g2.dispose();
        }

    }
}
