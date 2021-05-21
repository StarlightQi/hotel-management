package com.ludashen.dao;

import com.ludashen.control.Tool;
import com.ludashen.hothl.House;
import com.ludashen.hothl.Reservation;
import com.ludashen.hothl.Users;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 订单数据库操作类
 * @author: 陆均琪
 * @Data: 2019-12-06 8:43
 */
public class ReservationDao {
    private static JdbcTemplate template = new JdbcTemplate(DaoConnection.getDataSource());
        public static List<Reservation> getAllHouse(){
        /**
         * @description:    封装房子信息和客户信息，一对多，多表查询，根据外键搜索订房的客户信息
         * @param
         * @return: java.util.List<com.ludashen.hothl.House>
         * @author: 陆均琪
         * @time: 2019-12-04 22:39
         */

        String sql="SELECT *FROM ding INNER JOIN house ON ding.`hid`=house.`hid` INNER JOIN USER ON user.`uid`=ding.`uid`; ";
        List<Reservation> list = template.query(sql, (resultSet, i) -> {
            return packReser(resultSet);
        });
        return list;
    }
    public static List<Reservation> getUserRoom(String uid){
        /**
         * @description:    封装房子信息和客户信息，一对多，多表查询，根据外键搜索订房的客户信息
         * @param
         * @return: java.util.List<com.ludashen.hothl.House>
         * @author: 陆均琪
         * @time: 2019-12-04 22:39
         */

        String sql="SELECT *FROM ding INNER JOIN house ON ding.`hid`=house.`hid` INNER JOIN USER ON user.`uid`=ding.`uid` and user.`uid`=?; ";
        List<Reservation> list = template.query(sql, (resultSet, i) -> {
            return packReser(resultSet);
        },uid);
        return list;
    }

    private static Reservation packReser(ResultSet resultSet) throws SQLException {
        Users users=new Users(resultSet.getString(11),resultSet.getString(12),resultSet.getString(13),
                resultSet.getString(14), resultSet.getDate(15),resultSet.getBoolean(16),
                resultSet.getString(17),resultSet.getInt(18));
        House house=new House(resultSet.getInt(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getInt(10));
        Reservation reservation=new Reservation(house,users,resultSet.getTimestamp(4),resultSet.getDate(5));
        return reservation;
    }

    public static Boolean reserve(int hid, String uid,String dDate){
        /**
         * @description: 客户订房
         * @param hName     房子名字
         * @param uid   用户房间
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */

        String sql = "INSERT INTO ding (`hid`, `uid`, `dday`) VALUES (?, ?, ?); ";
        int count = template.update(sql,hid,uid,dDate);
        return Tool.isDate(count);
    }

    public static boolean dDelete(int hid) {
        /**
         * @description:    通过客房名字删除客房信息
         * @param name  客房名字
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */
        String sql = "DELETE FROM ding WHERE `hid` = ?; ";
        int count = template.update(sql,hid);
        System.out.println(hid);
        return Tool.isDate(count);
    }
    public static boolean getUser(String uid){
            /**
             * @description:
                 * @param uid 一个用户只能定一件
             * @return: boolean
             * @author: 陆均琪
             * @time: 2019-12-27 15:57
             */

        String sql = "select *from ding where uid = ?";
        try {
            Map<String, Object> count = template.queryForMap(sql,uid);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    public static int count(){
        String sql = "select count(*) from ding";
        int total = template.queryForObject(sql, int.class);
        return total;
    }
    public static void main(String[] args) {
        System.out.println(count());
    }
}
