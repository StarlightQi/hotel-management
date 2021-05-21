package com.ludashen.control;

import com.ludashen.dao.CommentDao;
import com.ludashen.dao.HistoryDao;
import com.ludashen.dao.ReservationDao;
import com.ludashen.dao.UserDao;

import com.ludashen.hothl.History;
import com.ludashen.hothl.Reservation;
import com.ludashen.hothl.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:  用户信息修改对话框  ---对话框的标题栏自有一个x
 * @author: 陆均琪
 * @Data: 2019-12-04 18:56
 */
public class UserInfoDialog extends JDialog {
    private RPanel p;
    private Map<String, Object> info;
    private String id;//评论id;
    private List<Reservation> reservation;//存储用户和房子的消息
    private int choose=0;//获取列表选中的消息

    public UserInfoDialog(Map<String, Object> info){
        /**
         * @description: 构造函数，把用户登录后保存的用户信息传入进来在设置为全局变量
         * @param info Map集合用户信息
         * @return:
         * @author: 陆均琪
         * @time: 2019-12-04 22:17
         */

        super(new JFrame());
        this.info=info;
        init();
    }

    private void init() {
        /**
         * @description: 初始化函数
         * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 22:19
         */
        setResizable(false);
        JTabbedPane tabbedPane=new JTabbedPane();
        Tool.Tabp(tabbedPane,"个人信息", "/resource/close2.png",mainPanel(), "详情");
        Tool.Tabp(tabbedPane,"消息记录", "/resource/close2.png",commPanel(), "详情");
        Tool.Tabp(tabbedPane,"客房记录", "/resource/close2.png",RoomMange(), "详情");
        tabbedPane.setTabPlacement(1);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/log.png")));//设置标题图标

        this.add(tabbedPane);
        setTitle("用户个人中心");
        setSize(new Dimension(500,800));
        setLocationRelativeTo(null);
    }

    private JPanel mainPanel(){
        /**
         * @description: 用户信息修改的主面板
         * @param
         * @return: javax.swing.JPanel
         * @author: 陆均琪
         * @time: 2019-12-04 22:19
         */

        p=new RPanel("/resource/user/user.png");
        p.setLayout(null);

        Date birthday1= (Date) info.get("uBirthday");
        //文本框
        JTextField fielHead=Tool.chooserFile(160,10,p,140,140);
        fielHead.setBorder(null);// 取消边框
        fielHead.setOpaque(false);// 设置控件透明
        fielHead.setText("");
        JLabel head=Tool.jLabel(160,10,"",p,130,130);
        head.setIcon(new ImageIcon((Image) new ImageIcon("image\\head\\"+info.get("head")).getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT )));

        fielHead.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                head.setIcon(new ImageIcon((Image) new ImageIcon(((FileChooser) fielHead).getPath()).getImage().getScaledInstance(120, 120,Image.SCALE_DEFAULT )));
            }
        });

        Tool.jLabel(100,150,"账号：",p);
        Tool.jLabel(100,150+60,"名称：",p);
        Tool.jLabel(100,150+60*2,"密码：",p);
        Tool.jLabel(90,150+60*3,"手机号：",p);
        Tool.jLabel(100,150+60*4,"生日：",p);
        Tool.jLabel(100,150+60*5,"性别：",p);

        JTextField uid=Tool.jTextField(150,150,p,200);
        JTextField name=Tool.jTextField(150,150+60,p,200);
        JTextField password= Tool.passwordField(150,150+60*2,'*',p,200);
        JTextField uPhone= Tool.jTextField(150,150+60*3,p,200);

        JTextField uBirthday=Tool.calendarChoose(150,150+60*4,birthday1,Tool.dateToStr(birthday1),p,200);
        JTextField uSex=Tool.jTextField(150,150+60*5,p,200);
        JTextField tMoney=Tool.jTextField(150,150+60*7,p,200);

        //通过定义全局的用户信息设置到文本框中
        uid.setText(info.get("uid").toString());
        name.setText(info.get("uName").toString());
        password.setText(info.get("uPassword").toString());
        uPhone.setText(info.get("uPhone").toString());
        uBirthday.setText(info.get("uBirthday").toString());
        uSex.setText(Tool.toSex((Boolean) info.get("uSex")));
        RButton modify=Tool.rButton(200,150+60*6,"修改",p,120);
        RButton recharge=Tool.rButton(50,150+60*7,"充值",p,80);
        JLabel ye=Tool.jLabel(180,150+60*8-15,"当前余额为"+info.get("money")+"元",p,300);


        //修改按钮监听-用户个人信息修改
        modify.addActionListener((e)->{
                String uId = uid.getText();
                String uName = name.getText();
                String Phone = uPhone.getText();
                String Pass =new String(password.getText());
                String birthday = uBirthday.getText();
                String Sex = uSex.getText();
                String heads=fielHead.getText();
                String ihead = (String) info.get("head");
                if(heads.equals(""))
                    heads=ihead;
                Users users=new Users(uId,uName,Pass,Phone,Tool.strToDate(birthday),Tool.getSex(Sex),heads,0);
                if(JOptionPane.showInputDialog(null,"请输入原密码：").equals(info.get("uPassword"))) {
                    if (UserDao.modify(users)) {
                        try {
                            FileChooser.copy("image\\head", heads);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (!heads.equals(ihead) && !heads.equals("")) {
                            File file = new File("image\\head\\" + ihead);
                            file.delete();
                        }
                        info.put("head", heads);
                        JOptionPane.showMessageDialog(null, "修改成功");
                    }
                }else
                JOptionPane.showMessageDialog(null,"密码错误！");
        });

        recharge.addActionListener(e->{
            if (!tMoney.getText().equals("")) {
                    float money = Float.parseFloat(tMoney.getText());
                    float ymoey = (float) info.get("money");
                    if (JOptionPane.showConfirmDialog(null, "您现在的余额为" + ymoey + "\n是否继续充值" + money + "元") == 0) {
                        if (money < 1000) {
                            info.put("money", money + ymoey);
                            ye.setText("当前余额为" + info.get("money") + "元");
                            new RechargeDialog(uid.getText(), ymoey + money).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "单次金额不能超过1000");
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"数据不能为空");
                }
        });

        return p;
    }

    private JPanel commPanel(){
        JPanel cp=new RPanel("/resource/user/user.png");
        cp.setLayout(null);
        JTable table=Tool.jTable(0,0,500,300,cp);
        RButton refresh=Tool.rButton(80,400,"刷新",cp);
        RButton delete=Tool.rButton(260,400,"删除",cp);

        Tool.comment(table, CommentDao.getRoomComment((String) info.get("uid"),2));
        refresh.addActionListener(e->{
            Tool.comment(table, CommentDao.getRoomComment((String) info.get("uid"),2));
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
              id=table.getValueAt(table.getSelectedRow(),0).toString();
            }
        });
        delete.addActionListener(e->{
            CommentDao.cDelete(id);
            Tool.comment(table, CommentDao.getRoomComment((String) info.get("uid"),2));
        });
