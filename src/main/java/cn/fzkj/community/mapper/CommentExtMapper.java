package cn.fzkj.community.mapper;

import cn.fzkj.community.domain.Comment;
import cn.fzkj.community.dto.QuestionDTO;

public interface CommentExtMapper {

    int CommentCount(Comment comment);
}