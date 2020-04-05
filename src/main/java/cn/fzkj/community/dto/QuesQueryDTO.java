package cn.fzkj.community.dto;

import lombok.Data;

@Data
public class QuesQueryDTO {
    private String search;
    private Integer page;
    private Integer limit;
}
