package com.ludashen.control;

import com.ludashen.dao.ContFile;
import com.ludashen.frame.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @description: 设置登录，对话框，登录界面点击设置，然后跳出来的对话框，用于记住密码，管理员登录等设置
 * @author: 陆均琪
 * @Data: 2019-11-25 23:23
 */
public class LoginSetDialog extends JDialog {
    private JCheckBox jCheckBox;
    private RPanel p;
    public LoginSetDialog(){
        init();
        setResizable(false);
    }

    private void init() {
        //设置标题图标
        URL re = getClass().getResource("/resource/log.png");
        ImageIcon ico =new ImageIcon(re);
        setIconImage(Toolkit.getDefaultToolkit().getImage(re));

        p=new RPanel("/resource/bg1.jpg");//自己绘制的带图片的面板

        jCheckBox=new JCheckBox("记住密码");
        jCheckBox.setBorder(null);// 取消边框
        jCheckBox.setOpaque(false);// 设置控件透明

        //实现记住密码功能
        ContFile file=new ContFile();
        String[] sb=file.readFile("pass").split("@");
        if(sb[0].trim().equals("0")){
            jCheckBox.setSelected(true);
        }

        jCheckBox.addActionListener((e)->{
            if(jCheckBox.isSelected())
                file.writeFile("pass","0");
            else
                file.writeFile("pass", "1");
        });

        //选中管理员登录或者用户登录
        JComboBox selectAdmin=new JComboBox();
        selectAdmin.addItem("用户");
        selectAdmin.addItem("管理员");
        p.add(selectAdmin);
        selectAdmin.addActionListener((e)->{
                int gerIndex=selectAdmin.getSelectedIndex();
                if(gerIndex==1)
                    LoginFrame.setAdmin(true);
                else
                    LoginFrame.setAdmin(false);
        });

        p.add(jCheckBox);
        this.add(p);
        this.setTitle("设置");
        this.setSize(200,200);
        this.setLocationRelativeTo(null);

    }
}
