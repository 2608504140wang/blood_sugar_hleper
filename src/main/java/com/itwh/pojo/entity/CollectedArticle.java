package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectedArticle {

    private Long id;

    //用户id
    private Long userId;

    //文章id
    private Long articleId;

}
