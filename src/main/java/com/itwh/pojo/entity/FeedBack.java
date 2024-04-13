package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack {

    private Long id;

    //用户id
    private Long userId;

    //内容
    private String word;

    //类型
    private String type;

    //联系方式
    private String mobile;

    //提交时间
    private LocalDateTime createTime;

}
