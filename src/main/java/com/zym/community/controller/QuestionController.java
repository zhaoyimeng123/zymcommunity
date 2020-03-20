package com.zym.community.controller;

import com.zym.community.dto.CommentWithCommentNameAndAvatorAndCommentDTO;
import com.zym.community.dto.QuestionDTO;
import com.zym.community.mapper.CommentMapper;
import com.zym.community.mapper.QuestionMapper;
import com.zym.community.model.Comment;
import com.zym.community.model.Question;
import com.zym.community.model.User;
import com.zym.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/16-10:00
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;


    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        //累加阅读数
        questionService.incView(id);
        //通过问题id查询出相应的评论
        List<Comment> comments = commentMapper.selectByParentId(id);
        List<CommentWithCommentNameAndAvatorAndCommentDTO> commentWithCommentNameAndAvatorAndCommentDTO = new ArrayList<>();

        for (Comment comment : comments) {
            CommentWithCommentNameAndAvatorAndCommentDTO commentResult = new CommentWithCommentNameAndAvatorAndCommentDTO();
            BeanUtils.copyProperties(comment,commentWithCommentNameAndAvatorAndCommentDTO);
            //拿到每一条评论对应的评论者,将评论者的name和avatarUrl设置到commentWithCommentNameAndAvatorAndCommentDTO里面,这样在页面好展示
            User commentUser = commentMapper.findUserByCommentId(comment.getCommentId());
            commentResult.setAvatarUrl(commentUser.getAvatarUrl());
            commentResult.setCommentName(commentUser.getName());
            commentResult.setContent(comment.getContent());
            commentResult.setGmtCreate(comment.getGmtCreate());
            commentResult.setId(comment.getId());
            //拿到这条评论所有的二级评论
            int i = commentMapper.countContentById(comment.getId());
            commentResult.setCommentCount(i);
            commentWithCommentNameAndAvatorAndCommentDTO.add(commentResult);

        }

        //通过问题tag查询类型相似的问题
        String tags = StringUtils.replace(questionDTO.getTag(),",","|");
        tags = "'"+tags+"'";

        Question question = new Question();
        question.setId(questionDTO.getId());
        //System.out.println(question.getId());
        question.setTag(tags);
        System.out.println(question.getTag());
        List<Question> questionList = questionMapper.selectLikeQuestionByTag(tags);
        System.out.println(questionList);
        model.addAttribute("likeQuestions",questionList);
        model.addAttribute("commentResult",commentWithCommentNameAndAvatorAndCommentDTO);
        return "question";
    }

}
