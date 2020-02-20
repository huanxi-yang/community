package cn.fzkj.community.mapper;

import cn.fzkj.community.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> getAllUser();

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{AccountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void saveUser(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
