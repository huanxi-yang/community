package cn.fzkj.community.service;

import cn.fzkj.community.domain.User;
import cn.fzkj.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitHubService {

    @Autowired(required = false)
    private UserMapper userMapper;

    public void saveUser(User user) {
        userMapper.saveUser(user);
    }
}
