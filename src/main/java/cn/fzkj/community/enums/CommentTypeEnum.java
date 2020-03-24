package cn.fzkj.community.enums;

/*

 */
public enum CommentTypeEnum {

    QUESTION(1),    // 针对问题的回复
    COMMENT(2);     //针对评论的回复

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(value.getType() == type){
                return true;
            }
        }
        return false;
    }

}
