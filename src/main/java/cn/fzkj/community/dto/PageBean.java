package cn.fzkj.community.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data
class PageBean<T> implements Serializable {
    private Integer page;//当前页数
    private Integer limit;//每页显示数
    private Integer totalPage;//总页数
    private Integer total;//总记录数
    private List<T> pageRecode;//当前页面的数据集合
    private List<Integer> pages;//返回页数的集合，用于显示index页面的上一页、下一页
}
