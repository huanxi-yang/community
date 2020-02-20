package cn.fzkj.community.service;

import cn.fzkj.community.domain.Question;
import cn.fzkj.community.domain.User;
import cn.fzkj.community.dto.PageBean;
import cn.fzkj.community.dto.QuestionDTO;
import cn.fzkj.community.mapper.QuestionMapper;
import cn.fzkj.community.mapper.UserMapper;
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

    public void createQues(Question question) {
        questionMapper.createQues(question);
    }

    //查询所有的问题回显到index页面
    public PageBean<QuestionDTO> questionList(Integer page) {
        List<QuestionDTO> list = new ArrayList<>();
        PageBean<QuestionDTO> pagesinfo = new PageBean<>();
        //1.设置limit
        Integer limit = 5;
        pagesinfo.setLimit(limit);
        //2.设置总记录数
        Integer total = questionMapper.fingCount();
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
        for(int i=1;i<totalPage+1;i++){
            pages.add(i);
        }
        pagesinfo.setPages(pages);
        //5.设置每页的数据集合
        List<Question> questions = questionMapper.questionList(page,limit);
        for(Question question : questions){
            User user = userMapper.findById(question.getCreatar());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        pagesinfo.setPageRecode(list);
        return pagesinfo;
    }
}
