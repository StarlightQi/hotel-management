package com.ludashen.dao;

import com.ludashen.control.Tool;
import com.ludashen.hothl.RoomInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @description:    客房的更多服务信息操作
 * @author: 陆均琪
 * @Data: 2019-12-04 15:54
 */
public class RoomInfoDao {
    private static JdbcTemplate template = new JdbcTemplate(DaoConnection.getDataSource());

    public static List<RoomInfo> getRoomInfo(int id,int sq){
        /**
         * @description:   根据房子名字查询全部的房子信息 ---这个的名字对应客房表的id
         * @param id    客户方的id
         * @return: java.util.List<com.ludashen.hothl.RoomInfo>
         * @author: 陆均琪
         * @time: 2019-12-04 22:51
         */
        String sql="";
        if(sq==1)
             sql= "SELECT *FROM roominfo WHERE hnames=? and name!='image'";
        if(sq==2)
            sql="SELECT *FROM roominfo WHERE hnames=?";
        if(sq==3)
            sql="SELECT *FROM roominfo WHERE hnames=? and name ='image'";
        List<RoomInfo> list = template.query(sql, (resultSet, i) -> {
            RoomInfo roomInfo=new RoomInfo(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3));
            return roomInfo;
        },id);
        return list;
    }

    public static Boolean udRoomInfo(RoomInfo roomInfo){
        /**
         * @description:    根据信息表id，更改信息表的消息
         * @param roomInfo
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:51
         */

        String sql = "UPDATE roominfo SET name =?, funtion =?  WHERE `id` = ?; ";
        int count = template.update(sql,roomInfo.getName(),roomInfo.getFuntion(),roomInfo.getId());
        return Tool.isDate(count);
    }

    public static Boolean addRoomInfo(RoomInfo info){
        /**
         * @description: 插入新的消息数据
         * @param info
         * @return: java.lang.Boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:51
         */

        String sql="INSERT INTO roominfo (`name`, `funtion`, `hnames`) VALUES (?, ?, ?); ";
        try {
            int total =template.update(sql,info.getName(),info.getFuntion(),info.getId());
            return  Tool.isDate(total);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rDelete(int id) {
        /**
         * @description:   根据id删除信息表数据
         * @param id
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:51
         */

        String sql = " DELETE FROM roominfo WHERE `id` = ?; ";
        int count = template.update(sql,id);
        return Tool.isDate(count);
    }
}
