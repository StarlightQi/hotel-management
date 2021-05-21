package com.ludashen.frame;

import com.ludashen.control.*;
import com.ludashen.dao.AdminDao;
import com.ludashen.dao.ContFile;
import com.ludashen.dao.UserDao;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.SQLException;

/**
 * @description: 登录界面
 * @author: 陆均琪
 * @Data: 2019-11-26 0:38
 */
public class LoginFrame extends JFrame {
    private RPanel RP1;
    private RPanel RP2;
    private JPanel p;
    private JTextField userName;
    private JPasswordField password;
    private static Boolean isAdmin=false;
    private JLabel head;
    ContFile file=new ContFile();
    public LoginFrame(){
        super();
        init();
    }

    public static void setAdmin(Boolean b){
        isAdmin=b;
    }

    private void init(){
        /**
         * @description: 初始化函数
         * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-26 18:41
         */

        setResizable(false);
        //设置图片
        URL re = getClass().getResource("/resource/log.png");
        ImageIcon ico =new ImageIcon(re);
        setIconImage(Toolkit.getDefaultToolkit().getImage(re));//设置标题图标
        this.setLayout(null);
        RP1=new RPanel("/resource/Lmain.png");
        RP2=new RPanel("/resource/login.png");
        RP1.setBounds(0,0,600,400);
        RP2.setBounds(0,400,600,100);
        RP2.setLayout(null);
        RP1.setLayout(null);


        Tool.jLabel(50,40,"账号：",RP2);
        Tool.jLabel(280,40,"密码：",RP2);
        userName= Tool.jTextField(100, 40, RP2,162);
        password=Tool.passwordField(330, 40, '*',RP2,159);

        head=Tool.jLabel(250,150,"",RP1,100,100);

        userName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                head.setIcon(new ImageIcon((Image) new ImageIcon("image\\head\\" +UserDao.head(userName.getText())).getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT )));
            }
        });
        RButton LoginButton=Tool.rButton(500, 39,"登录",RP2,80);
        JLabel register=Tool.jLabel(530, 69,"快速注册",RP2);
        JLabel set=Tool.jLabel(480, 69, "设置",RP2);

        setChangColor(register, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new Register().setVisible(true);
                dispose();
            }
        });

        setChangColor(set, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new LoginSetDialog().setVisible(true);
            }
        });

        RP1.add(Tool.getFunButton(560, 5, "/resource/close2.png", "/resource/close1.png", e->{
               System.exit(0);
        }));
        RP1.add(Tool.getFunButton(520, 5, "/resource/min1.png", "/resource/min2.png", e -> {
                setExtendedState(JFrame.ICONIFIED);
        }));

        LoginButton.addActionListener(e -> {
            try {
                String pass = file.readFile("pass");
                String[] sp = pass.split("@");
                String name = userName.getText();
                String spass = new String(password.getPassword());
                if (!isAdmin) {
                    if (UserDao.getUser(name, spass)) {
                        if (sp[0].trim().equals("0")) {
                            file.writeFile("pass", "0@" + name + "@" + spass);
                        } else {
                            file.writeFile("pass", "1");
                        }
                        new UserFrame(name).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "您现在正在登陆用户系统，而用户密码或名字错误");
                    }
                } else {
                    if (AdminDao.getAdminLogin(name, spass)) {
                        if (sp[0].trim().equals("0")) {
                            file.writeFile("pass", "0@" + name + "@" + spass);
                        } else {
                            file.writeFile("pass", "1");
                        }
                        new AdminFrame(name).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "您现在正在登陆管理系统，用户密码或名字错误");
                    }

                }
            }catch (Exception e1){
//                new ContFile().writeFile("e.txt",e1.printStackTrace());
                JOptionPane.showMessageDialog(null,e1.getLocalizedMessage());
            }
        });

        pass();
        this.add(RP1);
        this.add(RP2);
        this.setUndecorated(true);//去掉标题栏
        this.setSize(600,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
    }

    private void pass(){
        String pass=file.readFile("pass");
        String[] sp=pass.split("@");
        if(sp[0].trim().equals("0")&&sp.length>1){
            userName.setText(sp[1]);
            password.setText(sp[2]);
            head.setIcon(new ImageIcon((Image) new ImageIcon("image\\head\\" +sp[1]).getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT )));
        }
    }



    private void setChangColor(JLabel jLabel,MouseListener l){
        /**
         * @description: 添加JLable的鼠标监控事项
         * @param jLabel
         * @param l
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-07 10:37
         */
        jLabel.addMouseListener(l);
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jLabel.setForeground(Color.ORANGE);
            }

        });
    }

    public void setUser(String name,String pass){
        /**
         * @description: 注册页面跳转到登录页面时为登录页面的用户名和密码赋值
         * @param name 用户名
         * @param pass 密码
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-26 11:47
         */
        userName.setText(name);
        password.setText(pass);
    }

}
