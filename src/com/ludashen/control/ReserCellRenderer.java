package com.ludashen.control;

/**
 * @description:  JList图片+文字的列表  参考代码 https://blog.csdn.net/weixin_33779515/article/details/93600253
 * @author: 陆均琪
 * @Data: 2019-12-03 8:39
 */

import com.ludashen.hothl.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReserCellRenderer extends JLabel implements ListCellRenderer {
    private Reservation reservation;
    private Color foreground;
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
        //把数据转换为reservation.getHouse()对象， 在AbstractListModel中传过来的是一个user对象

        reservation = (Reservation) value;
        String text="<html><h3>"+reservation.getHouse().gethName()+"("+reservation.getUsers().getuName()+")</h3><font color=\"red\">订房时间："+reservation.getsDate()+"<br>订房日期："+reservation.getdDate()+"</font><html/>";
        setText(text);
        Image img=new ImageIcon("image\\room\\"+reservation.getHouse().gethImg()).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(img));
        setIconTextGap(10);
        foreground = Color.BLACK;
        if(isSelected){
            foreground = new Color(199, 237, 204);
        }

        setForeground(foreground);


        return this;
    }

    //绘制内容
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0xAAF0F564, true)); //绘制可以绘制图片
        g.setColor(new Color(0xC0BB4A31, true));
        g.fill3DRect(0,0,getSize().width - 1, getSize().height - 1,true);
        super.paintComponent(g);
    }

}
