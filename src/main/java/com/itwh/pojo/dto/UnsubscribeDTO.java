package com.itwh.pojo.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

@Data
public class UnsubscribeDTO {

    //短信验证码
    private String smsCode;

    //行为验证码id
    private String id;

    //行为验证码相关信息
    private ImageCaptchaTrack data;

}
