package cn.fzkj.community.cache;

import cn.fzkj.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> getTag(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO language = new TagDTO();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("javascript","php","css","html","html5","java","python","node.js","c++","c","golang","objective-c","typescript"
        ,"shell","TSL","c#","asp.net"));
        tagDTOS.add(language);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","spring","express","diango","flask","yii","tornado","struts"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","tomcat","缓存","负载均衡","unix","hadoop","windows-server"));
        tagDTOS.add(server);

        TagDTO cache = new TagDTO();
        cache.setCategoryName("数据库和缓存");
        cache.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql","sqlserver","sqlite"));
        tagDTOS.add(cache);

        TagDTO tools = new TagDTO();
        tools.setCategoryName("开发工具");
        tools.setTags(Arrays.asList("github","git","svn","vim","sublime-text","maven","eclipse","idea"));
        tagDTOS.add(tools);

        return tagDTOS;
    }

    // 校验不通过的标签
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = TagCache.getTag();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

}
