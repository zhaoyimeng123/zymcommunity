package com.zym.community.service;

import com.zym.community.dto.NotificationDTO;
import com.zym.community.mapper.CommentMapper;
import com.zym.community.mapper.NotificationMapper;
import com.zym.community.mapper.QuestionMapper;
import com.zym.community.mapper.UserMapper;
import com.zym.community.model.Comment;
import com.zym.community.model.Notification;
import com.zym.community.model.Question;
import com.zym.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/19-22:16
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;

    public List<NotificationDTO> getNotificationMsg(User user){
        List<NotificationDTO> notifications = new ArrayList<>();
        long userId = user.getId();
        //通过userId对应到notification的receiver,拿到所有的通知条数,把以拿到了的通知的状态设置为已读
        List<Notification> allNotification = notificationMapper.selectAllNotification(user);
        notificationMapper.setRead(user);
        //通过userId对应到notification的receiver,拿到所有对当前receiver做出评论的notification
        for (Notification notification : allNotification) {
            NotificationDTO notificationRet = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationRet);
            //通过notifier,拿到做出评论的user.name
            long notifier = notification.getNotifier();
            User userMapperById = userMapper.findById(notifier);
            notificationRet.setNotifierName(user);
            if (notification.getType()==1){
                //通过outer_id,拿到对应做出评论问题的title
                Question questionMapperById = questionMapper.getById(notification.getOuterId());
                notificationRet.setQuestion(questionMapperById);
            }else if (notification.getType()==2){
                Comment commentMapperById = commentMapper.findById(notification.getOuterId());
                notificationRet.setComment(commentMapperById);

            }
            notifications.add(notificationRet);
        }
        return notifications;
    }
}
