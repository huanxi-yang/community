package cn.fzkj.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;   //指定是评论还是回复
}
