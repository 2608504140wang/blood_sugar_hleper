package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackPicture {

    private Long id;

    //反馈id
    private Long feedBackId;

    //反馈图片
    private String picture;

}
