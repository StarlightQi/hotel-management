package com.ludashen.dao;

import com.ludashen.control.Tool;
import com.ludashen.hothl.Users;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @description:   客户表的操作
 * @author: 陆均琪
 * @Data: 2019-12-04 15:54
 */
public class UserDao {
    private static JdbcTemplate template = new JdbcTemplate(DaoConnection.getDataSource());

    public static  Boolean getUser(String name,String password){
        /**
         * @description: 用于判断密码是否正确
             * @param name：用户名
         * @param password：用户密码
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-02 22:41
         */

        String sql = "select *from user where uid = ? and uPassword=?";
        try {
            Map<String, Object> count = template.queryForMap(sql, name,password);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static Map<String, Object> users(String uid){
        /**
         * @description: 根据用户id搜索对应用户消息
         * @param uid  用户id
         * @return: java.util.Map<java.lang.String,java.lang.Object>
         * @author: 陆均琪
         * @time: 2019-12-04 22:56
         */


        String sql = "select  *from user where uid = ?";
        Map<String, Object> map = template.queryForMap(sql, uid);
        return map;
    }

    public static Boolean register(Users users){
        /**
         * @description:   插入新的数据(注册时用)
         * @param users 用户
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:56
         */

        String sql="insert into user(uid,uName,uPassword,uPhone,uBirthday,uSex) values(?,?,?,?,?,?)";
        try {
            int total =template.update(sql, users.getuId(),users.getuName(),users.getuPassword(),users.getuPhone(),users.getuBirthday(),users.getUsex());
            return Tool.isDate(total);
        }catch (Exception e){
            return false;
        }
    }

    public static List<Users> getAllUsers() {
        /**
         * @description: 搜索全部用户
         * @param
         * @return: java.util.List<com.ludashen.hothl.Users>
         * @author: 陆均琪
         * @time: 2019-12-04 23:01
         */

        String sql = "select *from user";
        List<Users> list = template.query(sql, (resultSet, i) -> user(resultSet));
        return list;
    }
    public static List<Users> findUser(String name){
        name="%"+name+"%";
        String sql="SELECT *FROM user WHERE uName LIKE ?";
        List<Users> list = template.query(sql, (resultSet, i) -> user(resultSet),name);
        return list;
    }

    private static Users user(ResultSet resultSet) throws SQLException {
        Users users = new Users(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getDate(5),
                resultSet.getBoolean(6),resultSet.getString(7),
                resultSet.getInt(8));
        return users;
    }


    public static Boolean modify(Users users){
        /**
         * @description: 根据用户id 修改用户
         * @param users
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 23:02
         */
        String sql = "UPDATE user SET uName = ? , uPassword = ? , uPhone = ? , uBirthday = ? , uSex =? ,head=? WHERE uid = ?;  ";
        int count = template.update(sql,users.getuName(),users.getuPassword(),users.getuPhone(),users.getuBirthday(),users.getUsex(),users.getHead(),users.getuId());
        return Tool.isDate(count);
    }

    public static Boolean delete(String id){
        /**
         * @description: 根据用户id删除数据
         * @param id    用户id
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 23:03
         */

        String sql="DELETE FROM user WHERE uid = ?; ";
        int count = template.update(sql,id);
        return Tool.isDate(count);
    }

    public static Boolean setMoney(String id,float money){
        /**
         * @description: 修改金额
         * @param users
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 23:02
         */

        String sql = "UPDATE user SET money = ?  WHERE uid = ?;  ";
        int count = template.update(sql,money,id);
        return Tool.isDate(count);
    }

    public static  String head(String name){
        /**
         * @description: 用于搜索用户头像
         * @param name：用户名
         * @return: java.lang.String
         * @author: 陆均琪
         * @time: 2019-12-02 22:41
         */

        String sql = "select head from user where uid = ?";
        try {
            String h = template.queryForObject(sql, String.class,name);
            return h;
        }catch (Exception e){
            return "hear.png";
        }

    }
    public static int count(){
        String sql = "select count(*) from user";
        int total = template.queryForObject(sql, int.class);
        return total;
    }

    public static void main(String[] args) {
        System.out.println(count());
    }
    }

