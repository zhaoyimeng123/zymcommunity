package com.zym.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author
 * @date 2020/3/14-19:52
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String toIndexPage(){
        return "index";
    }
}
