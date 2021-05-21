package com.ludashen.test;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * MyPanel.java.
 * @author Kaiyan Zhang
 */
public class aasdas extends JPanel implements Runnable{
    int x = 0,y = 400;
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawRect(x, y, 20, 20);
    }
    @Override
    public void run(){
        while(true){
            if(x>800)
                x = 0;
            else
                x = x + 10;
            this.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args){
        aasdas p = new aasdas();
        /* panel thread, paint the monkey */

        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* begin to paint */
        Thread panelThread = new Thread(p);
        panelThread.start();
    }
}
