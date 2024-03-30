package com.itwh.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "itwh.sms")
@Data
public class SmsProperties {
    private String  accessKeyId;
    private String  accessKeySecret;
    private String  regionId;
    private String  signName;
    private String  templateCode;
    private String  product;
    private String  domain;
}
