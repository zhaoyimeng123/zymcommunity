package com.zym.community.dto;

import lombok.Data;

/**
 * @author
 * @date 2020/3/14-22:10
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
    
}
