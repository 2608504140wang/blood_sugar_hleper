package com.itwh.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "itwh.security")
@Data
public class SecurityProperties {
    //用户名密码登录接口
    private String usernameLoginProcessingUrl = "/login/username";
    private String usernameParameter = "username";
    private String passwordParameter = "password";

    //手机号短信验证码登录接口
    private String mobileLoginProcessingUrl = "/login/mobile";

    //静态资源路径
    //private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};

    //忘记密码或个人中心修改密码接口
    private String updatePasswordUrl1 = "/password/update1";

    //登录后修改密码
    private String updatePasswordUrl2 = "/password/update2";

    //手机号注册账号接口
    private String registerUserUrl = "/register";

    //账号注销接口
    private String unsubscribeUrl = "/unsubscribe";

    //修改手机号
    private String updateMobile = "/mobile/update";

}
