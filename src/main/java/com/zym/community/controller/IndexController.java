package com.zym.community.controller;
import com.zym.community.dto.PaginationDTO;
import com.zym.community.dto.QuestionDTO;
import com.zym.community.mapper.QuestionMapper;
import com.zym.community.mapper.UserMapper;
import com.zym.community.model.Question;
import com.zym.community.model.User;
import com.zym.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author
 * @date 2020/3/14-19:52
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String toIndexPage(HttpServletRequest request,
                              Model model,
                              @RequestParam(name = "page",defaultValue = "1")Integer page,
                              @RequestParam(name = "size",defaultValue = "2")Integer size){
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
        PaginationDTO pagination = questionService.findAllQuestion(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }


}
