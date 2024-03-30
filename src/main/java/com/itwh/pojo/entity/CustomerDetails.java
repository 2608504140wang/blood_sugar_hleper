package com.itwh.pojo.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CustomerDetails {

    private Long id;

    //用户id
    private Long userId;

    //身高
    private Integer height;

    //体重
    private Integer weight;

    //年龄
    private Integer age;

    //住址
    private String address;

    //生日
    private LocalDate birthday;

    //职业
    private String occupation;

    //个人简介
    private String introduction;

    //信息修改时间
    private LocalDateTime updateTime;

    //信息修改人
    private Long updateUser;

}
