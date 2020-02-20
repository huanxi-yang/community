package cn.fzkj.community.dto;

import cn.fzkj.community.domain.User;
import lombok.Data;

public @Data
class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long GmtModified;
    private Integer creator;
    private Integer attentionCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;

}
