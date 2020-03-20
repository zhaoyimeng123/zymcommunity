package com.zym.community.model;

import lombok.Data;

/**
 * 这是通知表,显示通知信息
 * @author
 * @date 2020/3/19-21:24
 */
@Data
public class Notification {
    private long id;
    private long notifier;//发送通知的人
    private long receiver;//接收通知的人
    private long outerId;
    private int type;//通知类型, 1-回复问题  2-回复评论
    private long gmtCreate;//创建时间
    private int status;//通知状态,

}
