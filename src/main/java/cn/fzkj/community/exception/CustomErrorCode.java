package cn.fzkj.community.exception;

public enum CustomErrorCode implements ICustomErrorCode {

    QUESTION_NOT_FIND(2001, "问题不存在~~"),
    TARGET_PARAM_NOT_FIND(2002, "没有选中要评论的问题~~"),
    TYPE_ERROR(2003, "评论的类型异常~~"),
    NOT_LOGIN(2004,"登录状态异常~~"),
    SYSTEM_ERROR(2005,"服务器异常~~"),
    COMMENT_NOT_FIND(2006,"该评论不存在了~~"),
    CONTENT_NOT_EMPTY(2007,"评论为空~~");

    private String message;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    CustomErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
