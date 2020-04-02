package cn.fzkj.community.controller;

import cn.fzkj.community.domain.Notification;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.NotifyDTO;
import cn.fzkj.community.dto.PageBean;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.service.Impl.NotificationService;
import cn.fzkj.community.service.QuestionService;
import cn.fzkj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          Model model){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            //还没有登陆,跳转到登录页面
            return "redirect:/toLogin.action";
        }
        else if("questions".equals(action)){
            // 查出来我的问题
            PageBean<QuestionDTO> pageBean = questionService.findUserQuestions(user.getId(), page);
            model.addAttribute("pageBean",pageBean);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else{
            //我的回复
            // 查找回复
            PageBean<NotifyDTO> pageBean = notificationService.list(user.getId(), page);
            model.addAttribute("pageBeans",pageBean);
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","我的回复");
        }
        return "profile";
    }
}
