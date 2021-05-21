package com.ludashen.panel;

import com.ludashen.control.*;
import com.ludashen.dao.HouseDao;
import com.ludashen.dao.RoomInfoDao;
import com.ludashen.hothl.House;

import com.ludashen.hothl.RoomInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-07 15:48
 */
public class MoreRoomPanel extends RPanel {
    private JTable rTable;  //客房信息表
    private int rid;    //记录列表选择的客房id  ，用与数据更新
    private int rids;   //记录表格选择的客房信息的id  ，用于数据更新
    private String[] rhears={"id","名字","作用"};   //客房信息表
    private java.util.List<House> rHouse;//存储客房更多信息的
    private int tChoose=0;//获取表格当前列--客户管理
    private JLabel image;
    private JList  buddyList;
    private  JTextField rName;
    private JTextField rFuntion;
    private  JTextField browse;
    private RButton modify;
    private RButton addRoomInfo;
    private RButton rDelete;
    private  RButton refresh;

    public MoreRoomPanel(){
        super("/resource/admin/admin.png");
        this.setLayout(null);
        rTable=Tool.jTable(300,0,450,300,this);
        image= Tool.jLabel(380,250,"",this,300,300);
        buddyList = Tool.jList(0,0,280,600,this);
        buddyList.setCellRenderer(new FriListCellRenderer());
        reRoom(buddyList);

        Tool.jLabel(350,550,"名字",this);
        Tool.jLabel(350+150,550,"作用",this);
        Tool.jLabel(350+150*2,550,"图片",this);

        rName=Tool.jTextField(320,580, this);
        rFuntion=Tool.jTextField(320+150, 580, this);
        browse= Tool.chooserFile(320+150*2,580,this);
        modify=Tool.rButton(320,630,"修改",this);
        addRoomInfo=Tool.rButton(320+150,630,"添加",this);
        rDelete=Tool.rButton(320+150*2,630,"删除",this);
        refresh=Tool.rButton(0,620,"刷新",this,300,40);
        monitor();
    }

    private void monitor(){
        if(rHouse.size()!=0){
            rid=rHouse.get(0).getHid();
            rReTable(rid);
            image.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+rHouse.get(0).gethImg()).getImage().getScaledInstance(280, 170,Image.SCALE_DEFAULT )));
        }else {
            //没有房间
        }

        buddyList.addListSelectionListener(e -> {
            try {
                if(buddyList.getValueIsAdjusting());
                rid=rHouse.get(buddyList.getSelectedIndex()).getHid();
                image.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+rHouse.get(buddyList.getSelectedIndex()).gethImg()).getImage().getScaledInstance(280, 170,Image.SCALE_DEFAULT )));
                rReTable(rid);
            }catch (Exception ex){
                buddyList.clearSelection();
            }
        });

        rTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name=rTable.getValueAt(rTable.getSelectedRow(),1).toString();
                String funtion=rTable.getValueAt(rTable.getSelectedRow(),2).toString();
                tChoose=rTable.getSelectedRow();
                rids=new Integer(rTable.getValueAt(rTable.getSelectedRow(),0).toString());
                rName.setText(name);
                rFuntion.setText(funtion);
                if(name.equals("image")) {
                    image.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\" + funtion).getImage().getScaledInstance(280, 170, Image.SCALE_DEFAULT)));
                    rName.setEnabled(false);
                    rFuntion.setEnabled(false);
                }
                else {
                    rName.setEnabled(true);
                    rFuntion.setEnabled(true);
                }
            }
        });

        browse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                image.setIcon(new ImageIcon((Image) new ImageIcon(((FileChooser) browse).getPath()).getImage().getScaledInstance(280, 170,Image.SCALE_DEFAULT )));
                rName.setEnabled(false);
                rFuntion.setEnabled(false);
                rName.setText("image");
                rFuntion.setText("添加完图片后即可恢复");
                modify.setEnabled(false);
            }
        });

        modify.addActionListener((e)->{
            RoomInfo info=new RoomInfo(rids,rName.getText(),rFuntion.getText());
            if(RoomInfoDao.udRoomInfo(info)){
                JOptionPane.showMessageDialog(null,"修改成功");
                rReTable(rid);
            }
        });

        addRoomInfo.addActionListener((e)->{
            RoomInfo info;
            if(browse.getText().equals("点击浏览"))
                info=new RoomInfo(rid,rName.getText(),rFuntion.getText());
            else
                info = new RoomInfo(rid, rName.getText(), browse.getText());
            if(!rName.getText().equals("")) {
                try {
                    if (RoomInfoDao.addRoomInfo(info)) {
                        FileChooser.copy("image\\room",browse.getText());
                        JOptionPane.showMessageDialog(null, "添加成功");
                    }
                    modify.setEnabled(true);
                    browse.setText("点击浏览");
                    rName.setEnabled(true);
                    rFuntion.setEnabled(true);
                    rName.setText("");
                    rFuntion.setText("");
                    rReTable(rid);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"添加失败");
                }
            }else
                JOptionPane.showMessageDialog(null,"名字不能为空");
        });

        rDelete.addActionListener((e)->{
            if(RoomInfoDao.rDelete(rids)){
                JOptionPane.showMessageDialog(null,"删除成功");
                rReTable(rid);
            }
        });

        refresh.addActionListener(e -> {
            reRoom(buddyList);
        });
    }

    private void reRoom(JList list){
        /**
         * @description: 客房更多信息列表刷新
         * @param list  列表控件
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-05 18:02
         */
        rHouse= HouseDao.getHouser(1);
        HouseModel buddy = new HouseModel(rHouse);
        list.setModel(buddy);
        list.setPreferredSize(new Dimension(300, 72*rHouse.size()));//这里每次刷新时都要更新一下高

    }
    private void rReTable(int id){
        /**
         * @description: 客房信息管理表格刷新
         * @param id    客房id根据选中列表中的客房查看相关的客房信息
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 23:49
         */

        int i=0;
        List<RoomInfo> roomInfo= RoomInfoDao.getRoomInfo(id,2);
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
        rTable.setModel(model);
    }
}
