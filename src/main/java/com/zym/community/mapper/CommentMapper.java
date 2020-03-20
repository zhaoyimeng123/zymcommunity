package com.zym.community.mapper;

import com.zym.community.model.Comment;
import com.zym.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @date 2020/3/16-19:21
 * 评论CommentMapper
 */
@Mapper
@Component
public interface CommentMapper {


    @Select("select*from user where id = #{id}")
    User findUserByCommentId(long id);

    @Insert("insert into comment(parent_id,type,comment_id,gmt_create,gmt_modified,content) values(#{parentId},#{type},#{commentId},#{gmtCreate},#{gmtModified},#{content})")
    int insert(Comment comment);


    @Select("select * from comment where parent_id = #{parentId} order by gmt_create desc")
    List<Comment> selectByParentId(long parentId);

    /**
     * 查询这条评论有多少二级评论
     */
    @Select("select count(1) from comment where parent_id = #{parentId}")
    int countContentById(long parentId);

    //通过评论的id查询这条评论
    @Select("select * from comment where id = #{id}")
    Comment findById(long id);
}
