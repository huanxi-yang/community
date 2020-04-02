package cn.fzkj.community.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.UUID;

@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public JSONObject editormdPic (@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{

        String trueFileName = file.getOriginalFilename();

        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
//        System.out.println(StringUtils.substringAfterLast(uuid,"-"));
        String fileName = System.currentTimeMillis()+"_"+StringUtils.substringAfterLast(uuid,"-")+suffix;

        String path = request.getSession().getServletContext().getRealPath("/photo/upload/");
//        System.out.println(path);
        File targetFile = new File(path,fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONObject res = new JSONObject();
        res.put("url","/photo/upload/"+fileName);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;

    }
}
