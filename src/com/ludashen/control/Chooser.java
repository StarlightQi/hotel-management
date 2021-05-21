package com.ludashen.control;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * @description: 自定义日历输入框
 * @Data: 2019-11-25 18:04
 */

public class Chooser extends JPanel{

    private static final long serialVersionUID = -5384012731547358720L;

    private Calendar calendar;
    private Calendar now = Calendar.getInstance();
    private JPanel calendarPanel;
    private Font font = new Font("Times",Font.PLAIN,12);
    private SimpleDateFormat sdf;
    private final LabelManager lm = new LabelManager();
    private Popup pop;
    private TitlePanel titlePanel;
    private BodyPanel bodyPanel;
    private FooterPanel footerPanel;
    
    private JComponent showDate;
    private boolean isShow = false;
    private static final String DEFAULTFORMAT = "yyyy-MM-dd";
    private static final String[] showTEXT = {"日","一","二","三","四","五","六"};
    private static WeekLabel[] weekLabels = new WeekLabel[7];
    private static int defaultStartDAY = 1;//0 是以星期天开始, 1 是以星期一开始, 2是星期二
    private static Color hoverColor = Color.BLUE; // hover color

    private Chooser(Date date, String format, int startDAY){
        /**
         * @description:
         * @param date  设置日历的默认年月日
         * @param format    日期转为字符串
         * @param startDAY  开始的时间
         * @return:
         * @time: 2019-12-05 12:25
         */

        if(startDAY > -1 && startDAY < 7) defaultStartDAY = startDAY;
        int dayIndex = defaultStartDAY;
        for(int i=0; i<7; i++){
            if(dayIndex > 6) dayIndex = 0;
            weekLabels[i] = new WeekLabel(dayIndex, showTEXT[dayIndex]);
            dayIndex ++ ;
        }
        sdf = new java.text.SimpleDateFormat(format);
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        initCalendarPanel();
    }
    
