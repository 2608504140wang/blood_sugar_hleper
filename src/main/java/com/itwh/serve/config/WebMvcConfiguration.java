package com.itwh.serve.config;

import com.itwh.common.json.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * 扩展springMVC框架的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        //将自己的消息转换器加入到容器中
        converters.add(0, converter); //0表示将此消息转换器排在系统框架自带消息转换器的第一个，即优先使用
    }
}