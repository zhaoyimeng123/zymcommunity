package com.zym.community.dto;

import lombok.Data;

/**
 * @author
 * @date 2020/3/14-21:44
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