//        cp.add(jp);
        return cp;
    }

    private JPanel RoomMange(){
        JPanel rp=new RPanel("/resource/user/user.png");
        rp.setLayout(null);
        JList list = Tool.jList(0,0,500,300,rp);
        uRoom(list);
        RButton refresh=Tool.rButton(80,400,"刷新",rp);
        RButton checkOut=Tool.rButton(260,400,"退房",rp);
        JLabel label=Tool.jLabel(200,450,"酒店最近订房记录表",rp);
        JTable jTable=Tool.jTable(0,500,500,300,rp);
        history(jTable,HistoryDao.getUserHistory((String) info.get("uid")));

        list.addListSelectionListener(e -> {
            if (list.getValueIsAdjusting())
                try {
                    choose=list.getSelectedIndex();
                } catch (Exception ex) {
                    list.clearSelection();

                }
        });
        checkOut.addActionListener(e -> {
            //5少时内为正常退房，直接删除订单，视为点击错误
            //超过5小时内退房会问退房原因，并收取相应的赔偿金
            //超过预约时间视为违约，扣除100金额--不用用户退了管理员进行处理
            //用户如约而至管理员即可进行退定不会扣除金额
            if (reservation.size()!=0) {
                int gid = reservation.get(choose).getHouse().getHid();
                int money = reservation.get(choose).getUsers().getMoney();
                String name = reservation.get(choose).getHouse().gethName();
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long diff = new Date().getTime() - reservation.get(choose).getsDate().getTime();
                long hour = diff % nd / nh;
                long zdate = reservation.get(0).getdDate().getTime() - reservation.get(choose).getsDate().getTime();
                long dayhour = zdate % nd / nh;
                if (hour > 5) {
                    JOptionPane.showMessageDialog(null, "由于已经超过5少时，执行退房的话我们后收取相应的服务费用\n费用为：50元");
                    if (JOptionPane.showConfirmDialog(null, "是否退掉" + name) == 0) {
                        if (HistoryDao.history(gid)) {
                            if (ReservationDao.dDelete(gid)) {
                                String reason = JOptionPane.showInputDialog(null, "请输入退房原因：");
                                UserDao.setMoney((String) info.get("uid"), money - 50);
                                History history = new History(gid, (String) info.get("uid"), false, reason, 50);
                                HistoryDao.udHistory(history);
                                uRoom(list);
                                JOptionPane.showMessageDialog(null, "信息处理成功你现在的余额为" + String.valueOf(money - 50));
                            }
                        }
                    }
                } else {
                    if (JOptionPane.showConfirmDialog(null, "是否退掉" + name) == 0) {
                        if (HistoryDao.history(gid)) {
                            if (ReservationDao.dDelete(gid)) {
                                String reason = JOptionPane.showInputDialog(null, "请输入退房原因：");
                                History history = new History(gid, (String) info.get("uid"), false, reason, 0);
                                HistoryDao.udHistory(history);
                                uRoom(list);
                                JOptionPane.showMessageDialog(null, "信息处理成功");
                            }
                        }
                    }
                }
            }
        });


        refresh.addActionListener(e -> {
            uRoom(list);
        });
        list.setCellRenderer(new ReserCellRenderer());

        return rp;
    }

    private void uRoom(JList list){
        reservation= ReservationDao.getUserRoom((String) info.get("uid"));
        ReservationModel buddy = new ReservationModel(reservation);
        list.setModel(buddy);
        list.setPreferredSize(new Dimension(300, 60*reservation.size()));

    }


    public static void history(JTable table, List<History> histories){
        /**
         * @description: 客房表格刷新
         * @param sq  1 查看全部客房 2查看空的客房 3查看有客户预定的客房
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 23:46
         */
        String[] chead={"房子","订房时间","原退房时间","原因","扣除金额"};  //客房表格
        int i=0;

        Object[] [] resu=new Object[histories.size()][6];//*x行 4列
        i=0;
        for (History history:histories){
            resu[i][0]=history.getHid();
            resu[i][1]=history.getDtime();
            resu[i][2]=history.getCtime();
            resu[i][3]=history.getReason();
            resu[i][4]=history.getDeduct();
            i++;
        }
        DefaultTableModel model=new DefaultTableModel();
        model.setDataVector(resu,chead);
        table.setModel(model);
    }

}
