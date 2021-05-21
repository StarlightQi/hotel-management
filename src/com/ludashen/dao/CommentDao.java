package com.ludashen.dao;

import com.ludashen.control.Tool;
import com.ludashen.hothl.Comment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @description:
 * @author: 陆均琪
 * @Data: 2019-12-07 16:37
 */
public class CommentDao {
    private static JdbcTemplate template = new JdbcTemplate(DaoConnection.getDataSource());


    public static List<Comment> getComment(){
        /**
         * @description: 获取全部评论
         * @return: java.util.List<com.ludashen.hothl.House>
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */
        String sql="SELECT * FROM comment";
        List<Comment> list = template.query(sql, (resultSet, i) -> {
            Comment comment=new Comment(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5));
            return comment;
        });

        return list;
    }

    public static List<Comment> getRoomComment(String hid,int sq){
        /**
         * @description: 获取用户的评论
         * @return: java.util.List<com.ludashen.hothl.House>
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */
        String sql="";
        if (sq==1)
            sql="SELECT * FROM comment where hid= ?";
        if(sq==2)
            sql="SELECT * FROM comment where uid= ?";

        List<Comment> list = template.query(sql, (resultSet, i) -> {
            Comment comment=new Comment(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDate(5));
            return comment;
        },hid);

        return list;
    }

    public static boolean cDelete(String id) {
        /**
         * @description:  通过评论id删除评论
         * @param name  评论id
         * @return: boolean
         * @author: 陆均琪
         * @time: 2019-12-04 22:38
         */
        String sql = "DELETE FROM comment WHERE  id =?;  ";
        int count = template.update(sql,id);
        return Tool.isDate(count);
    }

    public static boolean setCommentDao(Comment comment){

        String sql="INSERT INTO comment (`uid`, `hid`, `comment`) VALUES (?, ?, ?); ";
        try {
            int total =template.update(sql, comment.getUid(),comment.getHid(),comment.getComment());
            return  Tool.isDate(total);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
