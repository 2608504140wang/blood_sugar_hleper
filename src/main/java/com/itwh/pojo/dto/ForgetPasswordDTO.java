package com.itwh.pojo.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

@Data
public class ForgetPasswordDTO {
    //账号
    private String userName;

    //新密码第一次输入
    private String newPassword1;

    //新密码第二次输入
    private String newPassword2;

}
