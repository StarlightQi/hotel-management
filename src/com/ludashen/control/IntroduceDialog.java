package com.ludashen.control;

import javax.swing.*;
import java.awt.*;

/**
 * @description:酒店简介实现，打字特效
 * @author: 陆均琪
 * @Data: 2019-12-09 20:45
 */
public class IntroduceDialog extends JDialog implements Runnable {
    private String words;
    private int i = 0;
    JTextPane pane;

    public IntroduceDialog() {
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/log.png")));
        setTitle("酒店简介");
        words = "<h2>纵情享受苏州酒店的奢华</h2>" +
                "<div color=\"red\">洲际酒店 苏州洲际酒店</div>" +
                "江苏省苏州工业园区旺墩路288号 , 苏州 ,215028, 中国<br>" +
                "+86-512-62858888 电子邮件<br>" +
                " <p>这家地标性酒店坐落于苏州金鸡湖濒水走道上，酒店位置四通八达，为游览艺术、购物和娱乐中心提供诸多便利。豪华设施有：提供全方位服务的洲际水疗、俯瞰金鸡湖美景的广阔泳池以及配备齐全的健身中心。客人可以在供应世界各地美食的四个世界级餐厅宴飨自己，也可以在设有水疗浴室并坐拥壮丽美景的客房体验当代舒适。</p>";
        pane = new JTextPane();
        pane.setContentType("text/html");
        pane.setOpaque(false);
        RScrollPane scrollPane = new RScrollPane(pane, "/resource/bg1.jpg");
        setSize(500, 500);
        setLocationRelativeTo(null);
        add(scrollPane);

    }

    public static void main(String[] args) {
        IntroduceDialog introduceDialog = new IntroduceDialog();
        introduceDialog.setVisible(true);
        new Thread(introduceDialog).start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < words.length(); i++) {
                String substring = words.substring(0, i);
                pane.setText(substring);
                Thread.sleep(90);
            }
        } catch (Exception e) {
        }
    }
}
