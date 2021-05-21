package com.ludashen.panel;

import com.ludashen.control.RButton;
import com.ludashen.control.RPanel;
import com.ludashen.control.Tool;
import com.ludashen.dao.AdminDao;
import com.ludashen.hothl.Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-07 15:53
 */
public class AdminPanel extends RPanel {
    private JTable aTable;  //管理员表格
    private int aid;    //记录表格管理员的id   ，用于数据更新
    private String[] ahears={"id","名字","密码","备注","权限"}; //管理员表格


    public AdminPanel(){
        /**
         * @description: 管理员管理面板，增添管理员的只有权限为1的管理员可以看到该面板
         * @param
         * @return: javax.swing.JPanel
         * @author: 陆均琪
         * @time: 2019-12-04 23:46
         */
        super("/resource/admin/admin.png");
        setLayout(null);
        aTable=Tool.jTable(0,0,750,450,this);
        aReTable();

        Tool.jLabel(10,510,"管理员账号",this);
        Tool.jLabel(10+200,510,"管理员密码",this);
        Tool.jLabel(10+400,510,"管理员备注",this);
        Tool.jLabel(10+600,510,"管理员权限",this);

        JTextField tName= Tool.jTextField(10, 550, this);
        JTextField tPass=Tool.jTextField(10+200, 550, this);
        JTextField tRemake=Tool.jTextField(10+400, 550, this);
        JTextField tPower=Tool.jTextField(10+600, 550, this);

        RButton modify=Tool.rButton(150,620,"修改",this);
        RButton addAdmin=Tool.rButton(300,620,"添加",this);
        RButton delete=Tool.rButton(450,620,"删除",this);

        aTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                aid=new Integer(aTable.getValueAt(aTable.getSelectedRow(),0).toString());
                tName.setText(aTable.getValueAt(aTable.getSelectedRow(),1).toString());
                tPass.setText(aTable.getValueAt(aTable.getSelectedRow(),2).toString());
                tRemake.setText(aTable.getValueAt(aTable.getSelectedRow(),3).toString());
                tPower.setText(aTable.getValueAt(aTable.getSelectedRow(),4).toString());
            }
        });

        modify.addActionListener((e)->{
            String name = tName.getText();
            String pass = tPass.getText();
            String remake= tRemake.getText();
            int power = new Integer(tPower.getText());
            Admin admin=new Admin(aid,name,pass,remake,power);
            if( AdminDao.udAdmin(admin))
            {
                JOptionPane.showMessageDialog(null,"修改成功");
                aReTable();
            }
        });

        addAdmin.addActionListener((e)->{
            String name = tName.getText();
            String pass = tPass.getText();
            String remake= tRemake.getText();
            int power = new Integer(tPower.getText());
            Admin admin=new Admin(aid,name,pass,remake,power);
            if(AdminDao.addAdmin(admin)){
                JOptionPane.showMessageDialog(null,"添加成功");
                aReTable();
            }
        });

        delete.addActionListener((e)->{
            String name = tName.getText();
            if(AdminDao.aDelete(name)){
                JOptionPane.showMessageDialog(null,"删除成功");
                aReTable();
            }
        });


    }
    private void aReTable() {
        /**
         * @description: 管理员管理表格刷新
         * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 23:48
         */

        int i=0;
        List<Admin> admins= AdminDao.getAdmin();
        Object[] [] resu=new Object[admins.size()][5];//*x行 4列
        i=0;
        for (Admin admin :admins){
            resu[i][0]=admin.getId();
            resu[i][1]=admin.getName();
            resu[i][2]=admin.getPassWord();
            resu[i][3]=admin.getRemarks();
            resu[i][4]=admin.getPower();
            i++;
        }
        DefaultTableModel model=new DefaultTableModel();
        model.setDataVector(resu,ahears);
        aTable.setModel(model);
    }
}
