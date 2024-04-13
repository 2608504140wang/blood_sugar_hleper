package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    private Long id;

    //标题
    private String title;

    //内容
    private String word;

    //发布时间
    private LocalDateTime createTime;

    //发布管理员id
    private Long adminId;

}
