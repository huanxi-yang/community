package cn.fzkj.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    private String categoryName;        // 大的分类
    private List<String> tags;      // 每个分类下的标签
}
