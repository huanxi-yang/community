package cn.fzkj.community.controller;

import cn.fzkj.community.cache.TagCache;
import cn.fzkj.community.exception.CustomErrorCode;
import cn.fzkj.community.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用来验证tag是否有效
 */
@RestController
public class TagController {
// # %23  + %2B
    @ResponseBody
    @RequestMapping("/tagValid.action")
    public Object tagValid(@RequestBody String tags){
        /**
         * 我不知道前端发送的json格式的数据怎样处理，
         * 接收到的数据：tags=...
         * 我只需要'='后面的数据，不知道怎么处理，投机
         */
        tags = tags.replace("%2C",",").replace("%23","#").replace("%2B","+");
        tags = StringUtils.substringAfterLast(tags,"=");

        if (tags == "" || tags == null){
            return new CustomException(CustomErrorCode.TAG_EMPTY);
        }else {
            // 获得非法的标签
            String filterInvalid = TagCache.filterInvalid(tags);
            return filterInvalid;
        }
    }
}
