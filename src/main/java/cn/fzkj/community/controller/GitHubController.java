package cn.fzkj.community.controller;

import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.AccessTokenDTO;
import cn.fzkj.community.dto.GitHubUser;
import cn.fzkj.community.provider.GitHubProvider;
import cn.fzkj.community.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class GitHubController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private GitHubService userService;

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
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userService.saveUser(user);
            response.addCookie(new Cookie("token",token)); //创建一个Cookie
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
