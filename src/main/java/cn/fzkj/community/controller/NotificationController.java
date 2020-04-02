package cn.fzkj.community.controller;

import cn.fzkj.community.domain.Comment;
import cn.fzkj.community.domain.Notification;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.NotifyDTO;
import cn.fzkj.community.dto.PageBean;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.enums.NotificationTypeEnum;
import cn.fzkj.community.service.CommentService;
import cn.fzkj.community.service.Impl.NotificationService;
import cn.fzkj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id,
                          HttpServletRequest request,
                          Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        NotifyDTO notifyDTO = notificationService.read(id,user);
        if (notifyDTO.getType() == NotificationTypeEnum.REPLAY_QUESTION.getType()){
            return "redirect:/question/"+notifyDTO.getOuterid();
        }else if(notifyDTO.getType() == NotificationTypeEnum.REPLAY_COMMENT.getType()){
            // 是二级评论
            // 查询对应的问题
            Comment comment = commentService.findById(notifyDTO.getOuterid());
            QuestionDTO questionDTO = questionService.findQuestionById(comment.getParentId());
            return "redirect:/question/"+questionDTO.getId();
        }else {
            return "redirect:/";
        }
    }
}
