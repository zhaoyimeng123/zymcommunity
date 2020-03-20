package com.zym.community.service;

import com.zym.community.enums.NotificationEnum;
import com.zym.community.enums.NotificationStatusEnum;
import com.zym.community.mapper.CommentMapper;
import com.zym.community.mapper.NotificationMapper;
import com.zym.community.mapper.QuestionMapper;
import com.zym.community.model.Comment;
import com.zym.community.model.Notification;
import com.zym.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @date 2020/3/16-20:11
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;


    @Transactional
    public int insert(Comment comment) {
        if (comment.getType() == 2) {
            //回复评论
            commentMapper.insert(comment);

        } else if (comment.getType() == 1) {
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            commentMapper.insert(comment);
            questionMapper.updateQuestionComment(comment.getParentId());
        }
        return 0;
    }
}
