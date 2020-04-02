package cn.fzkj.community.service;

import cn.fzkj.community.domain.*;
import cn.fzkj.community.dto.NotifyDTO;
import cn.fzkj.community.dto.PageBean;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.enums.NotificationStatusEnum;
import cn.fzkj.community.enums.NotificationTypeEnum;
import cn.fzkj.community.exception.CustomErrorCode;
import cn.fzkj.community.exception.CustomException;
import cn.fzkj.community.exception.ICustomErrorCode;
import cn.fzkj.community.mapper.CommentMapper;
import cn.fzkj.community.mapper.NotificationMapper;
import cn.fzkj.community.mapper.QuestionMapper;
import cn.fzkj.community.mapper.UserMapper;
import cn.fzkj.community.service.Impl.NotificationService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired(required = false)
    private NotificationMapper notificationMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Override
    public PageBean<NotifyDTO> list(Long id, Integer page) {
        List<NotifyDTO> list = new ArrayList<>();
        PageBean<NotifyDTO> pagesinfo = new PageBean<>();
        pagesinfo.setPage(page);
        //1.设置limit
        Integer limit = 5;
        pagesinfo.setLimit(limit);
        //2.
        NotificationExample notifyExample = new NotificationExample();
        notifyExample.createCriteria().
                andReceiverEqualTo(id);// 当前查看消息的用户就是接收方
        Integer total = (int)notificationMapper.countByExample(notifyExample);
        pagesinfo.setTotal(total);
        //3.设置总的页数
        Integer totalPage;
        if(total % limit == 0){
            totalPage = total / limit;
        }else{
            totalPage = total / limit + 1;
        }
        pagesinfo.setTotalPage(totalPage);
        //4.设置页数的集合
        List<Integer> pages = new ArrayList<>();
        //如果总页数大于7，就产生7个
        //如果总页数小于7.就显示全部
        if(totalPage > 5){
            if (page - 2 <= 0){
                for(int i=1; i< 6; i++){
                    pages.add(i);
                }
            }
            else if (page + 2 > totalPage){
                for(int i=page-2; i< totalPage+1; i++){
                    pages.add(i);
                }
            }
            else{
                for(int i=page-2; i < page+3; i++){
                    pages.add(i);
                }
            }
        }else{
            for(int i=1; i<totalPage+1; i++){
                pages.add(i);
            }
        }
        pagesinfo.setPages(pages);
        //5.设置每页的数据集合
        page = (page-1)*limit;  //计算每页开始的位置
        NotificationExample example = new NotificationExample();
        example.createCriteria().
                andReceiverEqualTo(id);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(page,limit));

        if (notifications.size() == 0){
            return pagesinfo;
        }
        Set<Long> disUserIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());

        List<Long> userIds = new ArrayList<>(disUserIds);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<NotifyDTO> notifyDTOS = new ArrayList<>();
        // 还需要查找出来回复了那条问题或评论，和评论人
        for (Notification notification : notifications){
            NotifyDTO notifyDTO = new NotifyDTO();
            // 查找被回复的
            if (notification.getType() == 1){
                // 回复了问题
                Question question = questionMapper.selectByPrimaryKey(notification.getOuterid());
                if (question == null){
                    notifyDTO.setData("");
                }
                notifyDTO.setData(question.getTitle());
            }else{
                // 回复了评论
                Comment comment = commentMapper.selectByPrimaryKey(notification.getOuterid());
                if (comment == null){
                    notifyDTO.setData("");
                }
                notifyDTO.setData(comment.getContent());
            }
            BeanUtils.copyProperties(notification,notifyDTO);
            notifyDTO.setTypeContent(NotificationTypeEnum.getTypeName(notification.getType()));
            Long userId = notification.getNotifier();
            notifyDTO.setUser(userMap.get(userId));
            notifyDTOS.add(notifyDTO);
        }
        pagesinfo.setPageRecode(notifyDTOS);
        return pagesinfo;
    }

    @Override
    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andStatusEqualTo(1);
        long unreadCount = notificationMapper.countByExample(notificationExample);
        return unreadCount;
    }

    @Override
    public NotifyDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomException(CustomErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver() != user.getId()){
            throw new CustomException(CustomErrorCode.HAVA_NO_RIGHT);
        }

        // 更新为已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotifyDTO notifyDTO = new NotifyDTO();
        BeanUtils.copyProperties(notification,notifyDTO);
        notifyDTO.setTypeContent(NotificationTypeEnum.getTypeName(notification.getType()));
        return notifyDTO;
    }
}
