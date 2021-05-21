package com.ludashen.control;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

public class FileUtil {

	public static void copyFile(String srcPath, String targetPath) throws Exception {
		/**
		 * @description: 复制文件 在FileChooser里边调用
		 * target.mkdir()创建目录
		 * (!target.isDirectory()) //判断目标路径是否是目录
		 * @param srcPath 源文件路径
		 * @param targetPath 复制后存放路径
		 * @return: void
		 * @author: 陆均琪
		 * @time: 2019-12-04 21:35
		 */

		File srcFile = new File(srcPath);

		File target = new File("image\\room\\");
		if (!srcFile.exists()) {
			JOptionPane.showMessageDialog(null,"文件不存在");
		}
		if (!srcFile.isFile()) {
            JOptionPane.showMessageDialog(null,"不是文件");
		}
		if (!target.isDirectory()) {
            JOptionPane.showMessageDialog(null,"文件路径不存在");
            target.mkdir();
		}

		// 获取源文件的文件名
		String fileName = srcPath.substring(srcPath.lastIndexOf("\\") + 1);
		//TODO:判断是否存在相同的文件名的文件
		File[] listFiles = target.listFiles();
		for (File file : listFiles) {
			if(fileName.equals(file.getName())){
				fileName += "_1";
			}
		}
		String newFileName = targetPath;
		System.out.println(newFileName);
		File targetFile = new File(newFileName);
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(targetFile);
			//从in中批量读取字节，放入到buf这个字节数组中，
			// 从第0个位置开始放，最多放buf.length个 返回的是读到的字节的个数
			byte[] buf = new byte[8 * 1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(in != null){
				  in.close();
				}
			}catch(Exception e){
				 System.out.println("关闭输入流错误！");
			}
			try{
				if(out != null){
					out.close();
				}
			}catch(Exception e){
				System.out.println("关闭输出流错误！");
			}
		}

	}
}
//————————————————
//版权声明：本文为CSDN博主「storm_fury」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/weixin_43215250/article/details/82908286