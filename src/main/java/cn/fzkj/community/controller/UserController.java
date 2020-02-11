package cn.fzkj.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/toLogin.action")
    public String toLogin(){
        return "login";
    }
}
