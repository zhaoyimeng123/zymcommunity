package com.zym.community.controller;

import com.zym.community.mapper.UserMapper;
import com.zym.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date 2020/3/14-19:52
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/")
    public String toIndexPage(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length>0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
