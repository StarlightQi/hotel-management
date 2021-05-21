package com.ludashen.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @description: 数据库连接池--就是多用户同时登陆时可以并发多个连接--或者同时间进行多个数据操作---该数据库连接池是阿里巴巴提供的
 * @author: 陆均琪
 * @time: 2019-12-04 22:25
 */

public class DaoConnection {

    //1.定义成员变量 DataSource
    private static DataSource ds ;
    static{
        try {
            //1.加载配置文件
            Properties pro = new Properties();
            //在相对路径中有这个文件---连接数据库时注意改这个文件的数据库 用户和密码要与本机数据库密码相同
            pro.load(DaoConnection.class.getClassLoader().getResourceAsStream("druid.properties"));
            //2.获取DataSource
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        /**
         * @description:
             * @param
         * @return: java.sql.Connection
         * @author: 陆均琪
         * @time: 2019-12-04 22:23
         */
        return ds.getConnection();
    }


    public static void close(Statement stmt,Connection conn){
        /**
         * @description:
         * @param stmt  释放数据资源
         * @param conn
         * @return: void
         * @author: 陆均琪
         * @time: 2019-12-04 22:23
         */
       close(null,stmt,conn);
    }


    public static void close(ResultSet rs , Statement stmt, Connection conn){

        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null){
            try {
                conn.close();//归还连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static DataSource getDataSource(){
        /**
         * @description: 获取连接池方法
             * @param
         * @return: javax.sql.DataSource
         * @author: 陆均琪
         * @time: 2019-12-04 22:24
         */

        return  ds;
    }
}
