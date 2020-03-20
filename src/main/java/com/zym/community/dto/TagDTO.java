package com.zym.community.dto;

import com.sun.javafx.font.PrismFontFactory;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2020/3/18-22:56
 */
@Data
public class TagDTO {

    private String tagCategory;
    private List<String> tagName;
}
