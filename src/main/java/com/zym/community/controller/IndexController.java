package com.zym.community.controller;

import com.zym.community.dto.PaginationDTO;
import com.zym.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date 2020/3/14-19:52
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String toIndexPage(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {

        PaginationDTO pagination = questionService.findAllQuestion(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }


}
