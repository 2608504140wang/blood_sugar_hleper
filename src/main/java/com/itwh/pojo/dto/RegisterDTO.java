package com.itwh.pojo.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    //昵称
    private String nickName;

    //密码
    private String password;

    //姓名（选）
    private String name;

    //性别（选）
    private String gender;

    //手机号
    private String mobile;

    //角色
    private String roleName;

}
