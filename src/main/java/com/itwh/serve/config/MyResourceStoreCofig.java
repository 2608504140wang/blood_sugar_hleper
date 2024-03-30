package com.itwh.serve.config;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.generator.common.constant.SliderCaptchaConstant;
import cloud.tianai.captcha.generator.impl.StandardSliderImageCaptchaGenerator;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.common.model.dto.ResourceMap;
import cloud.tianai.captcha.resource.impl.DefaultResourceStore;
import cloud.tianai.captcha.resource.impl.provider.ClassPathResourceProvider;
import org.springframework.stereotype.Component;

import static cloud.tianai.captcha.generator.impl.StandardSliderImageCaptchaGenerator.DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH;

@Component
public class MyResourceStoreCofig extends DefaultResourceStore {

    public MyResourceStoreCofig() {

        // 滑块验证码 模板 (系统内置)
        ResourceMap template1 = new ResourceMap("default",4);
        template1.put(SliderCaptchaConstant.TEMPLATE_ACTIVE_IMAGE_NAME, new cloud.tianai.captcha.resource.common.model.dto.Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/1/active.png")));
        template1.put(SliderCaptchaConstant.TEMPLATE_FIXED_IMAGE_NAME, new cloud.tianai.captcha.resource.common.model.dto.Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/1/fixed.png")));
        ResourceMap template2 = new ResourceMap("default",4);
        template2.put(SliderCaptchaConstant.TEMPLATE_ACTIVE_IMAGE_NAME, new cloud.tianai.captcha.resource.common.model.dto.Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/2/active.png")));
        template2.put(SliderCaptchaConstant.TEMPLATE_FIXED_IMAGE_NAME, new cloud.tianai.captcha.resource.common.model.dto.Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/2/fixed.png")));
        // 旋转验证码 模板 (系统内置)
        ResourceMap template3 = new ResourceMap("default",4);
        template3.put(SliderCaptchaConstant.TEMPLATE_ACTIVE_IMAGE_NAME, new cloud.tianai.captcha.resource.common.model.dto.Resource(ClassPathResourceProvider.NAME, StandardSliderImageCaptchaGenerator.DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/3/active.png")));
        template3.put(SliderCaptchaConstant.TEMPLATE_FIXED_IMAGE_NAME, new cloud.tianai.captcha.resource.common.model.dto.Resource(ClassPathResourceProvider.NAME, StandardSliderImageCaptchaGenerator.DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/3/fixed.png")));

        // 1. 添加一些模板
        addTemplate(CaptchaTypeConstant.SLIDER, template1);
        addTemplate(CaptchaTypeConstant.SLIDER, template2);
        addTemplate(CaptchaTypeConstant.ROTATE, template3);

        // 2. 添加自定义背景图片
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/a.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/b.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/c.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/d.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/e.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/g.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/h.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/i.jpg","default"));
        addResource(CaptchaTypeConstant.SLIDER, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/j.jpg","default"));

        addResource(CaptchaTypeConstant.ROTATE, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/48.jpg","default"));

        addResource(CaptchaTypeConstant.CONCAT, new cloud.tianai.captcha.resource.common.model.dto.Resource("classpath", "static/bgimages/48.jpg","default"));

        addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource("classpath", "static/bgimages/b.png","default"));
    }
}

