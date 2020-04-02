package cn.fzkj.community.mapper;

import cn.fzkj.community.domain.Question;
import cn.fzkj.community.domain.QuestionExample;
import cn.fzkj.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int CommentCount(QuestionDTO questionDTO);

    void incView(QuestionDTO questionDTO);

    List<Question> selectRelated(Question question);
}