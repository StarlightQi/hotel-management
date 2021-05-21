package com.ludashen.frame;

import com.ludashen.control.*;

import com.ludashen.dao.CommentDao;
import com.ludashen.dao.HouseDao;
import com.ludashen.dao.RoomInfoDao;
import com.ludashen.dao.UserDao;
import com.ludashen.hothl.Comment;
import com.ludashen.hothl.House;
import com.ludashen.hothl.RoomInfo;


import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户界面查看客房 -预定房间
 * @author: 陆均琪
 * @Data: 2019-11-26 0:38
 */

public class UserFrame extends JFrame {
    private Map<String, Object> userInfo;//保存用户信息的
    private JMenuBar menuBar;
    private JLabel title,img;   //选中列表显示房子标题和图片
    private JTextPane tDetails;     //客房详情
    private JTextPane tComment;     //顾客点评
    private List<House> house;
    private int hid;    //用于记录房子id的
    private String detail;  //记录房子详情的
    private JList buddyList;    //列表信息
    private int choose; //获取当前选择的列
    private List<String> image=new ArrayList<>();//用于放当前房间的片
    private int index;//记录当前放到那张照片了


    public UserFrame(String uid){
        //构造函数根据登录id查找用户信息
        super("酒店管理系统");
        try {
        userInfo= UserDao.users(uid);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"没有这个用户请重新登录");
            System.exit(0);
        }
        setTitle("酒店系统----"+userInfo.get("uName")+"在线");
        init();
    }


    private void init() {
        /**
         * @description: 初始化函数
             * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-25 19:09
         */

        setResizable(false);
        //设置标题图标
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/log.png")));

        title= new JLabel();
        img=new JLabel();
        tDetails=new JTextPane();
        tComment=new JTextPane();
        tComment.setEnabled(false);
        tDetails.setEnabled(false);
        tDetails.setBackground(new Color(0x134FAE));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (JOptionPane.showConfirmDialog(null, "是否最小化托盘") != 0) {
                    System.exit(0);
                }
            }
        });
        tDetails.setContentType("text/html");
        tComment.setContentType("text/html");
        title.setFont(new Font("",1,30));


        topBar();

        Tool.SystemTrayInitial(this);
        this.add(mainPanel());
        this.setSize(900,750);
        this.setLocationRelativeTo(null);
    }


    private JPanel mainPanel(){
        /**
         * @description: 主面板实现主要功能浏览客房，预定客房
         * @param
         * @return: javax.swing.JPanel
         * @author: 陆均琪
         * @time: 2019-12-05 0:00
         */

        JPanel p=new JPanel(null);
        JPanel panel = new JPanel();

        tDetails.setOpaque(false);
        RScrollPane details=new RScrollPane(tDetails,"/resource/bg1.jpg");

        panel.setLayout(new BorderLayout());
        JPanel p2=new JPanel(new BorderLayout());
        JTextField field=new JTextField(50);
        field.setSize(500,30);
        RButton send=new RButton("发送");
        p2.add(field,BorderLayout.WEST);
        p2.add(send,BorderLayout.EAST);
        panel.add(p2, BorderLayout.SOUTH);

        tComment.setOpaque(false);
        RScrollPane comment=new RScrollPane(tComment,"/resource/bg1.jpg");

        panel.add(comment, BorderLayout.CENTER);


        JTabbedPane jTabbedPane =Tool.jTabbedPane(235,450,p,640,215);
        jTabbedPane.setTabPlacement(1);
        Tool.Tabp(jTabbedPane,"酒店详情", "/resource/tabp/applicatio.png",details, "详情");
        Tool.Tabp(jTabbedPane,"点评列表", "/resource/tabp/Comment.png", panel, "列表");

        RButton ding=Tool.rButton(800,5,"预定",p,80);
        title.setBounds(240,5,260,45);
        img.setBounds(240,50,630,400);
        p.add(Tool.getFunButton(500, 10, "/resource/button/back1.png", "/resource/button/back2.png", e->{
            setBackImage();
        }));
        p.add(Tool.getFunButton(600, 10, "/resource/button/next1.png", "/resource/button/next2.png", e -> {
            setNetImage();
        }));

        RButton refresh=Tool.rButton(0,633,"刷新",p,235);

        send.addActionListener(e->{
            Comment comment1=new Comment((String) userInfo.get("uid"),String.valueOf(hid),field.getText());
            if(CommentDao.setCommentDao(comment1)) {
                tComment.setText(commentDao());
                JOptionPane.showMessageDialog(null,"评论成功！");
                field.setText("");
            }
        });

        ding.addActionListener(e ->{
            new YudingDialog(house.get(choose).getHid(),(String) userInfo.get("uid"),(Float) userInfo.get("money")).setVisible(true);
        });

        refresh.addActionListener(e -> {
            reRoom();
        });

        p.add(img);
        p.add(title);
        p.add(listRoom());
        p.add(refresh);
        return p;
    }

    private JScrollPane listRoom(){
        buddyList = new JList();
        buddyList.setOpaque(false);
        reRoom();
        if(house.size()!=0){
            img.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+house.get(0).gethImg()).getImage().getScaledInstance(630, 400,Image.SCALE_DEFAULT )));
            title.setText(house.get(0).gethName());
            image.add(house.get(0).gethImg());
            hid=house.get(0).getHid();
            detail= "<html><h2>详情："+house.get(0).gethDetails()+"</h2>"+roomInfo()+"</html>";
            tDetails.setText(detail);
            choose=0;
        }
        else {
            tDetails.setText("<h1>没有空房间</h1>");
        }

        buddyList.addListSelectionListener(e -> {
            if(buddyList.getValueIsAdjusting())
            try {
                img.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+house.get(buddyList.getSelectedIndex()).gethImg()).getImage().getScaledInstance(630, 400,Image.SCALE_DEFAULT )));    //设置JLable的图片
                image.clear();
                index=0;
                image.add(house.get(buddyList.getSelectedIndex()).gethImg());
                hid=house.get(buddyList.getSelectedIndex()).getHid();
                detail= "<html><h2>详情："+house.get(buddyList.getSelectedIndex()).gethDetails()+"</h2>"+roomInfo()+"</html>";
                title.setText(house.get(buddyList.getSelectedIndex()).gethName());
                tDetails.setText(detail);
                tComment.setText(commentDao());
                choose=buddyList.getSelectedIndex();

                for (RoomInfo imgs:RoomInfoDao.getRoomInfo(hid,3)){
                    image.add(imgs.getFuntion());
                }

            }catch (Exception e1){
                buddyList.clearSelection();
            }

        });

        buddyList.setCellRenderer(new FriListCellRenderer());
        buddyList.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        buddyList.setPreferredSize(new Dimension(230, 72*house.size()));
        RScrollPane jp = new RScrollPane(buddyList,"");
        jp.setBounds(0,0,233,630);
        return jp;
    }

    private void setNetImage() {

        if(index==image.size()-1) {
            JOptionPane.showMessageDialog(null,"已经是最后一张了");
            return;
        }
        else
            index++;
            img.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+image.get(index)).getImage().getScaledInstance(630, 400,Image.SCALE_DEFAULT )));
    }

    private void setBackImage(){
        if(index==0) {
            JOptionPane.showMessageDialog(null,"已经是第一张了");
            return;
        }
        index--;
        img.setIcon(new ImageIcon((Image) new ImageIcon("image\\room\\"+image.get(index)).getImage().getScaledInstance(630, 400,Image.SCALE_DEFAULT )));

    }

    public void reRoom(){
        /**
         * @description: 房间列表刷新
             * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-05 20:22
         */

        house= HouseDao.getHouser(2);
        HouseModel buddy = new HouseModel(house);
        buddyList.setModel(buddy);


    }

    private String roomInfo(){
        /**
         * @description: 遍历客房的消息,转换为一定格式的字符串
         * @return: java.lang.String
         * @author: 陆均琪
         * @time: 2019-12-05 0:00
         */

        List<RoomInfo> roomInfo =RoomInfoDao.getRoomInfo(hid,1);
        StringBuffer str=new StringBuffer();
        for(RoomInfo info:roomInfo){
            str.append("<p>"+info.getName()+"：");
            str.append(info.getFuntion()+"</p>");
        }
        return str.toString();
    }
    private String commentDao(){
        int a=0;
        List<Comment> comments= CommentDao.getRoomComment(String.valueOf(hid),1);
        StringBuffer str=new StringBuffer();
        str.append("<html>");
        for (Comment comment:comments){
            if(a%2==0) {
                str.append("<div style='background-color:#3333CC;'><span style='font-size: 20px'>");
                str.append(comment.getUid() + "</span><br>");
                str.append(comment.getComment() + "<br>" + comment.getDate());
                str.append("<br></div>");
            }else {
                str.append("<div  style='background-color:#3300FF;'><span style='font-size: 20px'>");
                str.append(comment.getUid() + "</span><br>");
                str.append(comment.getComment() + "<br>" + comment.getDate());
                str.append("<br></div>");
            }
            a++;
        }
        if (comments.size()==0)
            str.append("占时没有评论");

        str.append("</table></html>");
        return str.toString();
    }





    private void topBar() {
        /**
         * @description: 制作顶端的菜单栏
         * @param
         * @return: void
         * @author: 陆均琪
         * @time: 2019-11-26 0:18
         */
        menuBar = new JMenuBar();
        menuBar.setLayout(null);
        JButton head=new CircleButton(40,(String)userInfo.get("head"));
        head.setBounds(840, 0, 40, 40);
        head.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new UserInfoDialog(userInfo).setVisible(true);
            }
        });

        JMenu menu = new JMenu("酒店");
        menu.setBounds(0,0,80,30);
        JMenuItem zmenu = new JMenuItem("酒店简介");
        zmenu.addActionListener(e->{
            IntroduceDialog introduceDialog = new IntroduceDialog();
            introduceDialog.setVisible(true);
            new Thread(introduceDialog).start();
        });
        menu.add(zmenu);
        menuBar.add(head);
        menuBar.add(menu);
        menuBar.setPreferredSize(new Dimension(300, 40));
        setJMenuBar(menuBar);
    }
}