    public static Chooser getInstance(Date date){
        /**
         * @description: 设置初始化日期
         * @param date   日期
         * @return: com.ludashen.control.Chooser
         * @time: 2019-12-05 12:32
         */
        
        return new Chooser(date, DEFAULTFORMAT, defaultStartDAY);
    }

    
    private void initCalendarPanel(){
        calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0xAA, 0xAA, 0xAA)));
        calendarPanel.add(titlePanel = new TitlePanel(), BorderLayout.NORTH);
        calendarPanel.add(bodyPanel = new BodyPanel(), BorderLayout.CENTER);
        calendarPanel.add(footerPanel = new FooterPanel(),BorderLayout.SOUTH);
        this.addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent event) { }
            public void ancestorRemoved(AncestorEvent event) {hidePanel();}
            public void ancestorMoved(AncestorEvent event) {
                hidePanel();
            }
        });
    }

    public void register(final JComponent showComponent,int... border) {
        this.setLayout(null);
        this.showDate = showComponent;
        showComponent.setRequestFocusEnabled(true);
        if(border.length==0)
            showComponent.setBounds(0,0,120,30);
        if(border.length==1)
            showComponent.setBounds(0,0,border[0],30);
        if(border.length==2)
            showComponent.setBounds(0,0,border[0],border[1]);
        showComponent.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                showComponent.requestFocusInWindow();
            }
        });
        this.add(showComponent, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(90, 25));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        showComponent.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                if (showComponent.isEnabled()) {
                    showComponent.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
            public void mouseExited(MouseEvent me) {
                if (showComponent.isEnabled()) {
                    showComponent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    showComponent.setForeground(Color.BLACK);
                }
            }
            public void mousePressed(MouseEvent me) {
                if (showComponent.isEnabled()) {
                    showComponent.setForeground(hoverColor);
                    if (isShow) {
                        hidePanel();
                    } else {
                        showPanel(showComponent);
                    }
                }
            }
            public void mouseReleased(MouseEvent me) {
                if (showComponent.isEnabled()) {
                    showComponent.setForeground(Color.BLACK);
                }
            }
        });
        showComponent.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                hidePanel();
            }
            public void focusGained(FocusEvent e) { }
        });
    }

    private void hidePanel() {
        if (pop != null) {
            isShow = false;
            pop.hide();
            pop = null;
        }
    }
 
    //show the main panel.
    private void showPanel(Component owner) {
        if (pop != null) pop.hide();
        Point show = new Point(0, showDate.getHeight());
        SwingUtilities.convertPointToScreen(show, showDate);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = show.x;
        int y = show.y;
        if (x < 0) x = 0;
        if (x > size.width - 212) x = size.width - 212;
        if (y > size.height - 167) y -= 165;
        pop = PopupFactory.getSharedInstance().getPopup(owner, calendarPanel, x, y);
        pop.show();
        isShow = true;
    }
    // change text or label's content.
    private void commit() {
        if (showDate instanceof JTextField) {
            ((JTextField) showDate).setText(sdf.format(calendar.getTime()));
        }else if (showDate instanceof JLabel) {
            ((JLabel) showDate).setText(sdf.format(calendar.getTime()));
        }
        hidePanel();
    }

    //日历面板
    private class TitlePanel extends JPanel {
        
        private static final long serialVersionUID = -2865282186037420798L;
        private JLabel preYear,preMonth,center,nextMonth,nextYear,centercontainer;
        
        public TitlePanel(){
            super(new BorderLayout());
            this.setBackground(new Color(190, 200, 200));
            initTitlePanel();
        }

        private void initTitlePanel(){
            /**
             * @description: 这个就是弹出的日历面板，初始化函数
             * @param
             * @return: void
             * @time: 2019-12-05 12:09
             */

            preYear = new JLabel("<<", JLabel.CENTER);
            preMonth = new JLabel("<", JLabel.CENTER);
            center = new JLabel("", JLabel.CENTER);
            centercontainer = new JLabel("", JLabel.CENTER);
            nextMonth = new JLabel(">", JLabel.CENTER);
            nextYear = new JLabel(">>", JLabel.CENTER);
            
            preYear.setToolTipText("Last Year");
            preMonth.setToolTipText("Last Month");
            nextMonth.setToolTipText("Next Month");
            nextYear.setToolTipText("Next Year");
            
            preYear.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 0, 0));
            preMonth.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 15, 0, 0));
            nextMonth.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 0, 15));
            nextYear.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 0, 10));
            
            centercontainer.setLayout(new BorderLayout());
            centercontainer.add(preMonth,BorderLayout.WEST);
            centercontainer.add(center,BorderLayout.CENTER);
            centercontainer.add(nextMonth,BorderLayout.EAST);
            
            this.add(preYear,BorderLayout.WEST);
            this.add(centercontainer,BorderLayout.CENTER);
            this.add(nextYear,BorderLayout.EAST);
            this.setPreferredSize(new Dimension(210, 25));

            updateDate();

            //监听上一个月和下个月的点击
            preYear.addMouseListener(new MyMouseAdapter(preYear, Calendar.YEAR, -1));
            preMonth.addMouseListener(new MyMouseAdapter(preMonth, Calendar.MONTH, -1));
            nextMonth.addMouseListener(new MyMouseAdapter(nextMonth, Calendar.MONTH, 1));
            nextYear.addMouseListener(new MyMouseAdapter(nextYear, Calendar.YEAR, 1));
        }
        
        private void updateDate() {
            /**
             * @description: 日历顶上的时间
             * @param
             * @return: void
             * @time: 2019-12-05 12:11
             */

            center.setText(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH) + 1));
        }


        //监听label的
        class MyMouseAdapter extends MouseAdapter {
            
            JLabel label;
            private int type, value;
            
            public MyMouseAdapter(final JLabel label, final int type, final int value){
                this.label = label;
                this.type = type;
                this.value = value;
            }
            public void mouseEntered(MouseEvent me) {
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setForeground(hoverColor);
            }
            public void mouseExited(MouseEvent me) {
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                label.setForeground(Color.BLACK);
            }
            public void mousePressed(MouseEvent me) {
                calendar.add(type, value);
                label.setForeground(Color.WHITE);
                refresh();
            }
            public void mouseReleased(MouseEvent me) {
                label.setForeground(Color.BLACK);
            }
        }
    }

    // body panel, include week labels and day labels.
    private class BodyPanel extends JPanel {
        
        private static final long serialVersionUID = 5677718768457235447L;
        
        public BodyPanel(){
            super(new GridLayout(7, 7));
            this.setPreferredSize(new Dimension(210, 140));
            initMonthPanel();
        }
        private void initMonthPanel(){
            updateDate();
        }
        public void updateDate() {
            this.removeAll();
            lm.clear();
            Date temp = calendar.getTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(temp);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            
            int index = cal.get(Calendar.DAY_OF_WEEK);
            if(index > defaultStartDAY) cal.add(Calendar.DAY_OF_MONTH, -index + defaultStartDAY);
            else cal.add(Calendar.DAY_OF_MONTH, -index + defaultStartDAY - 7);
            
            for (WeekLabel weekLabel : weekLabels) {
                this.add(weekLabel);
            }
            for (int i = 0; i < 42; i++) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                lm.addLabel(new DayLabel(cal));
            }
            for (DayLabel my : lm.getLabels()) {
                this.add(my);
            }
        }
    }
    
    private class FooterPanel extends JPanel {
        
        private static final long serialVersionUID = 8135037333899746736L;
        private JLabel dateLabel;
        
        public FooterPanel(){
            super(new BorderLayout());
            initFooterPanel();
        }
        private void initFooterPanel(){
            dateLabel = new JLabel("Today is : "+sdf.format(new Date()));
            dateLabel.addMouseListener(new MouseListener() {
                
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {
                    calendar.setTime(new Date());
                    refresh();
                    commit();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    dateLabel.setForeground(Color.BLACK);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    dateLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    dateLabel.setForeground(hoverColor);
                }
                @Override
                public void mouseClicked(MouseEvent e) {}
            });
            this.add(dateLabel);
        }
        public void updateDate(){};
    }
    //refresh all panel
    private void refresh() {
        titlePanel.updateDate();
        bodyPanel.updateDate();
        footerPanel.updateDate();
        SwingUtilities.updateComponentTreeUI(this);
    }
    
    private class WeekLabel extends JLabel {
        
        private static final long serialVersionUID = -8053965084432740110L;
        private String name;
        
        public WeekLabel(int index, String name){
            super(name, JLabel.CENTER);
            this.name = name;
        }
        public String toString(){
            return name;
        }
    }
    
    private class DayLabel extends JLabel implements Comparator<DayLabel>, MouseListener, MouseMotionListener {

        private static final long serialVersionUID = -6002103678554799020L;
        private boolean isSelected;
        private int year, month, day;
        
        public DayLabel(Calendar cal){
            super(""+cal.get(Calendar.DAY_OF_MONTH), JLabel.CENTER);
            this.year = cal.get(Calendar.YEAR);
            this.month = cal.get(Calendar.MONTH);
            this.day = cal.get(Calendar.DAY_OF_MONTH);
            
            this.setFont(font);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            if(month == calendar.get(Calendar.MONTH)) this.setForeground(Color.BLACK);
            else this.setForeground(Color.LIGHT_GRAY);
            
        }
        public boolean getIsSelected() {
            return isSelected;
        }
        public void setSelected(boolean b, boolean isDrag) {
            isSelected = b;
            if (b && !isDrag) {
                int temp = calendar.get(Calendar.MONTH);
                calendar.set(year, month, day);
                if (temp == month) {
                    SwingUtilities.updateComponentTreeUI(bodyPanel);
                } else {
                    refresh();
                }
                this.repaint();
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            //set curr select day's background
            if(day == calendar.get(Calendar.DAY_OF_MONTH) && month == calendar.get(Calendar.MONTH)){
                g.setColor(new Color(0xBB, 0xBF, 0xDA));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            //set current day's border
            if(year == now.get(Calendar.YEAR) && month == now.get(Calendar.MONTH) && day == now.get(Calendar.DAY_OF_MONTH)){
                Graphics2D gd = (Graphics2D) g;
                gd.setColor(new Color(0x55, 0x55, 0x88));
                Polygon p = new Polygon();
                p.addPoint(0, 0);
                p.addPoint(getWidth() - 1, 0);
                p.addPoint(getWidth() - 1, getHeight() - 1);
                p.addPoint(0, getHeight() - 1);
                gd.drawPolygon(p);
            }
            if (isSelected){
                Stroke s = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1.0f, new float[] { 2.0f, 2.0f }, 1.0f);
                Graphics2D gd = (Graphics2D) g;
                gd.setStroke(s);
                gd.setColor(Color.BLACK);
                Polygon p = new Polygon();
                p.addPoint(0, 0);
                p.addPoint(getWidth() - 1, 0);
                p.addPoint(getWidth() - 1, getHeight() - 1);
                p.addPoint(0, getHeight() - 1);
                gd.drawPolygon(p);
            }
            super.paintComponent(g);
        }
        public boolean contains(Point p) {
            return this.getBounds().contains(p);
        }
        private void update() {
            repaint();
        }
        @Override
        public void mouseDragged(MouseEvent e) { }
        @Override
        public void mouseMoved(MouseEvent e) { }
        @Override
        public void mouseClicked(MouseEvent e) { }

        @Override
        public void mousePressed(MouseEvent e) {
            isSelected = true;
            update();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Point p = SwingUtilities.convertPoint(this, e.getPoint(), bodyPanel);
            this.setForeground(Color.BLACK);
            lm.setSelect(p, false);
            commit();
        }
        @Override // change color when mouse over.
        public void mouseEntered(MouseEvent e) {
            this.setForeground(hoverColor);
            this.repaint();
        }
        @Override // change color when mouse exit.
        public void mouseExited(MouseEvent e) {
            if(month == calendar.get(Calendar.MONTH)) this.setForeground(Color.BLACK);
            else this.setForeground(Color.LIGHT_GRAY);
            this.repaint();
        }
        @Override
        public int compare(DayLabel o1, DayLabel o2) {
            Calendar c1 = Calendar.getInstance();
            c1.set(o1.year, o1.month, o1.day);
            Calendar c2 = Calendar.getInstance();
            c2.set(o2.year, o2.month, o2.day);
            return c1.compareTo(c2);
        }
        
    }
    
    private class LabelManager {
        private List<DayLabel> list;
        
        public LabelManager(){
            list = new ArrayList<Chooser.DayLabel>();
        }
        
        public List<DayLabel> getLabels(){
            return list;
        }
        public void addLabel(DayLabel label){
            list.add(label);
        }
        public void clear() {
            list.clear();
        }
        public void setSelect(Point p, boolean b) {
            if (b) {
                boolean findPrevious = false, findNext = false;
                for (DayLabel lab : list) {
                    if (lab.contains(p)) {
                        findNext = true;
                        if (lab.getIsSelected()) findPrevious = true;
                        else lab.setSelected(true, b);
                    } else if (lab.getIsSelected()) {
                        findPrevious = true;
                        lab.setSelected(false, b);
                    }
                    if (findPrevious && findNext) return;
                }
            }else {
                DayLabel temp = null;
                for (DayLabel m : list) {
                    if (m.contains(p)) {
                        temp = m;
                    } else if (m.getIsSelected()) {
                        m.setSelected(false, b);
                    }
                }
                if (temp != null) temp.setSelected(true, b);
            }
        }
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame jf = new JFrame("Date Picker Test");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);
        jf.setBounds(400, 200, 600, 400);
        
        Chooser ser = Chooser.getInstance(new Date());
        JTextField text = new JTextField();
        text.setBounds(10, 10, 200, 30);
        text.setText("2013-10-11");
        ser.register(text);
        
        jf.add(text);
        jf.setVisible(true);
    }
}