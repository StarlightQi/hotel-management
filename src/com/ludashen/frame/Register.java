package com.ludashen.frame;

import com.ludashen.control.*;
import com.ludashen.dao.UserDao;

import com.ludashen.hothl.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import java.util.Date;

/**
 * @description: 注册界面
 * @author: 陆均琪
 * @Data: 2019-11-25 22:27
 */
public class Register extends JFrame {

    private RPanel p;
    private String getCode;//放置当前验证码
    private String codePath;//获取生成的文件名，方便删除
    private JLabel imgCode;
    public Register(){
        /**
         * @description: 构建函数继承框架
             * @param
         * @return:
         * @author: 陆均琪
         * @time: 2019-11-25 23:11
         */
        super("酒店管理系统");
        init();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                codeDelete();
                super.windowClosing(e);
            }
        });

    }

    private void init(){
        /**
         * @description: 初始化方法
             * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-25 22:29
         */
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/log.png")));//设置标题图标‘

        this.add(getPanel());
        this.setSize(400,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
    }


    private JPanel getPanel(){
        /**
         * @description: 总面面板实现各种数据录入
         * @param
         * @return: javax.swing.JPanel
         * @author: 陆均琪
         * @time: 2019-11-25 23:10
         */
        if(p==null){
            //设置注册界面的背景
            p=new RPanel("/resource/rbg1.png");
            p.setLayout(null);
            Tool.jLabel(50,4,"账号：",p);
            Tool.jLabel(50,4+62,"密码：",p);
            Tool.jLabel(30,4+62*2,"再次输入：",p);
            Tool.jLabel(40,4+62*3,"手机号：",p);
            Tool.jLabel(30,4+62*4,"出生日期：",p);
            Tool.jLabel(40,4+62*5,"验证码：",p);
            Tool.jLabel(50,4+62*6,"性别：",p);

            JTextField tName=Tool.jTextField(125,4,p,220);
            JPasswordField tPass=Tool.passwordField(125,4+62,'*',p,220);
            JPasswordField tRepass=Tool.passwordField(125,4+62*2,'*',p,220);
            JTextField tPhone=Tool.jTextField(125,4+62*3,p,220);
            JTextField code=Tool.jTextField(125,4+62*5,p);
            imgCode=Tool.jLabel(260,4+62*5+3,"",p);
            RButton register=Tool.rButton(125,4+62*7+20,"注册",p);
            RButton rlogin=Tool.rButton(125,4+62*7+20,"注册",p);



           JTextField tBirthday=Tool.calendarChoose(125,4+62*4,Tool.strToDate("2000-1-1"),"2000-1-1",p,200);


            JComboBox cSex=new JComboBox();
            cSex.setBounds(120,4+62*6,100,30);
            cSex.addItem("--请选择--");    //向下拉列表中添加一项
            cSex.addItem("男");
            cSex.addItem("女");

            setCode(imgCode);
            imgCode.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    setCode(imgCode);
                }
            });

            rlogin.addActionListener(e->{
            new LoginFrame().setVisible(true);
            dispose();
            });
            register.addActionListener( (e)->{
                String pass=new String(tPass.getPassword());
                String rePass=new String(tRepass.getPassword());
                if (pass.equals(rePass)&&pass.length()>=6) {
                    if(!tName.getText().equals("")&&tName.getText().length()>3){

                        String regex = "^1[3456789]\\d{9}$";
                        String birthday="^\\d\\d\\d\\d\\-.*?\\d-.*?\\d$";
                        if(tPhone.getText().matches(regex)&&tBirthday.getText().matches(birthday)) {

                            Users users = new Users(tName.getText(),tName.getText(), pass, tPhone.getText(), Tool.strToDate(tBirthday.getText()), Tool.getSex(cSex.getSelectedItem().toString()),"",0);
                            if(code.getText().equalsIgnoreCase(getCode)) {
                                if (UserDao.register(users)) {//判断是否注册成功成功则自动跳到登录页面
                                    JOptionPane.showMessageDialog(null, "注册成功");
                                    codeDelete();
                                    LoginFrame login = new LoginFrame();
                                    login.setVisible(true);
                                    login.setUser(tName.getText(), new String(tPass.getPassword()));
                                    dispose();

                                } else
                                    JOptionPane.showMessageDialog(null, "注册失败，该账户已经被注册");
                            }else {
                                JOptionPane.showMessageDialog(null,"您输入的验证码有误");
                                //验证码有误刷新验证码
                                setCode(imgCode);
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(null, "手机号不匹配,或者生日格式错误（2000-10-2）");
                    }else
                        JOptionPane.showMessageDialog(null,"名字不为空且不能小于3位");
                }
                else
                    JOptionPane.showMessageDialog(null,"密码不一致,和密码要不小于6位");
            });

            p.add(cSex);
        }
        return p;
    }

    private void setCode(JLabel imgCode){
        codeDelete();
        ImageCode imageCode=new ImageCode(80,30,5,150);
        codePath=new Date().getTime()+".png";
        try {
            imageCode.write(codePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getCode=imageCode.getCode();//获取验证码
        imgCode.setIcon(new ImageIcon(codePath));
    }

    private void codeDelete(){
        if(codePath!=null) {
            File file = new File(codePath);
            file.delete();
        }
    }
    private JPanel jPanel(Component comp1,Component comp2){
        /**
         * @description: 这面板容器是专门用于布局的
         * @param comp1 控件1
         * @param comp2 控件2
         * @return: javax.swing.JPanel
         * @author: 陆均琪
         * @time: 2019-11-25 23:11
         */
        JPanel jp=new JPanel();
        jp.setBorder(null);// 取消边框
        jp.setOpaque(false);// 设置控件透明
        jp.add(comp1);
        jp.add(comp2);
        return jp;
    }


    private JButton jButton(String text, ActionListener l){
        /**
         * @description: JButton，减少重复代码
         * @param text 按钮文本
         * @param l 添加按钮监控事件
         * @return: javax.swing.JButton
         * @author: 陆均琪
         * @time: 2019-11-25 23:47
         */
        RButton bt=new RButton(text);
        bt.addActionListener(l);
        return bt;
    }

}
