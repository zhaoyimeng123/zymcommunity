package com.zym.community.controller;

import com.zym.community.dto.QuestionDTO;
import com.zym.community.mapper.QuestionMapper;
import com.zym.community.model.Question;
import com.zym.community.model.User;
import com.zym.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date 2020/3/15-10:49
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description")String description,
                            @RequestParam("tag")String tag,
                            HttpServletRequest request,
                            @RequestParam(value = "id",required = false)Integer id,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if (description==null || description==""){
            model.addAttribute("error","问题补充不能为空");
            return "/publish";
        }
        if (tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","请登陆后再发布问题");
            return "publish";
        }
        question.setCreator(user.getId());

        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
