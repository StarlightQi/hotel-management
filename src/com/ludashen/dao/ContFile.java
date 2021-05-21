package com.ludashen.dao;

import java.io.*;

/**
 * @description: 用于读写本地文件的
 * @author: 陆均琪
 * @time: 2019-11-26 0:40
 */

public class ContFile {
    public void writeFile(String filePathAndName, String fileContent) {
        try {
            java.io.File f = new java.io.File(filePathAndName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f));
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            System.out.println("写文件内容操作出错");
            e.printStackTrace();
        }
    }

    public String readFile(String filePathAndName) {
        String fileContent = "";
        try {
            java.io.File f = new java.io.File(filePathAndName);
            if ((f.isFile()) && (f.exists())) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f));
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent = fileContent + line + "\n";
                }
                read.close();
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return fileContent;
    }
}
