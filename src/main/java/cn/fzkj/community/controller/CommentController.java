package cn.fzkj.community.controller;

import cn.fzkj.community.domain.Comment;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.CommentDTO;
import cn.fzkj.community.dto.CommentResultDTO;
import cn.fzkj.community.dto.ResultDTO;
import cn.fzkj.community.enums.CommentTypeEnum;
import cn.fzkj.community.exception.CustomErrorCode;
import cn.fzkj.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomErrorCode.NOT_LOGIN);
        }

        if (commentDTO == null || StringUtils.isEmpty(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomErrorCode.CONTENT_NOT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setCommentor(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentService.insert(comment);
        return ResultDTO.success();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentResultDTO>> comments(@PathVariable(name = "id") Long id){
        List<CommentResultDTO> commentResultDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.success(commentResultDTOS);
    }
}
