package cn.fzkj.community.controller;

import cn.fzkj.community.domain.Question;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.PageBean;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.mapper.UserMapper;
import cn.fzkj.community.service.QuestionService;
import cn.fzkj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page){
        Cookie[] cookies = request.getCookies();
        if(cookies.length!=0 && cookies!=null){
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userService.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        PageBean<QuestionDTO> pageBean = questionService.questionList(page);
        model.addAttribute("pageBean",pageBean);
        return "index";
    }

    @RequestMapping("/toLogin.action")
    public String toLogin(){
        return "login";
    }

}
