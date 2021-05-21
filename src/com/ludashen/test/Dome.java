package com.ludashen.test;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-03 8:40
 */

import javax.swing.JFrame;

public class Dome extends JFrame {

    private static final long serialVersionUID = 1L;

    public Dome(){
        //*创建一个Jlist,显示好友类表**/
//        ArrayList<Users> uArray=new ArrayList<>();
//        uArray.add(new Users("123","123","123",new Date(),true));
//        uArray.add(new Users("哈山东","asdas","123",new Date(),true));
//        FriListModel buddy = new FriListModel(uArray);

//        @SuppressWarnings("rawtypes")
//        JList buddyList = new JList(buddy);
////        设置单元渲染器
//        buddyList.addListSelectionListener(e -> {
//            if(buddyList.getValueIsAdjusting())
//            System.out.println(uArray.get(buddyList.getSelectedIndex()));
//        });
//
//        buddyList.setCellRenderer(new FriListCellRenderer());
//        buddyList.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
//        buddyList.setPreferredSize(new Dimension(360, 350));
//        /*********给好友列表添加滚动条**************/
//        JScrollPane jp = new JScrollPane(buddyList);
//        jp.setPreferredSize(new Dimension(360, 400));
//        this.add(jp);
//        this.setSize(100,300);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(3);
//    }
//
//    public static void main(String[] args) {
//        new Dome();

    }

    public static void main(String[] args) {
        String s="asdas<sd";
        System.out.println(s.endsWith("<"));
    }

}
