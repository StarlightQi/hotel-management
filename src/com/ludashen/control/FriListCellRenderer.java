package com.ludashen.control;

/**
 * @description:  JList图片+文字的列表  参考代码 https://blog.csdn.net/weixin_33779515/article/details/93600253
 * @author: 陆均琪
 * @Data: 2019-12-03 8:39
 */

import com.ludashen.hothl.House;
import javax.swing.*;
import java.awt.*;

public class FriListCellRenderer extends JLabel implements ListCellRenderer {
    private House house;
    int x = 0, y = 0;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        /**
         * @description:
         * @param list   Jlist控件
         * @param value  List控件的值
         * @param index  第几个
         * @param isSelected 选中
         * @param cellHasFocus
         * setIconTextGap(10); 设置JLable的图片与文字之间的距离
         * @return: java.awt.Component
         * @author: 陆均琪
         * @time: 2019-12-04 21:41
         */
        //把数据转换为House对象， 在AbstractListModel中传过来的是一个user对象
        house = (House) value;
        String text = "<html><h3>" + house.gethName() + "</h3><br/> <font color=\"red\">" + house.gethPrice() + "￥</font>" + house.gethDetails() + "<html/>";
        setText(text);
        Image img = new ImageIcon("image\\room\\" + house.gethImg()).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(img));
        setIconTextGap(10);
        Color foreground = Color.BLACK;
        if (isSelected) {
            foreground = new Color(199, 237, 204);
        }
        setForeground(foreground);
        return this;
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0xAAF0F564, true)); //绘制可以绘制图片
        g.setColor(new Color(0xC0BB4A31, true));
        g.fill3DRect(0, 0, getWidth(), getHeight(), true);
        super.paintComponent(g);
    }

}





