package com.itwh.pojo.dto;

import lombok.Data;

@Data
public class UpdateArticleDTO {

    //文章id
    private Long id;

    //文章类型
    private String type;

    //文章标题
    private String title;

    //预览
    private String preview;

    //文章链接
    private String link;

    //作者
    private String author;

    //图片
    private String picture;

}
