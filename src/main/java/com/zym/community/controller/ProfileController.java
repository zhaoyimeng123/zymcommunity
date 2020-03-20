package com.zym.community.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zym.community.dto.NotificationDTO;
import com.zym.community.dto.PaginationDTO;
import com.zym.community.mapper.QuestionMapper;
import com.zym.community.mapper.UserMapper;
import com.zym.community.model.Question;
import com.zym.community.model.User;
import com.zym.community.service.NotificationService;
import com.zym.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author
 * @date 2020/3/15-21:20
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action",value = "")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size){


        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
            PageHelper.startPage(page,size);
            List<Question> allQuestionByUserId = questionMapper.findAllQuestionByUserId(user.getId());
            PageInfo<Question> questionPageInfo = new PageInfo<>(allQuestionByUserId,5);
            model.addAttribute("questionPageInfo",questionPageInfo);

        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            List<NotificationDTO> notificationMsg = notificationService.getNotificationMsg(user);
            int notificationCount = notificationMsg.size();
            model.addAttribute("notificationCount",notificationCount);
            model.addAttribute("notifications",notificationMsg);

        }

        return "profile";
    }
}
