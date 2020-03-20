package com.zym.community.dto;

import com.zym.community.model.Comment;
import com.zym.community.model.Question;
import com.zym.community.model.User;
import lombok.Data;

/**
 * 这是通知表,显示通知信息
 * @author
 * @date 2020/3/19-21:24
 */
@Data
public class NotificationDTO {
    private long id;
    private long notifier;//发送通知的人
    private long receiver;//接收通知的人
    private long outerId;
    private int type;//通知类型, 1-回复问题  2-回复评论
    private long gmtCreate;//创建时间
    private int status;//通知状态,
    private User notifierName;//发送通知的人姓名
    private Question question;//做出通知对应的问题
    private Comment comment;//做出通知对应的评论

}
