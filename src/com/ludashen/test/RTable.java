package com.ludashen.test;

import com.ludashen.control.RPanel;
import com.ludashen.dao.RoomInfoDao;
import com.ludashen.hothl.RoomInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-08 21:21
 */
public class RTable extends JTable {

        public RTable() {
            super();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }

        /**
         * 以不同的颜色对交替的行进行着色。这是是上色的
         */
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (isCellSelected(row, column) == false)
                c.setBackground((row % 2 == 0) ? new Color(0xC7EDCC) : new Color(0xA3FFCC99, true));
            return c;
        }

    public static void main(String[] args) {
        JFrame f=new JFrame();
        RTable rTabel = new RTable();
        String[] rhears={"id","名字","作用"};   //客房信息表
        int i=0;
        List<RoomInfo> roomInfo= RoomInfoDao.getRoomInfo(20,1);
        Object[] [] resu=new Object[roomInfo.size()][3];//*x行 4列
        i=0;
        for (RoomInfo info :roomInfo){
            resu[i][0]=info.getId();
            resu[i][1]=info.getName();
            resu[i][2]=info.getFuntion();
            i++;
        }
        DefaultTableModel model=new DefaultTableModel();
        model.setDataVector(resu,rhears);
        rTabel.setModel(model);
        RScrollPane jScrollPane=new RScrollPane(rTabel);
//        jScrollPane.setOpaque(false);
        Thread panelThread = new Thread(jScrollPane);
        panelThread.start();
        jScrollPane.setBounds(0,0,300,300);
        RPanel rPanel = new RPanel("../resource/admin/admin.png");
        rPanel.setLayout(null);
        rPanel.add(jScrollPane);

        f.add(rPanel);
        f.setSize(500,500);
        f.setVisible(true);
        f.setDefaultCloseOperation(3);
    }
}

class RScrollPane extends JScrollPane implements Runnable{
    public RScrollPane(RTable rTabel) {
        super(rTabel);
        getViewport().setOpaque(false);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        Image image = new ImageIcon(RPanel.class.getResource("../resource/admin/admin.png")).getImage();

        int width = getWidth(); // 获得面板的宽度
        int height = getHeight(); // 获得面板的高度
        // 绘制图像
        g.drawImage(image, 0, 0, width, height, this);
        g.drawRect(x, y, 20, 20);
    }
    int x = 0,y = 100;
    @Override
    public void run() {
        while(true){
            if(x>800)
                x = 0;
            else
                x = x + 10;
            this.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        super.paintComponent(g); // 调用父类的方法
//        Image image = new ImageIcon(RPanel.class.getResource("../resource/admin/admin.png")).getImage();
//
//        int width = getWidth(); // 获得面板的宽度
//        int height = getHeight(); // 获得面板的高度
//        // 绘制图像
//        g.drawImage(image, 0, 0, width, height, this);
//    }


//    @Override
//    protected void paintChildren(Graphics g) {
//        super.paintChildren(g);
//
//    }


}
