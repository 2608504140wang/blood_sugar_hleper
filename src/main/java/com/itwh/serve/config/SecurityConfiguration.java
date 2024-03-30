package com.itwh.serve.config;

import com.itwh.serve.handler.AccessDeniedHandlerHandler;
import com.itwh.serve.filter.JwtAuthenticationTokenFilter;
import com.itwh.serve.filter.SmsVerifyCodeValidateFilter;
import com.itwh.serve.handler.AuthenticationEntryPointHandler;
import com.itwh.serve.handler.DefinedAuthenticationFailureHandler;
import com.itwh.serve.handler.DefinedAuthenticationSuccessHandler;
import com.itwh.serve.service.Impl.LoginUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * springsecurity配置类
 */
@Configuration
@EnableWebSecurity //启动 SpringSecurity 过滤器链功能
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefinedAuthenticationSuccessHandler definedAuthenticationSuccessHandler;

    @Autowired
    private DefinedAuthenticationFailureHandler definedAuthenticationFailureHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private SmsVerifyCodeValidateFilter smsVerifyCodeValidateFilter;

    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    private LoginUserDetailsServiceImpl loginUserDetailsService;

    @Autowired
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;

    @Autowired
    private AccessDeniedHandlerHandler accessDeniedHandlerHandler;

    /**
     * 注入BCryptPasswordEncoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 将provider manager注入容器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //  允许匿名访问  即可以绕过过滤器
                .antMatchers("/login/mobile", "/login/username","/register","/code/mobile","/captcha/get","/captcha/check","/password/update1").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        http.formLogin()
                //配置认证成功处理器
                .successHandler(definedAuthenticationSuccessHandler)
                //配置认证失败处理器
                .failureHandler(definedAuthenticationFailureHandler);

        http
                //把token校验过滤器添加到过滤器链中 ,添加到 UsernamePasswordAuthenticationFilter 前面
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                //短信验证码校验过滤器 smsVerifyCodeValidateFilter 添加到 UsernamePasswordAuthenticationFilter 前面
                .addFilterBefore(smsVerifyCodeValidateFilter,UsernamePasswordAuthenticationFilter.class);

        //跨域
        http.cors();

                                //配置自定义认证失败处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandler)
                                //配置自定义授权失败处理器
                                .accessDeniedHandler(accessDeniedHandlerHandler);

        // 将手机相关的配置绑定过滤器链上
        http.apply(mobileAuthenticationConfig);
    }

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定使用自定义查询用户信息来完成身份认证
        auth.userDetailsService(loginUserDetailsService);

    }
}
