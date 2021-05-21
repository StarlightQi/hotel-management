package com.ludashen.test;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
class TestFrame extends JFrame {
    private JLabel jl;
    public TestFrame() {
        setName("一个一个字地显示");
        setBounds(200,200,300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jl = new JLabel();
        jl.setHorizontalTextPosition((int) TOP_ALIGNMENT);
        jl.setBackground(Color.red);
        jl.setSize(300, 300);
        jl.setVerticalAlignment(SwingConstants.TOP);
        add(jl);
        setVisible(true);
    }
    private void setLabelText(String str){
        jl.setText(str);
    }
    private class WriterWord implements Runnable{
        private String words;
        public WriterWord(String words) {
            this.words = words;
        }
        @Override
        public void run() {
            try {
                for (int i = 0; i < words.length(); i++) {
                    setLabelText(words.substring(0,i));
                    Thread.sleep(90);//隔一秒设置label文本
                }
            } catch (Exception e) {
            }
        }
    }
    public static void main(String[] args) {
        TestFrame tf = new TestFrame();
        WriterWord ww = tf.new WriterWord("　上海三湘大厦位于长宁区中山西路，紧邻内环高架，地理位置优越，交通便捷，步行约5分钟可到地铁3号线、4号线、10号线虹桥路站，酒店毗邻交通大学徐汇校区和东华大学延安西路校区，位于虹桥开发区和徐家汇商业圈之间车程约5分钟，距国家会展中心车程约25分钟，外滩及南京路步行街车程约20分钟，距迪士尼乐园车程约60分钟。\n" +
                "　上海三湘大厦是湖南省政府在上海投资建造的涉外酒店，拥有精心打造的各类客房百余间，并设有中餐厅、宴会厅、多功能会议室、商场等配套服务设施，加上贴心、细致、周到的服务，能满足您的商务、休闲所需。");
        Thread t = new Thread(ww);
        t.start();//运行线程
    }
}