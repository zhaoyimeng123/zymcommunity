package com.zym.community.model;

import lombok.Data;

/**
 * @author
 * @date 2020/3/15-9:26
 */
@Data
public class User {
    private long id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
