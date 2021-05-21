package com.ludashen.control;

/**
 * @description: 重新绘制面板容器背景
 * @author: 陆均琪
 * @Data: 2019-11-25 18:04
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.*;

public class RPanel extends JPanel {
    private static final long serialVersionUID = 1L;// 序列化编号
    private Image image; // 定义图像对象
    public RPanel(String path) {
        /**
         * @description: 面板构造方法
             * @param path  -背景图片对象地址
         * @return:
         * @author: 陆均琪
         * @time: 2019-11-26 9:38
         */
        super();
        image = new ImageIcon(RPanel.class.getResource(path)).getImage();
        initialize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        /**
         * @description: 重写绘制组件方法，做到框架改变大小图片大小也随之改变
             * @param g 绘制方法
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-26 9:40
         */
        super.paintComponent(g); // 调用父类的方法
        Graphics2D g2 = (Graphics2D) g; // 创建Graphics2D对象
        if (image != null) {
            int width = getWidth(); // 获得面板的宽度
            int height = getHeight(); // 获得面板的高度
            // 绘制图像
            g2.drawImage(image, 0, 0, width, height, this);
        }
    }

    /**
     * 初始化面板大小
     */
    private void initialize() {
        /**
         * @description: 设置初始化函数
             * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-26 9:40
         */
        this.setSize(300, 200);
    }
}
