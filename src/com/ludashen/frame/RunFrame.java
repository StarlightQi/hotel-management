package com.ludashen.frame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
/**
 * @description: 启动页面
 * @author: 陆均琪
 * @Data: 2019-11-26 23:30
 */
public class RunFrame extends JDialog implements ActionListener {
    private int gi=0;
    private Random random = new Random();
    private Dimension screenSize;
    private JPanel graphicsPanel;
    private final static int gap = 20;
    //存放雨点顶部的位置信息(marginTop)
    private int[] posArr;
    //行数
    private int lines;
    //列数
    private int columns;
    public RunFrame() {
        initComponents();
    }
    private void initComponents() {
        setLayout(new BorderLayout());
        graphicsPanel = new GraphicsPanel();
        add(graphicsPanel, BorderLayout.CENTER);
        this.setUndecorated(true);
        setSize(500,400);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        lines = screenSize.height / gap;
        columns = screenSize.width / gap;
        posArr = new int[columns + 1];
        random = new Random();
        for (int i = 0; i < posArr.length; i++) {
            posArr[i] = random.nextInt(lines);
        }

        new Timer(120, this).start();
    }
    private int getChr() {
        return random.nextInt(10);
    }
    //定时器的
    @Override
    public void actionPerformed(ActionEvent e) {
        graphicsPanel.repaint();
    }

    private class GraphicsPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(getFont().deriveFont(Font.BOLD));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, screenSize.width, screenSize.height);
            //当前列
            int currentColumn = 0;
            for (int x = 0; x < screenSize.width; x += gap) {
                int endPos = posArr[currentColumn];
                g2d.setColor(Color.CYAN);
                g2d.drawString(String.valueOf(getChr()), x, endPos * gap);
                int cg = 0;
                for (int j = endPos - 15; j < endPos; j++) {
                    //颜色渐变
                    cg += 20;
                    if (cg > 255) {
                        cg = 255;
                    }
                    g2d.setColor(new Color(0, cg, 0));
                    g2d.drawString(String.valueOf(getChr()), x, j * gap);
                }
                //每放完一帧，当前列上雨点的位置随机下移1~5行
                posArr[currentColumn] += random.nextInt(5);
                //当雨点位置超过屏幕高度时，重新产生一个随机位置
                if (posArr[currentColumn] * gap > getHeight()) {
                    posArr[currentColumn] = random.nextInt(lines);
                }
                currentColumn++;
            }
            if(gi>6){
                g2d.setFont(new Font("italicc",3,25));
                g2d.setColor(Color.WHITE);
                g2d.drawString("欢迎使用酒店管理系统", getWidth()/2-100, getHeight()/2);
            }
            if(gi++>25){
                dispose();
                new LoginFrame().setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        new RunFrame();
    }
}