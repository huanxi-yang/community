package cn.fzkj.community.mapper;

import cn.fzkj.community.domain.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creatar,tag) values (#{title},#{description},#{gmtCreate},#{GmtModified},#{creatar},#{tag})")
    void createQues(Question question);

    //查询所有的问题并回显到index页面
    @Select("select * from question where id limit #{page},#{limit}")
    List<Question> questionList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    //查询总的问题数
    @Select("select count(id) from question")
    Integer fingCount();
}
