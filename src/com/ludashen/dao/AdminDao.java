package com.ludashen.dao;

import com.ludashen.control.Tool;
import com.ludashen.hothl.Admin;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @description:    管理员数据库表的各种操作
 * @author: 陆均琪
 * @Data: 2019-12-04 17:26
 */
public class AdminDao {
    private static JdbcTemplate template = new JdbcTemplate(DaoConnection.getDataSource());

    public static Map<String, Object> admin(String name){
        /**
         * @description: 通过登录的名字查询管理员
         * @param name  登录中获取的用户名
         * @return: java.util.Map<java.lang.String,java.lang.Object>
         * @author: 陆均琪
         * @time: 2019-12-04 22:30
         */

        String sql = "select  *from admin where name = ?";
        Map<String, Object> map = template.queryForMap(sql, name);
        return map;
    }

    public static List<Admin> getAdmin(){
        /**
         * @description: 查询全部的管理员
         * @param
         * @return: java.util.List<com.ludashen.hothl.Admin>
         * @author: 陆均琪
         * @time: 2019-12-04 22:30
         */
        String sql="select *from admin";

        List<Admin> list = template.query(sql, (resultSet, i) -> {
            Admin admin=new Admin(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5));
            return admin;
        });
        return list;
    }

    public static boolean udAdmin(Admin admin) {
        /**
         * @description:  通过id更新管理员的数据，更新成功返回true
         * @param admin  管理员实体类
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:30
         */

        String sql = " UPDATE admin SET `name` = ? , `password` = ? , `remarks` =? , `power` = ? WHERE `id` = ?;  ";
        int count = template.update(sql,admin.getName(),admin.getPassWord(),admin.getRemarks(),admin.getPower(),admin.getId());
        return Tool.isDate(count);
    }

    public static boolean addAdmin(Admin admin) {
        /**
         * @description:   插入新的管理员
         * @param admin 管理员实体类
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:30
         */

        String sql="INSERT INTO admin (`name`, `password`, `remarks`,`power`) VALUES (?, ?, ?, ?); ";
        try {
            int total =template.update(sql,admin.getName(),admin.getPassWord(),admin.getRemarks(),admin.getPower());
            return  Tool.isDate(total);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean aDelete(String name) {
        /**
         * @description:  根据管理员名字删除数据
         * @param name  管理员名字
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:31
         */

        String sql = "DELETE FROM admin WHERE `name` = ?; ";
        int count = template.update(sql,name);
        return Tool.isDate(count);
    }

    public static  Boolean getAdminLogin(String name,String password){
        /**
         * @description: 用于判断密码是否正确 找不到数据时会报错所有返回false
         * @param name：用户名
         * @param password：用户密码
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-02 22:41
         */
        String sql = "select *from admin where name = ? and password=?";
        try {
            Map<String, Object> count = template.queryForMap(sql, name,password);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
