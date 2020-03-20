package com.zym.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date 2020/3/15-14:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private long id;
    private String title;
    private String tag;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private long creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;




}
