package cn.fzkj.community.service;

import cn.fzkj.community.domain.Question;
import cn.fzkj.community.domain.QuestionExample;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.PageBean;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.mapper.QuestionMapper;
import cn.fzkj.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public void updateOrcreateQues(Question question) {
        if(question.getId()==null){
            //创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }else{
            //更新问题
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, example);
        }
    }

    //查询所有的问题回显到index页面nested exception is java.sql.SQLException: Field 'id' doesn't have a default value] with root cause
    public PageBean<QuestionDTO> questionList(Integer page) {
        List<QuestionDTO> list = new ArrayList<>();
        PageBean<QuestionDTO> pagesinfo = new PageBean<>();
        //1.设置limit
        Integer limit = 5;
        pagesinfo.setLimit(limit);
        //2.设置总记录数
        Integer total = (int)questionMapper.countByExample(new QuestionExample());
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
        if(totalPage>7){
            for(int i=page-3; i<8; i++){
                pages.add(i);
            }
        }else{
            for(int i=1; i<totalPage+1; i++){
                pages.add(i);
            }
        }
        pagesinfo.setPages(pages);
        //5.设置每页的数据集合
        page = (page-1)*limit;  //计算每页开始的位置
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(page,limit));
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        pagesinfo.setPageRecode(list);
        return pagesinfo;
    }

    //查询用户的问题集合返回
    public PageBean<QuestionDTO> findUserQuestions(Integer id, Integer page) {
        List<QuestionDTO> list = new ArrayList<>();
        PageBean<QuestionDTO> pagesinfo = new PageBean<>();
        //1.设置limit
        Integer limit = 5;
        pagesinfo.setLimit(limit);
        //2.设置总记录数,单个用户的所有问题数量
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().
                andCreatorEqualTo(id);
        Integer total = (int)questionMapper.countByExample(example1);
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
        if(totalPage>7){
            for(int i=page-3; i<8; i++){
                pages.add(i);
            }
        }else{
            for(int i=1; i<totalPage+1; i++){
                pages.add(i);
            }
        }
        pagesinfo.setPages(pages);
        //5.设置每页的数据集合
        page = (page-1)*limit;  //计算每页开始的位置
        QuestionExample example = new QuestionExample();
        example.createCriteria().
                andCreatorEqualTo(id);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(page,limit));
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        pagesinfo.setPageRecode(list);
        return pagesinfo;
    }

    //通过问题的id查找
    public QuestionDTO findQuestionById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

}
