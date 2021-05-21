package com.ludashen.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class JScrollPaneDemo
{
    JScrollPane scrollPane;
    public JScrollPaneDemo()
    {
        JFrame f = new JFrame("JScrollpane1");
        Container contentPane = f.getContentPane();
        JTextPane label=new JTextPane();
        label.setText("qwewe");
        JButton btn = new JButton("Button");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        f.setSize(new Dimension(350, 220));
        f.pack();
        f.setVisible(true);
    }
    public static void main(String[] args)
    {
        new JScrollPaneDemo();
    }
}
