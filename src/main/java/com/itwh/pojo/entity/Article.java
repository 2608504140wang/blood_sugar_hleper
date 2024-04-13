package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Long id;

    //文章类型
    private String type;

    //文章标题
    private String title;

    //预览
    private String preview;

    //文章链接
    private String link;

    //点赞
    private Long likeNum;

    //收藏
    private Long collect;

    //作者
    private String author;

    //图片
    private String picture;

}
