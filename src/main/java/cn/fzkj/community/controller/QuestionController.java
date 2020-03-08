package cn.fzkj.community.controller;

import cn.fzkj.community.dto.PageBean;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/question/{id}")
    public String question (@PathVariable("id") Integer id, Model model){
        //通过问题id查询出来对应的问题
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }

}
