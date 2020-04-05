package cn.fzkj.community.controller;

import cn.fzkj.community.dto.CommentResultDTO;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.enums.CommentTypeEnum;
import cn.fzkj.community.service.CommentService;
import cn.fzkj.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        // 将tag中的中文逗号替换成英文的，前端显示的时候方便
        String tag = StringUtils.replace(questionDTO.getTag(), "，", ",");
        questionDTO.setTag(tag);
        // 拿到评论
        List<CommentResultDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //实现阅读数的加1
        questionService.incView(questionDTO);

        // 查找出相关问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";

    }

    @RequestMapping("/del/{id}")
    public String del(@PathVariable("id") Long id){
        questionService.delQuesById(id);
        return "redirect:/profile/questions";
    }
}
