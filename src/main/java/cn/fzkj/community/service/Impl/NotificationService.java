package cn.fzkj.community.service.Impl;

import cn.fzkj.community.domain.Notification;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.NotifyDTO;
import cn.fzkj.community.dto.PageBean;
import org.springframework.stereotype.Service;

public interface NotificationService {

    // 查询我的消息列表
    PageBean<NotifyDTO> list(Long id, Integer page);


    Long unreadCount(Long id);

    // 已读
    NotifyDTO read(Long id, User user);

}
