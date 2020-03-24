package cn.fzkj.community.exception;

import cn.fzkj.community.dto.ResultDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            // 返回提示
            if(e instanceof CustomException){
                resultDTO = ResultDTO.errorOf((CustomException) e);
            }else{
                resultDTO = ResultDTO.errorOf(CustomErrorCode.SYSTEM_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch(IOException ioException){}
            return null;
        }else{
            // 返回错误页面
            if(e instanceof CustomException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}