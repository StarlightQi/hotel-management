package com.ludashen.control;

/**
 * @description:文件选择输入框，此控件在管理员界面的添加客房的图片时用，文件选择输入框，选中后把时间戳的名字显示出来
 * @author: 陆均琪
 * @Data: 2019-12-03 22:45
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Date;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileChooser extends RTextField implements MouseListener {
    /**
     * @description:继承文本框和继承鼠标监控接口，重新了鼠标点击事情
     * @author: 陆均琪
     * @time: 2019-12-04 21:05
     */

    private static String choose;

    public FileChooser(){
        /**
         * @description: 构建函数
         * @param textField：传入文本输入框控件，用于设置输入框文本，
         * @author: 陆均琪
         * @time: 2019-12-04 21:08
         */

        addMouseListener(this);
     }

        public static void copy(String path,String getName) throws Exception {
        /**
         * @description: 复制文件到相对路径的image下 FileUtil.copyFile是自定义复制文件类，参数为原路径和要复制的路径
             * @param getName：获取最终的文件名
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 21:13
         */

        if (choose==null)return;
            File directory = new File(path);
            FileUtil.copyFile(choose,directory.getCanonicalPath()+"\\"+getName);
        }

        public static String getPath(){

        return choose;
        }

    @Override
    public void mouseClicked(MouseEvent e) {
        /**
         * @description:重写接口的监控事件 ，其中JFileChooser文件选择器 filter过滤掉不是图片的文件，file获取选中的文件路径
         * String name=new Date().getTime()+".png";是通过时间戳获取不会重复的文件名，并设置的文本输入框
         * @param e
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 21:10
         */
        JFileChooser jfc=new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("图像文件（JPG/GIF）", "JPG", "JPEG", "GIF","PNG");
        jfc.setFileFilter(filter);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file!=null&&file.isFile()){
            System.out.println(file);
            choose= file.getAbsolutePath();
            String name=new Date().getTime()+".png";
            this.setText(name);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}