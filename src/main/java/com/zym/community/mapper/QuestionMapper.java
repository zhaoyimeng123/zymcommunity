package com.zym.community.mapper;

import com.zym.community.dto.QuestionDTO;
import com.zym.community.model.Question;
import com.zym.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @date 2020/3/15-13:57
 */
@Mapper
@Component
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void create(Question question);

    @Select("select * from question order by gmt_create desc limit #{offset},#{size}")
    List<Question> findAllQuestion(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId}")
    List<Question> findAllQuestionByUserId(long userId);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(long id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id = #{id}")
    void update(Question question);

    @Update("update question set view_count = (view_count+1) where id = #{id}")
    void incViewByQuesId(long id);

    //修改问题的评论数
    @Update("update question set comment_count = (comment_count+1) where id = #{id}")
    int updateQuestionComment(long id);

    //通过question的creator查询对应的用户
    @Select("select * from question where creator = #{creator}")
    User findByCreator(long creator);

    //通过问题的标签查询到相似的问题
    @Select("select * from question where tag regexp #{tags}")
    List<Question> selectLikeQuestionByTag(@Param("tags") String tags);
}
