package cn.fzkj.community.exception;

import cn.fzkj.community.dto.ResultDTO;

public class CustomException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomException(ICustomErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public CustomException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
