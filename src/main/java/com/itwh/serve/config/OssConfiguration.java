package com.itwh.serve.config;

import com.itwh.common.properties.AliOssProperties;
import com.itwh.common.utils.AliOssUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//配置类，用于创建AliOssUtil对象
@Configuration
public class OssConfiguration {

    @Bean //注入容器
    @ConditionalOnMissingBean //确保容器中只有一个对象
    public AliOssUtils aliOssUtils(AliOssProperties aliOssProperties){
        return new AliOssUtils(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
