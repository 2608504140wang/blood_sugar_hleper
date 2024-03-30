package com.itwh.pojo.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

@Data
public class CaptchaData {

    private String  id;
    private ImageCaptchaTrack data;

}
