package cn.fzkj.community.dto;

import cn.fzkj.community.exception.CustomErrorCode;
import cn.fzkj.community.exception.CustomException;
import lombok.Data;

@Data
public class ResultDTO<T> {

    private Integer code;   //状态码
    private String message;     //信息
    private T data;     //返回的数据

    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomException e){
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO success(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功！！！");
        return resultDTO;
    }

    public static <T> ResultDTO success(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功！！！");
        resultDTO.setData(t);
        return resultDTO;
    }
}
