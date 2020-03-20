package com.zym.community.dto;

import com.zym.community.model.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2020/3/16-19:29
 */
@Data
public class CommentWithCommentNameAndAvatorAndCommentDTO {
    private long id;
    private Long parentId;
    private String content;
    private Integer type;
    private String avatarUrl;
    private String commentName;
    private Comment comment;
    private long gmtCreate;
    private int commentCount;//二级评论的条数



}
