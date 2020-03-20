package com.zym.community.controller;

import com.zym.community.dto.CommentDTO;
import com.zym.community.dto.CommentWithCommentNameAndAvatorAndCommentDTO;
import com.zym.community.dto.ResultDTO;
import com.zym.community.enums.NotificationEnum;
import com.zym.community.enums.NotificationStatusEnum;
import com.zym.community.mapper.CommentMapper;
import com.zym.community.mapper.NotificationMapper;
import com.zym.community.mapper.QuestionMapper;
import com.zym.community.mapper.UserMapper;
import com.zym.community.model.Comment;
import com.zym.community.model.Notification;
import com.zym.community.model.User;
import com.zym.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/16-19:22

 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request,
                       Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(2002,"未登陆,不能进行评论,请登陆");
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentId(user.getId());
        comment.setLikeCount(0);


        if (comment.getContent().equals("")){
            return new ResultDTO(2007,"输入内容不能为空");
        }

        int row = commentService.insert(comment);

        //设置通知notification,评论问题的通知
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(NotificationEnum.REPLY_QUESTION.getType());
        notification.setOuterId(comment.getParentId());
        notification.setNotifier(user.getId());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(comment.getCommentId());

        notificationMapper.insert(notification);
        //System.out.println(row+"---------------------");
        if (row==0){
            return new ResultDTO(200,"评论成功");
        }else {
            return new ResultDTO(400,"评论出错");
        }
    }


    /**
     *添加二级评论
     */
    @RequestMapping(value = "/subcomment/{id}",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO comments(@PathVariable("id")long id,
                              HttpServletRequest request, Model model,
                              @RequestBody CommentDTO commentDTO){
        String subContent = commentDTO.getContent();
        //long id = commentDTO.getParentId();
        System.out.println("id:"+id+","+"subContent:"+subContent);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(2002,"未登陆,不能进行评论,请登陆");
        }
        if (StringUtils.isEmpty(subContent)){
            return ResultDTO.errorOf(2007,"评论不能为空");
        }
        Comment comment = new Comment();
        comment.setCommentId(user.getId());
        comment.setParentId(id);
        comment.setType(2);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setContent(subContent);
        commentService.insert(comment);

        //设置通知notification,评论评论的通知
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(NotificationEnum.REPLY_COMMENT.getType());
        notification.setOuterId(comment.getParentId());
        notification.setNotifier(user.getId());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(comment.getCommentId());

        notificationMapper.insert(notification);


        //拿到这个评论下的所有评论
        List<Comment> comments = commentMapper.selectByParentId(id);
        List<CommentWithCommentNameAndAvatorAndCommentDTO> subCommentResult = new ArrayList<>();
        for (Comment comment1 : comments) {
            CommentWithCommentNameAndAvatorAndCommentDTO commentResultDTO = new CommentWithCommentNameAndAvatorAndCommentDTO();
            BeanUtils.copyProperties(comment1,commentResultDTO);
            commentResultDTO.setAvatarUrl(user.getAvatarUrl());
            commentResultDTO.setCommentName(user.getName());
            commentResultDTO.setComment(comment1);
            commentResultDTO.setContent(subContent);
            subCommentResult.add(commentResultDTO);
        }
        //model.addAttribute("subCommentResult",subCommentResult);
        return ResultDTO.okOfSubComment(subCommentResult);
    }


    @RequestMapping(value = "/getsubcomment/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO getComments(@PathVariable("id")long id,
                                 HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        //拿到这个评论下的所有评论
        List<Comment> comments = commentMapper.selectByParentId(id);
        List<CommentWithCommentNameAndAvatorAndCommentDTO> subCommentResult = new ArrayList<>();
        for (Comment comment1 : comments) {
            CommentWithCommentNameAndAvatorAndCommentDTO commentResultDTO = new CommentWithCommentNameAndAvatorAndCommentDTO();
            BeanUtils.copyProperties(comment1,commentResultDTO);
            commentResultDTO.setAvatarUrl(user.getAvatarUrl());
            commentResultDTO.setCommentName(user.getName());
            commentResultDTO.setComment(comment1);
            subCommentResult.add(commentResultDTO);
        }
        return ResultDTO.okOfSubComment(subCommentResult);
    }

}
