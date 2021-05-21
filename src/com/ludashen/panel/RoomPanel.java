package com.ludashen.panel;

import com.ludashen.control.*;
import com.ludashen.dao.HistoryDao;
import com.ludashen.dao.ReservationDao;
import com.ludashen.dao.UserDao;
import com.ludashen.hothl.History;
import com.ludashen.hothl.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-07 15:40
 */
public class RoomPanel extends RPanel {
    private JLabel img; //设置列表选中后的客房图标
    private String title;   //设置列表选中后的客房标题
    private JTextPane tDetails; //用于显示客房详情的
    private List<Reservation> reservation;//存储用户和房子的消息
    private int rChoose;//退房保存选中的列
    private JList buddyList;
    private RButton checkOut;
    private RButton refresh;
    private RButton errorCheck;


    public RoomPanel(){
        super("/resource/admin/admin.png");
        setLayout(null);
        tDetails=new JTextPane();
        tDetails.setContentType("text/html");
        tDetails.setBounds(245,305,250,295);
        tDetails.setOpaque(false);

        img=new JLabel();
        img.setBounds(245,0,250,300);


        RScrollPane djp=new RScrollPane(tDetails,"");
        djp.setBounds(245,305,250,295);
        buddyList = Tool.jList(0,0,240,600,this);
        buddyList.setCellRenderer(new ReserCellRenderer());
        uRoom(buddyList);

        checkOut= Tool.rButton(250,610,"退房",this,200,50);
        refresh=Tool.rButton(5,610,"刷新",this,200,50);
        errorCheck=Tool.rButton(500,610,"异常退房",this,200,50);

        StatisticsPanel stat=new StatisticsPanel();
        stat.setBounds(500,340,250,260);
        JPanel his=new HistoryStatics();
        his.setBounds(500,0,250,300);

        Thread panelThread = new Thread(stat);
        panelThread.start();

        monitor();
        add(his);
        add(stat);
        add(djp);
        add(img);
    }

   private void monitor(){
       if(reservation.size()!=0){
           img.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+reservation.get(0).getHouse().gethImg()).getImage().getScaledInstance(250, 300,Image.SCALE_DEFAULT )));
           tDetails.setText(reservation.get(0).toString());
           title=reservation.get(0).getHouse().gethName();
           rChoose=0;
       }else {
           tDetails.setText("<h1>没有人预定房间</h2>");
       }

       buddyList.addListSelectionListener(e -> {
           if(buddyList.getValueIsAdjusting())
               try {
                   rChoose=buddyList.getSelectedIndex();
                   img.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\" + reservation.get(buddyList.getSelectedIndex()).getHouse().gethImg()).getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT)));    //设置JLable的图片
                   tDetails.setText(reservation.get(buddyList.getSelectedIndex()).toString());
                   title = (reservation.get(buddyList.getSelectedIndex()).getHouse().gethName());
               }catch (Exception ex){
                   buddyList.clearSelection();
               }
       });

       errorCheck.addActionListener((e)->{
           try {
               int gid = reservation.get(rChoose).getHouse().getHid();
               String uid = reservation.get(rChoose).getUsers().getuId();
               if (JOptionPane.showConfirmDialog(null, "是否退掉" + title) == 0) {
                   if (HistoryDao.history(gid)) {
                       if (ReservationDao.dDelete(gid)) {
                           String reason = JOptionPane.showInputDialog(null, "请输入退房原因：");
                           String deduct = JOptionPane.showInputDialog(null, "请输入退房金额：");
                           int money = new Integer(deduct);
                           UserDao.setMoney(uid, reservation.get(rChoose).getUsers().getMoney() - money);
                           History history = new History(gid, uid, false, reason, money);
                           HistoryDao.udHistory(history);
                           uRoom(buddyList);
                           JOptionPane.showMessageDialog(null, "信息处理成功");
                       }
                   }
               }
           }catch (Exception e1){
               JOptionPane.showMessageDialog(null,"当前没有房间");
           }
       });

       checkOut.addActionListener(e->{
           try {
               int gid = reservation.get(rChoose).getHouse().getHid();
               if (JOptionPane.showConfirmDialog(null, "是否退掉" + title) == 0) {
                   if (HistoryDao.history(gid)) {
                       if (ReservationDao.dDelete(gid)) {
                           JOptionPane.showMessageDialog(null, "退房成功");
                           uRoom(buddyList);
                       }
                   }
               }
           }catch (Exception ex){
               JOptionPane.showMessageDialog(null,"当前没有房间不支持退房");
           }
       });

       refresh.addActionListener(e->{
           uRoom(buddyList);

       });
    }

    private void uRoom(JList list){
        reservation= ReservationDao.getAllHouse();
        ReservationModel buddy = new ReservationModel(reservation);
        list.setModel(buddy);
        list.setPreferredSize(new Dimension(300, 72*reservation.size()));

    }
}
