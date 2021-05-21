package com.ludashen.dao;

import com.ludashen.control.Tool;
import com.ludashen.hothl.House;

import com.ludashen.hothl.Users;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @description:  酒店房子数据库的操作
 * @author: 陆均琪
 * @Data: 2019-12-03 13:38
 */

public class HouseDao {
    private static JdbcTemplate template = new JdbcTemplate(DaoConnection.getDataSource());

    public static List<House> getHouser(int sq){
        /**
         * @description: 获取酒店房子
         * @param sq   1 获取全部的房子 2获取全部空房  3获取全部已经预约的房子
         * @return: java.util.List<com.ludashen.hothl.House>
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */

        String sql="";
        if(sq==1)
            sql="select *from house";
        if(sq==2)
            sql="SELECT house.`hid`,house.`hname`,house.`hdetails`,house.`himg`,house.`hprice`,ding.`hid` FROM house LEFT JOIN ding ON house.`hid`=ding.`hid` HAVING ding.`hid` IS NULL; ";// -- 查找空房
        if (sq==3)
            sql="SELECT house.`hid`,house.`hname`,house.`hdetails`,house.`himg`,house.`hprice` FROM ding INNER JOIN house ON ding.`hid`=house.`hid` ;";//查找已经定的房子

        List<House> list = template.query(sql, (resultSet, i) -> houses(resultSet));
        return list;
    }

    public static  List<House> findHouse(String name){
        name="%"+name+"%";
        String sql="SELECT *FROM house WHERE hname LIKE ?";
        List<House> list = template.query(sql, (resultSet, i) -> houses(resultSet),name);
        return list;
    }
    private static House houses(ResultSet resultSet) throws SQLException {
        House house=new House(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5));
        return house;
    }



    private void houses(){


    }

    public static Boolean modify(House house){
        /**
         * @description: 更新酒店客房信息
         * @param house House实体类
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */

        String sql = "UPDATE house SET hname = ? , hdetails = ? , himg = ? , hprice = ? WHERE hid =?; ";
        int count = template.update(sql,house.gethName(),house.gethDetails(),house.gethImg(),house.gethPrice(),house.getHid());
        return Tool.isDate(count);
    }

    public static Boolean addHouse(House house){
        /**
         * @description:   添加新的客房
         * @param house
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */

        String sql="INSERT INTO house (`hname`, `hdetails`, `himg`, `hprice`) VALUES (?, ?, ?, ?); ";
        try {
            int total =template.update(sql, house.gethName(),house.gethDetails(),house.gethImg(),house.gethPrice());
            return  Tool.isDate(total);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delect(String name) {
        /**
         * @description:    通过客房名字删除客房信息
         * @param name  客房名字
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */

        String sql = "DELETE FROM house WHERE  hname =?;  ";
        int count = template.update(sql,name);
        return Tool.isDate(count);
    }

    public static void main(String[] args) {
        findHouse("2");
//        getHouser(1);
    }
}
