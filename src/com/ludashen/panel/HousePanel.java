package com.ludashen.panel;

import com.ludashen.control.FileChooser;
import com.ludashen.control.RButton;
import com.ludashen.control.RPanel;
import com.ludashen.control.Tool;
import com.ludashen.dao.CommentDao;
import com.ludashen.dao.HouseDao;
import com.ludashen.hothl.Comment;
import com.ludashen.hothl.House;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-07 15:27
 */
public class HousePanel extends RPanel {
    private  JTable table;  //客房表格
    private int sqx;    //记录当前选中数据库的，用于刷新同一个表格(在用户数据库用有个users方法)
    private String hid; //记录当前的客房id，用在更改数据时改对数据
    private String[] heads={"id","名字","详情","价格","图片"};  //客房表格
    private String[] chead={"id","用户","房子","评论","时间"};  //客房表格
    private JTable table2;
    private int hChoose=0;//获取房子表格当前列
    private JTextField tFind;
    private JTextField tName;
    private JTextField tDetail;
    private JTextField tPrice;
    private JTextField browse;
    private RButton allFind;
    private RButton refresh;
    private RButton cRefresh;
    private RButton modify;
    private RButton addHouse;
    private RButton hDelete;
    private RButton clear;
    private RButton cDelete;
    private JLabel imgage;
    private JComboBox selectHouse=new JComboBox();
    private int cChoose;//保存选中评论的id


    public HousePanel(){
        super("/resource/admin/admin.png");
        sqx=1;
        setLayout(null);
        selectHouse.addItem("查看全部");
        selectHouse.addItem("查看空房");
        selectHouse.addItem("查看预约");
        selectHouse.setBounds(0,0,150,30);

        table=Tool.jTable(0,32,500,300,this);
        reTable(HouseDao.getHouser(sqx));//刷新表格
        table2=Tool.jTable(0,340,500,150,this);
        Tool.comment(table2,CommentDao.getComment());


        Tool.jLabel(65,510,"房间名称",this);
        Tool.jLabel(65+180,510,"房间详情",this);
        Tool.jLabel(65+180*2,510,"房间价格",this);
        Tool.jLabel(65+180*3,510,"房间图片",this);

        tFind= Tool.jTextField(200,0,this,150);

        tName=Tool.jTextField(50, 550, this);
        tDetail=Tool.jTextField(50+180, 550, this);
        tPrice=Tool.jTextField(50+180*2, 550, this);
        browse= Tool.chooserFile(50+180*3,550,this);
        //按钮控件
        allFind=Tool.rButton(370,0,"查找房子",this,110);
        cDelete=Tool.rButton(510,400,"删除评论",this,110);
        cRefresh=Tool.rButton(510,460,"刷新评论",this,110);
        refresh=Tool.rButton(50,630,"刷新",this,80);
        modify=Tool.rButton(50+120,630,"修改",this,80);
        addHouse=Tool.rButton(50+120*2,630,"添加",this,80);
        hDelete=Tool.rButton(50+120*3,630,"删除",this,80);
        clear=Tool.rButton(50+120*4,630,"清空",this,80);
        imgage=Tool.jLabel(510,30,"",this,300,300);
        Monitor();
        this.add(selectHouse);

    }

    //各种控件添加的事件监控
    private void Monitor(){

        selectHouse.addActionListener((e)->{
            int sh=selectHouse.getSelectedIndex();
            sqx=sh+1;
            reTable(HouseDao.getHouser(sqx));
        });

        browse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                imgage.setIcon(new ImageIcon((Image) new ImageIcon(((FileChooser) browse).getPath()).getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT )));
                modify.setEnabled(false);

            }
        });

        refresh.addActionListener((e)->{
            reTable(HouseDao.getHouser(sqx));
        });

        modify.addActionListener((e)->{
            String name = tName.getText();
            String detail = tDetail.getText();
            int price = new Integer(tPrice.getText());
            String img = browse.getText();
            House house=new House(new Integer(hid),name,detail,img,price);
            if(HouseDao.modify(house)){
                JOptionPane.showMessageDialog(null,"修改成功");
                reTable(HouseDao.getHouser(sqx));
            }});

        addHouse.addActionListener((e)->{
            String name = tName.getText();
            String detail = tDetail.getText();
            int price = new Integer(tPrice.getText());
            String img = browse.getText();
            House house=new House(name,detail,img,price);

            try {
                if (HouseDao.addHouse(house)){
                    FileChooser.copy("image\\room",browse.getText());
                    JOptionPane.showMessageDialog(null,"添加成功");
                    modify.setEnabled(true);
                    reTable(HouseDao.getHouser(sqx));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"添加失败");
            }

        });

        hDelete.addActionListener((e)->{
            try {
                if (HouseDao.delect(tName.getText())) {
                    JOptionPane.showMessageDialog(null, "删除成功");
                    String headImg = table.getValueAt(hChoose, 3).toString();
                    File file = new File("image\\room\\" + headImg);
                    file.delete();
                    reTable(HouseDao.getHouser(sqx));
                }
            }catch (Exception es){
                JOptionPane.showMessageDialog(null,"该房子不能删除，情先删除相关信息");
            }
        });
        allFind.addActionListener(e -> {
            List<House> house = HouseDao.findHouse(tFind.getText());
            if(house.size()==0)
                JOptionPane.showMessageDialog(null,"没有找到任何东西");
            reTable(house);
        });

        //获取选中表格的值输入到文本框中90
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Tool.comment(table2,CommentDao.getRoomComment(table.getValueAt(table.getSelectedRow(),0).toString(),1));
                tName.setText(table.getValueAt(table.getSelectedRow(),1).toString());
                tDetail.setText(table.getValueAt(table.getSelectedRow(),2).toString());
                tPrice.setText(table.getValueAt(table.getSelectedRow(),3).toString());
                browse.setText(table.getValueAt(table.getSelectedRow(),4).toString());
                hid=table.getValueAt(table.getSelectedRow(),0).toString();
                imgage.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+table.getValueAt(table.getSelectedRow(),4).toString()).getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT )));
            }
        });
        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cChoose=table2.getSelectedRow();
            }
        });

        clear.addActionListener(e -> {
            tName.setText("");
            tDetail.setText("");
            tPrice.setText("");
            browse.setText("点击浏览");
        });

        cDelete.addActionListener(e->{
            String commId = table2.getValueAt(cChoose, 0).toString();
            String hid = table2.getValueAt(cChoose, 2).toString();
            if(CommentDao.cDelete(commId)){
                JOptionPane.showMessageDialog(null,"删除成功");
                Tool.comment(table2,CommentDao.getRoomComment(hid,1));
            }

        });

        cRefresh.addActionListener(e->{
           Tool.comment(table2,CommentDao.getComment());
        });
    }



    private void reTable(List<House> houser){
        /**
         * @description: 客房表格刷新
         * @param sq  1 查看全部客房 2查看空的客房 3查看有客户预定的客房
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 23:46
         */
        int i=0;
//        List<House> houser = HouseDao.getHouser(sq);
        Object[] [] resu=new Object[houser.size()][5];//*x行 4列
        i=0;
        for (House house:houser){
            resu[i][0]=house.getHid();
            resu[i][1]=house.gethName();
            resu[i][2]=house.gethDetails();
            resu[i][3]=house.gethPrice();
            resu[i][4]=house.gethImg();
            i++;
        }
        DefaultTableModel model=new DefaultTableModel();
        model.setDataVector(resu,heads);
        table.setModel(model);

    }


}
