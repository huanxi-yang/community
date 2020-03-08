package cn.fzkj.community.service;

import cn.fzkj.community.domain.User;
import cn.fzkj.community.domain.UserExample;
import cn.fzkj.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    //通过token查询用户
    public User findByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        return userMapper.selectByExample(userExample).get(0);
    }

    //判断数据库中是否已经存在该用户
    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        User dbUser = userMapper.selectByExample(userExample).get(0);
        if(dbUser == null){
            //添加用户到数据库
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新数据库中用户的信息
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            userMapper.updateByPrimaryKeySelective(dbUser);
        }
    }
}
