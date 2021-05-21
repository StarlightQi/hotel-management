package com.ludashen.test;
/**
 * 使用 categoryDataset 数据集创建折线图
 * 当数据多时，在JPanel中无法完全看到横坐标的值，显示为省略号。
 * 解决方法：
 * 方法1、将报表保存为图片时，设置图片的宽度足够大（2000或3000），图片可以显示横坐标值。
 *  这种方法治标不治本，所以有了第2种方法
 * 方法2、设置X轴上的Lable让其45度倾斜。
 */
import java.awt.*;
import java.awt.geom.Ellipse2D;

import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.*;

import com.ludashen.dao.HistoryDao;
import com.ludashen.hothl.History;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class LineChartDemo1 extends JPanel {

    public LineChartDemo1() {
        JFreeChart jfreechart = createChart(createDataset());
        ChartPanel chartPanel = new ChartPanel(jfreechart);
        chartPanel.setSize(100,200);
        chartPanel.setPreferredSize(new Dimension(500,500));
        add(chartPanel);
    }

    /**
     * 如何区分不同的图例：根据DefaultCategoryDataset.addValue()的第二个参数是否相同来区分。
     * 同一个图例的数据的添加顺序影响最终的显示；不同图例的数据的添加顺序不影响最终的显示。
     * @return
     */
    private static CategoryDataset createDataset() {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
//        for (Map<String, Object> count : HistoryDao.count()) {
//            defaultcategorydataset.addValue((Long) count.get("c"), "First", (Comparable) count.get("t"));
//        }

        for (Map<String, Object> count :HistoryDao.count2()) {
            String g=(String) count.get("x");
            String[] split = g.split(",");
            int t=0;
            int f=0;
            for (String s:split){
                if(s.equals("1"))
                    t++;
                else
                    f++;
            }
            defaultcategorydataset.addValue(t, "normal", (Comparable) count.get("t"));
            defaultcategorydataset.addValue(f, "abnormal", (Comparable) count.get("t"));
        }
        return defaultcategorydataset;
    }

    private static JFreeChart createChart(CategoryDataset categorydataset) {
        JFreeChart jfreechart = ChartFactory.createLineChart(
                "Last 5 days order record",// 图表标题
                "X", // 主轴标签（x轴）
                "Y",// 范围轴标签（y轴）
                categorydataset, // 数据集
                PlotOrientation.VERTICAL,// 方向
                false, // 是否包含图例
                true, // 提示信息是否显示
                false);// 是否使用urls

        // 改变图表的背景颜色
        jfreechart.setBackgroundPaint(Color.white);

        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        categoryplot.setBackgroundPaint(Color.lightGray);
        categoryplot.setRangeGridlinePaint(Color.white);
        categoryplot.setRangeGridlinesVisible(false);

        //修改范围轴。 我们将默认刻度值 （允许显示小数） 改成只显示整数的刻度值。
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // 设置X轴上的Lable让其45度倾斜
//        CategoryAxis domainAxis = categoryplot.getDomainAxis();
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 设置X轴上的Lable让其45度倾斜
//        domainAxis.setLowerMargin(0.0); // 设置距离图片左端距离
//        domainAxis.setUpperMargin(0.0); // 设置距离图片右端距离


        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
                .getRenderer();
        lineandshaperenderer.setShapesVisible(true);
        lineandshaperenderer.setDrawOutlines(true);
        lineandshaperenderer.setUseFillPaint(true);
        lineandshaperenderer.setBaseFillPaint(Color.white);
        lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3.0F));
        lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
        lineandshaperenderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0,
                10.0, 10.0));
        lineandshaperenderer.setItemMargin(0.4); //设置x轴每个值的间距（不起作用？？）

        // 显示数据值
        DecimalFormat decimalformat1 = new DecimalFormat("##.##");// 数据点显示数据值的格式
        lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
                "{2}", decimalformat1));// 设置数据项标签的生成器
        lineandshaperenderer.setBaseItemLabelsVisible(true);// 基本项标签显示
        lineandshaperenderer.setBaseShapesFilled(true);// 在数据点显示实心的小图标


        return jfreechart;
    }

    public static void main(String[] strings) {
        JFrame f=new JFrame();
        LineChartDemo1 lineChartDemo1 = new LineChartDemo1();
        lineChartDemo1.setSize(100,200);
        f.add(lineChartDemo1);
        f.setVisible(true);
        f.setSize(500,600);
//        LineChartDemo1 linechartdemo1 = new LineChartDemo1("JFreeChart - Line Chart Demo 1");
//        linechartdemo1.pack();
//        RefineryUtilities.centerFrameOnScreen(linechartdemo1);
//        linechartdemo1.setVisible(true);
    }

}