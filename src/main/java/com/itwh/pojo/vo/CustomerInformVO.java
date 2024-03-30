package com.itwh.pojo.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerInformVO {

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

    //账号
    private String userName;

    //昵称
    private String nickName;

    //姓名
    private String name;

    //手机号
    private String mobile;

    //性别
    private String gender;

    //头像
    private String avatar;

    //账号关联人id
    private List<Long> associatedIds;

    //账号创建时间
    private LocalDateTime createTime;

    //账号修改时间
    private LocalDateTime updateTime;

}
