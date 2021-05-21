package com.ludashen.test;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class LabelFont {

    public static void main(String[] args) {
        Font oldLabelFont = UIManager.getFont("Label.font");
        UIManager.put("Label.font",oldLabelFont.deriveFont(Font.PLAIN));

        JFrame f = new JFrame("LabelFont Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new FlowLayout());

        JLabel df = new JLabel("Default JLabel font");
        f.getContentPane().add(df);

        JLabel ef = new JLabel("Font explicitly set");
        ef.setFont(oldLabelFont);
        f.getContentPane().add(ef);

        f.pack();
        f.setVisible(true);
    }
}