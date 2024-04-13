package com.itwh.pojo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListArticleDTO {

    //文章类型
    private String type;

    //文章标题
    private String title;

    //作者
    private String author;

}
