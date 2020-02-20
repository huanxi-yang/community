package cn.fzkj.community.domain;

import java.io.Serializable;


public class Question implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long GmtModified;
    private Integer creatar;
    private Integer attentionCount; //回复数
    private Integer viewCount;  //浏览数
    private Integer likeCount;  //
    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return GmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        GmtModified = gmtModified;
    }


    public Integer getCreatar() {
        return creatar;
    }

    public void setCreatar(Integer creatar) {
        this.creatar = creatar;
    }

    public Integer getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(Integer attentionCount) {
        this.attentionCount = attentionCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
