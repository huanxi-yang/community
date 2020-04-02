package cn.fzkj.community.dto;

import cn.fzkj.community.domain.User;
import lombok.Data;

@Data
public class NotifyDTO {
    private Long id;
    private Long outerid; // 被评论的id
    private Long notifier;  // 发送方的id
    private Long receiver;  // 接收方的id,就是当前用户
    private Integer type;   // 通知的类型：评论、点赞、收藏
    private Long gmtCreate;
    private Integer status; // 已读、未读
    private User user;  // 发送方
    private String data; // 被评论的
    private String typeContent; //type的内容：回复了评论、回复了问题、收藏
}
