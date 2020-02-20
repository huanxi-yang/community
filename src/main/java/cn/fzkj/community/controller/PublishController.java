package cn.fzkj.community.controller;

import cn.fzkj.community.domain.Question;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.mapper.QuestionMapper;
import cn.fzkj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//处理文章的

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/publish.action")
    public String publish(@RequestParam("title") String title ,
                          @RequestParam("description") String description ,
                          @RequestParam("tag") String tag,
                          HttpServletRequest request){
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        User user = (User)request.getSession().getAttribute("user");
        question.setCreatar(user.getId());
        questionService.createQues(question);
        return "redirect:/";
    }

}
