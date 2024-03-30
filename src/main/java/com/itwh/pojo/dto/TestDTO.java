package com.itwh.pojo.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

@Data
public class TestDTO {
    private String id;
    private ImageCaptchaTrack data;
}
