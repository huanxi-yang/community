package cn.fzkj.community.dto;

import cn.fzkj.community.domain.User;
import lombok.Data;

public @Data
class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long GmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;

}
