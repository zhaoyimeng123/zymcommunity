package com.zym.community.cache;

import com.zym.community.dto.TagDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @date 2020/3/18-22:59
 */
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagCategory("开发语言");
        tagDTO.setTagName(Arrays.asList("js","java","c语言","c++","python"));
        tagDTOS.add(tagDTO);

        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setTagCategory("平台框架");
        tagDTO1.setTagName(Arrays.asList("Spring","django","flask","koa","struct"));
        tagDTOS.add(tagDTO1);

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setTagCategory("服务器");
        tagDTO2.setTagName(Arrays.asList("linux","nginx","tomcat","jetty","hadoop"));
        tagDTOS.add(tagDTO2);
        return tagDTOS;
    }

}
