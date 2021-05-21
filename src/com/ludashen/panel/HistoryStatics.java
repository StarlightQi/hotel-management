package com.ludashen.panel;

import com.ludashen.dao.HistoryDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-08 18:27
 */
public class HistoryStatics extends JPanel {
    public HistoryStatics() {
        setLayout(new BorderLayout());
        JFreeChart jfreechart = createChart(createDataset());
        ChartPanel chartPanel = new ChartPanel(jfreechart);
        chartPanel.setSize(100,200);
        chartPanel.setPreferredSize(new Dimension(250,300));
        JLabel label =new JLabel("");
        add(chartPanel,BorderLayout.NORTH);
        add(label,BorderLayout.SOUTH);
    }

    /**
     * 如何区分不同的图例：根据DefaultCategoryDataset.addValue()的第二个参数是否相同来区分。
     * 同一个图例的数据的添加顺序影响最终的显示；不同图例的数据的添加顺序不影响最终的显示。
     * @return
     */
    private static CategoryDataset createDataset() {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        for (Map<String, Object> count : HistoryDao.count()) {
            defaultcategorydataset.addValue((Long) count.get("c"), "First", (Comparable) count.get("t"));
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
        categoryplot.setRangeGridlinePaint(Color.ORANGE);
        categoryplot.setRangeGridlinesVisible(false);

        //修改范围轴。 我们将默认刻度值 （允许显示小数） 改成只显示整数的刻度值。
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


        // 设置X轴上的Lable让其45度倾斜
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 设置X轴上的Lable让其45度倾斜
        domainAxis.setLowerMargin(0.0); // 设置距离图片左端距离
        domainAxis.setUpperMargin(0.0); // 设置距离图片右端距离


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
        lineandshaperenderer.setItemMargin(0.1); //设置x轴每个值的间距（不起作用？？）

        // 显示数据值
        DecimalFormat decimalformat1 = new DecimalFormat("##.##");// 数据点显示数据值的格式
        lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
                "{2}", decimalformat1));// 设置数据项标签的生成器
        lineandshaperenderer.setBaseItemLabelsVisible(true);// 基本项标签显示
        lineandshaperenderer.setBaseShapesFilled(true);// 在数据点显示实心的小图标


        return jfreechart;
    }
}
