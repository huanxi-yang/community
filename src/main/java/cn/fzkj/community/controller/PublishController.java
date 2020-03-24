package cn.fzkj.community.controller;

import cn.fzkj.community.domain.Question;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.mapper.QuestionMapper;
import cn.fzkj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//处理文章的

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    //发布和修改问题
    @RequestMapping("/publish.action")
    public String publish(@RequestParam(value = "id", required = false) Long id,@RequestParam("title") String title ,
                          @RequestParam("description") String description ,
                          @RequestParam("tag") String tag,
                          HttpServletRequest request){
        System.out.println(id);
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        User user = (User)request.getSession().getAttribute("user");
        if(id != null) {
            question.setId(id);
        }
        question.setCreator(user.getId());
        questionService.updateOrcreateQues(question);
        return "redirect:/";
    }

    @RequestMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        //从数据库查询问题出来
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        //回显到publish页面
        model.addAttribute("question",questionDTO);
        //保存修改后的问题

        return "publish";
    }

}
