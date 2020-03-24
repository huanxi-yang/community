package cn.fzkj.community.controller;

import cn.fzkj.community.dto.CommentResultDTO;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.enums.CommentTypeEnum;
import cn.fzkj.community.service.CommentService;
import cn.fzkj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/question/{id}")
    public String question (@PathVariable("id") Long id, Model model){
        //通过问题id查询出来对应的问题
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        // 拿到评论
        List<CommentResultDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //实现阅读数的加1
        questionService.incView(questionDTO);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }

}
