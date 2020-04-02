package cn.fzkj.community.service;

import cn.fzkj.community.domain.*;
import cn.fzkj.community.dto.CommentResultDTO;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.enums.CommentTypeEnum;
import cn.fzkj.community.enums.NotificationStatusEnum;
import cn.fzkj.community.enums.NotificationTypeEnum;
import cn.fzkj.community.exception.CustomErrorCode;
import cn.fzkj.community.exception.CustomException;
import cn.fzkj.community.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private CommentExtMapper commentExtMapper;
    @Autowired(required = false)
    private NotificationMapper notificationMapper;
    @Autowired
    private HttpServletRequest request;

    // 添加评论
    @Transactional
    public void insert(Comment comment ) {
        User user = (User) request.getSession().getAttribute("user");
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomException(CustomErrorCode.TARGET_PARAM_NOT_FIND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomException(CustomErrorCode.TYPE_ERROR);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论，2级的回复
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId()); // 获取回复的那个问题
            if (dbComment == null){
                throw new CustomException(CustomErrorCode.COMMENT_NOT_FIND);
            }else{
                //插入
                commentMapper.insertSelective(comment);

                // 增加评论数
                Comment parentComment = new Comment();
                parentComment.setId(comment.getParentId());
                parentComment.setCommentCount(1);
                commentExtMapper.CommentCount(parentComment);

                // 插入回复，用于通知的显示
                createNotify(comment, dbComment.getCommentor(), NotificationTypeEnum.REPLAY_COMMENT);
            }
        }else{
            // 添加评论，1级的评论
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FIND);
            }
            commentMapper.insertSelective(comment);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
//            System.out.println("CommentServie lines:74:"+questionDTO.toString());
            // 增加评论数
            questionDTO.setCommentCount(1);
            questionExtMapper.CommentCount(questionDTO);

            createNotify(comment, question.getCreator(), NotificationTypeEnum.REPLAY_QUESTION);
        }
    }

    private void createNotify(Comment comment, Long receiver, NotificationTypeEnum replayType) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(replayType.getType());
        notification.setNotifier(comment.getCommentor());
        notification.setOuterid(comment.getParentId());  //被评论的内容id
        notification.setReceiver(receiver);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);
    }


    // 查找评论
    public List<CommentResultDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        // 拿出所有的作者
        if (comments.size() == 0){
            return new ArrayList<>();
        }
        // 使用lamda表达式获取不重复的评论人
        Set<Long> commentors = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentors);

        // 获取评论人并存到map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转化comment为commentResultDTO
        List<CommentResultDTO> commentResultDTOs = comments.stream().map(comment -> {
            CommentResultDTO commentResultDTO = new CommentResultDTO();
            BeanUtils.copyProperties(comment, commentResultDTO);
            commentResultDTO.setUser(userMap.get(comment.getCommentor()));
            return commentResultDTO;
        }).collect(Collectors.toList());

        return commentResultDTOs;
    }

    public Comment findById(Long outerid) {
        return commentMapper.selectByPrimaryKey(outerid);
    }
}
