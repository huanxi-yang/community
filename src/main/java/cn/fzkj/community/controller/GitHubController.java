package cn.fzkj.community.controller;

import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.AccessTokenDTO;
import cn.fzkj.community.dto.GitHubUser;
import cn.fzkj.community.provider.GitHubProvider;
import cn.fzkj.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Controller
public class GitHubController {

    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private UserService userService;

    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.redirect_uri}")
    private String redirect_uri;

    @Value("${github.client_secret}")
    private String client_secret;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(client_secret);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if(gitHubUser!=null){
            //保存登录状态到session
            User user = new User();
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            //判断数据库中是否已经存在该用户
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token)); //创建一个Cookie
            return "redirect:/";
        }else{
            log.error("callback error for github login!,{}",gitHubUser);
            return "redirect:/";
        }
    }
}
