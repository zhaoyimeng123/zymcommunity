package com.zym.community.dto;

import lombok.Data;

/**
 * @author
 * @date 2020/3/16-19:29
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
