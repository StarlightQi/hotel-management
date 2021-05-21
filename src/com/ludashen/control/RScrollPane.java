package com.ludashen.control;


import javax.swing.*;
import java.awt.*;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-09 0:13
 */
public class RScrollPane extends JScrollPane {
    private String path;
    public RScrollPane(Component comp,String path) {
        super(comp);
        getViewport().setOpaque(false);
        this.path=path;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        Image image;
        if(path.equals(""))
            image = new ImageIcon(RPanel.class.getResource("/resource/admin/admin.png")).getImage();
        else
            image = new ImageIcon(RPanel.class.getResource(path)).getImage();

        int width = getWidth(); // 获得面板的宽度
        int height = getHeight(); // 获得面板的高度
        g.drawImage(image, 0, 0, width, height, this);
    }
}
