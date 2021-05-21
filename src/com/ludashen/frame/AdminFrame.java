package com.ludashen.frame;

import com.ludashen.control.*;
import com.ludashen.dao.*;
import com.ludashen.hothl.History;
import com.ludashen.panel.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import java.util.List;
import java.util.Map;


/**
 * @description: 主操作面板
 * @author: 陆均琪
 * @Data: 2019-11-26 0:38
 */
public class AdminFrame extends JFrame {
    private JMenuBar menuBar;  //菜单栏
    private JTabbedPane jTabbedPane;   //切换板
    private Map<String, Object> adminInfo;  //保存登录后用户信息的

    public AdminFrame(String name) {
        //构造函数传入登录的用户名获取用户信息
        super("酒店管理系统");
        setResizable(false);
        try {
            adminInfo = AdminDao.admin(name);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"抱歉没有这个用户请联系管理员添加，最高管理员为root");
        }
        setTitle("酒店管理---" + adminInfo.get("name"));
        init();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (JOptionPane.showConfirmDialog(null, "是否最小化托盘") != 0) {
                    System.exit(0);
                }
            }
        });
    }

    private void init() {
        /**
         * @description: 初始化函数
         * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-25 19:09
         */
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/log.png")));
        jTabbedPane = new JTabbedPane();
        add(jTabbedPane, "Center");
        jTabbedPane.setTabPlacement(2);
        Tool.Tabp(jTabbedPane, "客房管理", "/resource/tabp/house.png", new HousePanel(), "客房管理");
        Tool.Tabp(jTabbedPane, "用户管理", "/resource/tabp/user.png", new UserPanel(), "用户管理");
        Tool.Tabp(jTabbedPane, "订单管理", "/resource/tabp/ordering.png", new RoomPanel(), "用户管理");
        Tool.Tabp(jTabbedPane, "客房服务", "/resource/tabp/server.png", new MoreRoomPanel(), "用户管理");
        Tool.Tabp(jTabbedPane, "历史记录", "/resource/tabp/history.png", history(), "历史记录");

        //判断权限1超级管理员权限，管理员权限显示管理员管理面板
        if (adminInfo.get("power").equals(1)) {
            Tool.Tabp(jTabbedPane, "管理员", "/resource/tabp/drupal.png", new AdminPanel(), "管理员管理");
        }
        topBar(); //顶部菜单栏
        Tool.SystemTrayInitial(this);
        this.setSize(900, 750);
        this.setLocationRelativeTo(null);
    }

    private JPanel history() {
        RPanel panel=new RPanel("/resource/admin/admin.png");
        panel.setLayout(null);
        JTable historys=Tool.jTable(0,0,750,500,panel);
        rHistory(historys,HistoryDao.getAllHistory());

        JButton refresh=Tool.rButton(100,600,"刷新",panel);
        JButton clear=Tool.rButton(250,600,"清空",panel);
        JButton export=Tool.rButton(400,600,"导出表格",panel);
        refresh.addActionListener(e->{
            rHistory(historys,HistoryDao.getAllHistory());
        });
        clear.addActionListener(e->{
            if(JOptionPane.showConfirmDialog(null,"是否清空表格")==0) {
                HistoryDao.clear();
                JOptionPane.showMessageDialog(null, "清空成功");
                rHistory(historys, HistoryDao.getAllHistory());
            }
        });

        export.addActionListener(e->{
            StringBuffer data=new StringBuffer();
            data.append("id,房子,用户,订房时间,原退房时间,现退房时间,结果,原因,扣除金额\n");
            for(History history:HistoryDao.getAllHistory()){
                data.append(history.getId()+",");
                data.append(history.getHid()+",");
                data.append(history.getUdi()+",");
                data.append(history.getDtime()+",");
                data.append(history.getTtime()+",");
                data.append(history.getCtime()+",");
                data.append(history.getResult()+",");
                data.append(history.getReason()+",");
                data.append(history.getDeduct());
                data.append("\n");
            }

            new ContFile().writeFile("info.csv",data.toString());
            JOptionPane.showMessageDialog(null,"文件保存成功"+new File("info.csv").getAbsolutePath());

        });

        return panel;

    }

    public static void rHistory(JTable table, List<History> histories){
        /**
         * @description: 客房表格刷新
         * @param sq  1 查看全部客房 2查看空的客房 3查看有客户预定的客房
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 23:46
         */
        String[] chead={"id","房子","用户","订房时间","原退房时间","现退房时间","结果","原因","扣除金额"};  //客房表格
        int i=0;

        Object[] [] resu=new Object[histories.size()][9];//*x行 4列
        i=0;
        for (History history:histories){
            resu[i][0]=history.getId();
            resu[i][1]=history.getHid();
            resu[i][2]=history.getUdi();
            resu[i][3]=history.getDtime();
            resu[i][4]=history.getTtime();
            resu[i][5]=history.getCtime();
            resu[i][6]=history.getResult();
            resu[i][7]=history.getReason();
            resu[i][8]=history.getDeduct();
            i++;
        }
        DefaultTableModel model=new DefaultTableModel();
        model.setDataVector(resu,chead);
        table.setModel(model);
    }

    private void topBar() {
        /**
         * @description: 制作顶端的菜单栏
         * @param   什么功能都没实现
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-26 0:18
         */
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Start");
        JMenuItem zmenu = new JMenuItem("Start");
        menu.add(zmenu);
        menuBar.add(menu);
        menuBar.setPreferredSize(new Dimension(300, 40));
        setJMenuBar(menuBar);
    }

}
