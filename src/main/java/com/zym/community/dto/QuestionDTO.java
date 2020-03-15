package com.zym.community.dto;

import com.zym.community.model.User;
import lombok.Data;

/**
 * @author
 * @date 2020/3/15-16:15
 */
@Data
public class QuestionDTO {

    private Integer id;
    private String title;
    private String tag;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}
