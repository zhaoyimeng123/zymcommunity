package com.zym.community.model;

import lombok.Data;

/**
 * @author
 * @date 2020/3/16-19:18
 * 评论表
 */
@Data
public class Comment {
    private long id;
    private long parentId;//父类id
    private int type;//父类类型
    private long commentId;//评论人id
    private long gmtCreate;//创建时间
    private long gmtModified;//修改时间
    private long likeCount;//点赞数
    private String content;//评论内容
    private long commentCount;//这条评论的评论数,即二级评论数
}
