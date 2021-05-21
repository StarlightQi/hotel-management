package com.ludashen.panel;

import com.ludashen.control.FileChooser;
import com.ludashen.control.RButton;
import com.ludashen.control.RPanel;
import com.ludashen.control.Tool;
import com.ludashen.dao.UserDao;
import com.ludashen.hothl.Users;

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
 * @Data: 2019-12-07 15:35
 */
public class UserPanel extends RPanel {
    private JTable uTable;   //用户表格
    private String[] uhears={"id","用户名","密码","电话","生日","性别","头像","余额"};   //用户表格
    private int tChoose=0;//获取表格当前列--客户管理
    public UserPanel(){
        super("/resource/admin/admin.png");
        this.setLayout(null);
        uTable=Tool.jTable(0,32,600,350,this);
        uReTable(UserDao.getAllUsers());

        Tool.jLabel(35,400,"用户账号",this);
        Tool.jLabel(35+200,400,"用户名称",this);
        Tool.jLabel(35+400,400,"用户密码",this);
        Tool.jLabel(35+600,400,"用户余额",this);
        Tool.jLabel(35,480,"手机号码",this);
        Tool.jLabel(35+200,480,"出入日期",this);
        Tool.jLabel(35+400,480,"用户性别",this);
        Tool.jLabel(35+600,480,"用户头像",this);

        JTextField tFind= Tool.jTextField(10,0,this,150);
        JTextField id=Tool.jTextField(10, 430, this);
        JTextField name=Tool.jTextField(10+200, 430, this);
        JTextField password=Tool.jTextField(10+400, 430, this);
        JTextField money=Tool.jTextField(10+600, 430, this);

        JTextField phone=Tool.jTextField(10, 510, this);

        JLabel head = Tool.jLabel(610, 30,"", this, 80, 80);

        JTextField birthday=Tool.calendarChoose(10+200,510,Tool.strToDate("2000-1-1"),"2000-1-1",this);

        JTextField Sex=Tool.jTextField(10+400, 510, this);
        JTextField browse= Tool.chooserFile(10+600,510,this);
        RButton allFind=Tool.rButton(200,0,"查找全部",this,110);
        RButton refresh=Tool.rButton(80,580,"刷新",this);
        RButton modify=Tool.rButton(80+200,580,"修改",this);
        RButton delete=Tool.rButton(80+400,580,"删除",this);

        uTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String headImg=uTable.getValueAt(uTable.getSelectedRow(),6).toString();
                id.setText(uTable.getValueAt(uTable.getSelectedRow(),0).toString());
                name.setText(uTable.getValueAt(uTable.getSelectedRow(),1).toString());
                phone.setText(uTable.getValueAt(uTable.getSelectedRow(),2).toString());
                password.setText(uTable.getValueAt(uTable.getSelectedRow(),3).toString());
                birthday.setText(uTable.getValueAt(uTable.getSelectedRow(),4).toString());
                Sex.setText(uTable.getValueAt(uTable.getSelectedRow(),5).toString());
                browse.setText(headImg);
                money.setText(uTable.getValueAt(uTable.getSelectedRow(),7).toString());
                head.setIcon(new ImageIcon((Image) new ImageIcon("image\\head\\"+headImg).getImage().getScaledInstance(80, 80,Image.SCALE_DEFAULT )));
                tChoose=uTable.getSelectedRow();

            }
        });

        refresh.addActionListener((e)->{
            uReTable(UserDao.getAllUsers());
        });

        modify.addActionListener((e)->{
            String uId = id.getText();
            String uName = name.getText();
            String uPhone = phone.getText();
            String uPass = password.getText();
            String uBirthday = birthday.getText();
            String uSex = Sex.getText();
            String heads=browse.getText();
            Users users=new Users(uId,uName,uPass,uPhone,Tool.strToDate(uBirthday),Tool.getSex(uSex),heads,0);
            if(UserDao.modify(users)){
                try {
                    FileChooser.copy("image\\head",heads);
                    String headImg=uTable.getValueAt(tChoose,6).toString();
                    if(!headImg.equals(heads)&&!heads.equals("点击即可浏览文件")) {
                        File file = new File("image\\head\\" + headImg);
                        file.delete();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"修改成功");
                uReTable(UserDao.getAllUsers());
            }
        });

        delete.addActionListener((e)->{
            if (UserDao.delete(id.getText())){
                String headImg=uTable.getValueAt(tChoose,6).toString();
                File file = new File("image\\head\\" + headImg);
                file.delete();
                JOptionPane.showMessageDialog(null,"删除成功");
                uReTable(UserDao.getAllUsers());
            }
        });
        allFind.addActionListener(e->{
            List<Users> user = UserDao.findUser(tFind.getText());
            if(user.size()==0)
                JOptionPane.showMessageDialog(null,"没有查到！");
            uReTable(user);
        });

    }

    private void uReTable(List<Users> users){
        /**
         * @description: 用户信息管理表格刷新
         * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 23:47
         */
        int i=0;
        Object[] [] resu=new Object[users.size()][8];//*x行 4列
        i=0;
        for (Users user :users){
            resu[i][0]=user.getuId();
            resu[i][1]=user.getuName();
            resu[i][2]=user.getuPassword();
            resu[i][3]=user.getuPhone();
            resu[i][4]=user.getuBirthday();
            resu[i][5]=Tool.toSex(user.getUsex());
            resu[i][6]=user.getHead();
            resu[i][7]=user.getMoney();
            i++;
        }
        DefaultTableModel model=new DefaultTableModel();
        model.setDataVector(resu,uhears);
        uTable.setModel(model);

    }
}
