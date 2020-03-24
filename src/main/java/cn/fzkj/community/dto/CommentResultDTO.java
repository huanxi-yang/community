package cn.fzkj.community.dto;

import cn.fzkj.community.domain.User;
import lombok.Data;

// 返回页面的DTO
@Data
public class CommentResultDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
