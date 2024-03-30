package com.itwh.serve.config;


import com.itwh.serve.filter.MobileAuthenticationFilter;
import com.itwh.serve.handler.DefinedAuthenticationFailureHandler;
import com.itwh.serve.handler.DefinedAuthenticationSuccessHandler;
import com.itwh.serve.provider.MobileAuthenticationProvider;
import com.itwh.serve.service.Impl.MobileUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 自定义管理认证配置
 * 将定义的手机短信认证相关的组件组合起来，一起添加到容器中
 */
@Configuration
public class MobileAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    DefinedAuthenticationSuccessHandler successHandler;
    @Autowired
    DefinedAuthenticationFailureHandler failureHandler;
    @Autowired
    MobileUserDetailsServiceImpl mobileUserDetailsServiceImpl;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 创建校验手机号过滤器实例
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        // 接收 AuthenticationManager 认证管理器
        mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        // 采用哪个成功、失败处理器
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        //手机登录记住我功能
        //mobileAuthenticationFilter.setRememberMeServices(http.getSharedObject(RememberMeServices.class));

        // 为 Provider 指定明确的mobileUserDetailsService 来查询用户信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsServiceImpl);
        // 将 provider 绑定到 HttpSecurity 上面，
        // 并且将 手机认证加到 用户名密码认证之后
        http.authenticationProvider(provider).addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
