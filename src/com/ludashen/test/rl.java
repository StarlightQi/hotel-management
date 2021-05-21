//package com.ludashen.test;
//
//
//import java.awt.*;
//import java.awt.event.*;
//import java.beans.*;
//import java.io.Serializable;
//import java.util.*;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import javax.swing.border.TitledBorder;
//
///**
// * @author 李钟尉
// *
// */
//public class Date extends JPanel {
//
//    public static final String DATE_CHANGED = "DateChanged";
//
//    /**
//     * 日历控制按钮的超类
//     *
//     * @author Administrator
//     */
//    private class ActionAdapter implements ActionListener, Serializable {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            initDateField();// 初始化日期文本框
//        }
//    }
//
//    private final class DayClientListener extends MouseAdapter {
//        @Override
//        public void mousePressed(MouseEvent e) {
//            JLabel label = (JLabel) e.getSource();
//            if (label.getText().isEmpty())
//                return;
//            reMark();
//            String text = label.getText();
//            int dayNum = Integer.parseInt(text);
//            calendar.set(Calendar.DAY_OF_MONTH, dayNum);// 改变当前日历缓存对象
//
//            initDateField();// 初始化日期文本框
//            label.setOpaque(true);
//            label.setBackground(new Color(0xeeee00));
//            calendarChanged();
//        }
//    }
//
//    private static final long serialVersionUID = 1L;
//    private Calendar calendar; // @jve:decl-index=0:
//    private java.sql.Date date;
//    private JPanel jPanel1 = null;
//    private JPanel toolBar = null;
//    private JCheckBox jButton = null;
//    private JCheckBox jButton1 = null;
//    private JFormattedTextField dateField = null;
//    private JCheckBox jButton2 = null;
//    private JCheckBox jButton3 = null;
//    private JLabel[][] days;
//    private final int YEAR;
//    private final int MONTH;
//    private final int DAY;
//    private Color gridColor = Color.DARK_GRAY; // @jve:decl-index=0:
//
//    private DayClientListener dayClientListener;
//    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
//            this);
//
//    /**
//     * This is the default constructor
//     */
//    public Date() {
//        super();
//        calendar = Calendar.getInstance();
//        YEAR = calendar.get(Calendar.YEAR);
//        MONTH = calendar.get(Calendar.MONTH);
//        DAY = calendar.get(Calendar.DAY_OF_MONTH);
//        dayClientListener = new DayClientListener();// 初始化日期标签的监听器
//        initialize();
//        calendar.set(YEAR, MONTH, DAY);// 恢复当前日期
//    }
//
//    public void addPropertyListener(PropertyChangeListener listener) {
//        propertyChangeSupport.addPropertyChangeListener(listener);
//    }
//
//    /**
//     * 触发日期改变事件
//     */
//    private void calendarChanged() {
//        propertyChangeSupport.firePropertyChange(DATE_CHANGED, null, calendar);
//    }
//
//    /**
//     * This method initializes dateField
//     *
//     * @return javax.swing.JTextField
//     */
//    private JFormattedTextField getDateField() {
//        if (dateField == null) {
//            dateField = new JFormattedTextField();
//            dateField.setColumns(12);
//            dateField.setEditable(false);
//            dateField.setHorizontalAlignment(SwingConstants.CENTER);
//            dateField.addMouseListener(new MouseAdapter() {
//                public void mousePressed(MouseEvent e) {
//                    calendar.set(YEAR, MONTH, DAY);
//                    initDateField();// 初始化日期文本框
//                    initDayButtons();// 初始化日期按钮
//                    calendarChanged();
//                }
//            });
//        }
//        return dateField;
//    }
//
//    /**
//     * This method initializes jButton
//     *
//     * @return javax.swing.JButton
//     */
//    private JCheckBox getJButton() {
//        if (jButton == null) {
//            jButton = new JCheckBox();
//            jButton.setText("<<");
//            jButton.setHorizontalTextPosition(SwingConstants.CENTER);
//            jButton.addActionListener(new ActionAdapter() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    calendar.add(Calendar.YEAR, -1);
//                    calendarChanged();
//                    initDayButtons();
//                    JCheckBox source = (JCheckBox) e.getSource();
//                    source.setSelected(false);
//                    super.actionPerformed(e);
//                }
//            });
//        }
//        return jButton;
//    }
//
//    /**
//     * This method initializes jButton1
//     *
//     * @return javax.swing.JButton
//     */
//    private JCheckBox getJButton1() {
//        if (jButton1 == null) {
//            jButton1 = new JCheckBox();
//            jButton1.setText("<");
//            jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
//            jButton1.addActionListener(new ActionAdapter() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    calendar.add(Calendar.MONTH, -1);
//                    calendarChanged();
//                    initDayButtons();
//                    JCheckBox source = (JCheckBox) e.getSource();
//                    source.setSelected(false);
//                    super.actionPerformed(e);
//                }
//            });
//        }
//        return jButton1;
//    }
//
//    /**
//     * This method initializes jButton2
//     *
//     * @return javax.swing.JButton
//     */
//    private JCheckBox getJButton2() {
//        if (jButton2 == null) {
//            jButton2 = new JCheckBox();
//            jButton2.setText(">");
//            jButton2.setHorizontalTextPosition(SwingConstants.CENTER);
//            jButton2.addActionListener(new ActionAdapter() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    calendar.add(Calendar.MONTH, 1);
//                    calendarChanged();
//                    initDayButtons();
//                    JCheckBox source = (JCheckBox) e.getSource();
//                    source.setSelected(false);
//                    super.actionPerformed(e);
//                }
//            });
//        }
//        return jButton2;
//    }
//
//    /**
//     * This method initializes jButton3
//     *
//     * @return javax.swing.JButton
//     */
//    private JCheckBox getJButton3() {
//        if (jButton3 == null) {
//            jButton3 = new JCheckBox();
//            jButton3.setText(">>");
//            jButton3.setHorizontalTextPosition(SwingConstants.CENTER);
//            jButton3.addActionListener(new ActionAdapter() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    calendar.add(Calendar.YEAR, 1);
//                    calendarChanged();
//                    initDayButtons();
//                    JCheckBox source = (JCheckBox) e.getSource();
//                    source.setSelected(false);
//                    super.actionPerformed(e);
//                }
//            });
//        }
//        return jButton3;
//    }
//
//    /**
//     * 创建星期标题和日期按钮
//     *
//     * @return javax.swing.JPanel
//     */
//    private JPanel getJPanel1() {
//        if (jPanel1 == null) {
//            GridLayout gridLayout2 = new GridLayout();
//            gridLayout2.setColumns(7);
//            gridLayout2.setRows(0);
//            jPanel1 = new JPanel();
//            jPanel1.setOpaque(false);
//            jPanel1.setLayout(gridLayout2);
//            JLabel[] week = new JLabel[7];
//            week[0] = new JLabel("日");
//            week[0].setForeground(Color.MAGENTA);
//            week[1] = new JLabel("一");
//            week[2] = new JLabel("二");
//            week[3] = new JLabel("三");
//            week[4] = new JLabel("四");
//            week[5] = new JLabel("五");
//            week[6] = new JLabel("六");
//            week[6].setForeground(Color.ORANGE);
//            for (JLabel theWeek : week) {
//                theWeek.setHorizontalAlignment(SwingConstants.CENTER);
//                Font font = theWeek.getFont();
//                Font deriveFont = font.deriveFont(Font.BOLD);
//                theWeek.setFont(deriveFont);
//                String info = theWeek.getText();
//                if (!info.equals("日") && !info.equals("六"))
//                    theWeek.setForeground(Color.BLUE);
//                getJPanel1().add(theWeek);
//            }
//            days = new JLabel[6][7];
//            for (int i = 0; i < 6; i++) {
//                for (int j = 0; j < 7; j++) {
//                    days[i][j] = new JLabel();
//                    days[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
//                    days[i][j].setHorizontalAlignment(SwingConstants.CENTER);
//                    days[i][j].setOpaque(false);
//                    days[i][j].addMouseListener(dayClientListener);
//                    getJPanel1().add(days[i][j]);
//                }
//            }
//            initDateField();// 初始化日期文本框
//            initDayButtons();// 初始化日期按钮
//        }
//        return jPanel1;
//    }
//
//    /**
//     * 创建日期的控制面板
//     *
//     * @return javax.swing.JToolBar
//     */
//    private JPanel getToolBar() {
//        if (toolBar == null) {
//            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//            gridBagConstraints4.insets = new Insets(0, 0, 0, 0);
//            gridBagConstraints4.gridy = 0;
//            gridBagConstraints4.gridx = 4;
//            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//            gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
//            gridBagConstraints3.gridy = 0;
//            gridBagConstraints3.gridx = 3;
//            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//            gridBagConstraints2.fill = GridBagConstraints.BOTH;
//            gridBagConstraints2.gridx = 2;
//            gridBagConstraints2.gridy = 0;
//            gridBagConstraints2.weightx = 1.0;
//            gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
//            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//            gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
//            gridBagConstraints1.gridy = 0;
//            gridBagConstraints1.gridx = 1;
//            GridBagConstraints gridBagConstraints = new GridBagConstraints();
//            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
//            gridBagConstraints.gridy = 0;
//            gridBagConstraints.gridx = 0;
//            toolBar = new JPanel();
//            toolBar.setLayout(new GridBagLayout());
//            toolBar.setMinimumSize(new Dimension(11, 22));
//            toolBar.setPreferredSize(new Dimension(162, 30));
//            toolBar.setOpaque(false);
//            toolBar.add(getJButton(), gridBagConstraints);
//            toolBar.add(getJButton1(), gridBagConstraints1);
//            toolBar.add(getDateField(), gridBagConstraints2);
//            toolBar.add(getJButton2(), gridBagConstraints3);
//            toolBar.add(getJButton3(), gridBagConstraints4);
//        }
//        return toolBar;
//    }
//
//    private void initDateField() {
//        java.util.Date time = calendar.getTime();
//        getDateField().setValue(time);
//    }
//
//    /**
//     * 初始化所有日期按钮
//     */
//    private void initDayButtons() {
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        // 获取本月天数
//        int dayNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        // 获取本月第一天的星期数
//        int firstDayIndex = calendar.get(Calendar.DAY_OF_WEEK)
//                - calendar.getFirstDayOfWeek();
//        int dateNum = 1;
//        // 清除原有日历日期
//        for (int i = 0; i < days.length; i++) {
//            for (int j = 0; j < days[i].length; j++) {
//                days[i][j].setText("");
//            }
//        }
//        // 填充新日历日期
//        for (int i = 0; i < days.length; i++) {
//            int j = 0;
//            if (i == 0)// 略过月初日期之前的位置
//                j = firstDayIndex;
//            for (; j < 7; j++) {
//                days[i][j].setText(dateNum + "");
//                dateNum++;
//                if (dateNum > dayNum + 1)// 舍弃本月之后的日期
//                    days[i][j].setText("");
//            }
//        }
//        reMark();
//        calendar.set(year, month, day);// 恢复当前日期
//    }
//
//    /**
//     * This method initializes this
//     *
//     * @return void
//     */
//    private void initialize() {
//        this.setSize(200, 260);
//        this.setLayout(new BorderLayout());
//        this.setOpaque(false);
//        this.add(getToolBar(), BorderLayout.NORTH);
//        this.add(getJPanel1(), BorderLayout.CENTER);
//    }
//
//    /**
//     * 为日期按钮做标记
//     */
//    private void reMark() {
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        // 获取本月第一天的星期数
//        int firstDayIndex = calendar.get(Calendar.DAY_OF_WEEK)
//                - calendar.getFirstDayOfWeek();
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//        LineBorder lightGrayBorder = new LineBorder(gridColor, 1);
//        LineBorder redBorder = new LineBorder(Color.RED, 1);
//        int dateNum = 1;
//        for (int i = 0; i < days.length; i++) {
//            for (int j = 0; j < days[i].length; j++) {
//                days[i][j].setOpaque(false);
//                if (year == YEAR && month == MONTH
//                        && dateNum - firstDayIndex == DAY) {
//                    days[i][j].setBorder(redBorder);
//                } else {
//                    days[i][j].setBorder(lightGrayBorder);
//                }
//                dateNum++;
//            }
//        }
//    }
//
//    public void setGridColor(Color gridColor) {
//        this.gridColor = gridColor;
//    }
//
//    public Calendar getCalendar() {
//        return calendar;
//    }
//
//    public void setCalendar(Calendar calendar) {
//        this.calendar = calendar;
//    }
//
//    public java.sql.Date getDate() {
//        long millis = getCalendar().getTimeInMillis();
//        date = new java.sql.Date(millis);
//        return date;
//    }
//}
//
//
////private Date getCalendarPanel() {
////        if (calendarPanel == null) {
////        calendarPanel = new Date();
////        calendarPanel.setBorder(BorderFactory.createTitledBorder(null,
////        "另一天的日程安排", TitledBorder.DEFAULT_JUSTIFICATION,
////        TitledBorder.ABOVE_TOP,
////        new Font("sansserif", Font.BOLD, 12), new Color(48, 102,
////        163)));
////        calendarPanel.addPropertyListener(new PropertyChangeListener() {
////public void propertyChange(PropertyChangeEvent e) {
////        if ((e.getPropertyName().equals("DateChanged"))) {
////        }
////        }
////        });
////        }
////        return calendarPanel;
////        }