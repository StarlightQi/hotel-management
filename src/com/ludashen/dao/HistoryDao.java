package com.ludashen.dao;

import com.ludashen.control.Tool;
import com.ludashen.hothl.History;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-06 17:18
 */
public class HistoryDao {
    private static JdbcTemplate template = new JdbcTemplate(DaoConnection.getDataSource());

    public static Boolean history(int hid){
        /**
         * @description: 退房记录---数据库语句的作用是把查找出来的数据安装对应的格式存储到新的表里边
         * @param hid     房子id
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */
        String sql = "INSERT INTO chargeback(id,hid,uid,dtime,ttime)SELECT id,hid,uid,sday,dday FROM ding WHERE hid=?; ";
        int count = template.update(sql,hid);
        return Tool.isDate(count);
    }

    public static boolean udHistory(History hid) {
        /**
         * @description:  更新数据退费语音登
         * @param admin  管理员实体类
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:30
         */

        Date date = new Date();
        SimpleDateFormat nowTime = new SimpleDateFormat("yyyy-MM-dd%HH%");
        String sql = "UPDATE chargeback SET `result` = ? , `reason` = ? , `deduct` = ? WHERE hid=? AND uid=? AND ctime LIKE ?";
        int count = template.update(sql,hid.getResult(),hid.getReason(),hid.getDeduct(),hid.getHid(),hid.getUdi(),nowTime.format(date));
        return Tool.isDate(count);
    }

    public static List<History> getAllHistory(){
        String sql="SELECT * FROM chargeback";
        List<History> list = template.query(sql, (resultSet, i) -> {
            return packReser(resultSet);
        });
        return list;
    }
    public static List<History> getUserHistory(String uid){
        String sql="SELECT * FROM chargeback where uid=?";
        List<History> list = template.query(sql, (resultSet, i) -> packReser(resultSet),uid);
        return list;
    }

    public static List<Map<String, Object>> count(){
        /**
         * @description: 查询5天内每一天的订单数量
         * @param
         * @return: java.util.List<java.lang.Integer>
         * @author: 陆均琪
         * @time: 2019-12-08 18:00
         */
        String sql="SELECT b.t,COUNT(*) c FROM(SELECT *, DATE_FORMAT(ctime,'%Y-%m-%d') AS t , CURDATE() AS n, DATE_SUB(NOW(), INTERVAL 5 DAY)AS bf FROM chargeback HAVING t BETWEEN bf AND n)AS b GROUP BY b.t";
        List<Map<String, Object>> list = template.queryForList(sql);
        return list;
    }
    public static List<Map<String, Object>> count2(){
        /**
         * @description: 查询5天内每一天的订单数量
         * @param
         * @return: java.util.List<java.lang.Integer>
         * @author: 陆均琪
         * @time: 2019-12-08 18:00
         */
        String sql="SELECT b.t ,GROUP_CONCAT(result) x FROM(SELECT *, DATE_FORMAT(ctime,'%Y-%m-%d') AS t , CURDATE() AS n, DATE_SUB(NOW(), INTERVAL 5 DAY)AS bf FROM chargeback HAVING t BETWEEN bf AND n)AS b GROUP BY b.t";
        List<Map<String, Object>> list = template.queryForList(sql);
        return list;
    }


    private static History packReser(ResultSet rs) throws SQLException {
        History history=new History(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getTimestamp(4),rs.getDate(5),
                rs.getTimestamp(6),rs.getBoolean(7),rs.getString(8),rs.getInt(9));
        return history;
    }

    public static Boolean clear() {
        /**
         * @description: 清空表格---删除表格后重新创
         * @param
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-07 21:52
         */
        String sql = "truncate table chargeback";
        int count = template.update(sql);
        System.out.println(count);
        return Tool.isDate(count);

    }

    public static void main(String[] args) {

        for (Map<String, Object> stringObjectMap : count2()) {
            String g=(String) stringObjectMap.get("x");
            String[] split = g.split(",");
            int t=0;
            int f=0;
            for (String s:split){
                if(s.equals("1"))
                    t++;
                else
                    f++;
            }
            System.out.println(stringObjectMap);
            System.out.println(t);
            System.out.println(f);
        }

    }
}
