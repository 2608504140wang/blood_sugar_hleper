package com.itwh.serve.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itwh.common.exception.SmsVerifyCodeExpection;
import com.itwh.common.properties.SecurityProperties;
import com.itwh.serve.handler.DefinedAuthenticationFailureHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * 短信验证码校验过滤器
 * OncePerRequestFilter: 所有请求之前被调用一次
 */
@Component
public class SmsVerifyCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    DefinedAuthenticationFailureHandler definedAuthenticationFailureHandler;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //判断所拦截接口的URL即其请求方法类型
        if ((securityProperties.getMobileLoginProcessingUrl().equalsIgnoreCase(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("post"))
                || (securityProperties.getUpdatePasswordUrl1().equalsIgnoreCase(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("put"))
                || (securityProperties.getRegisterUserUrl().equalsIgnoreCase(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("post"))
                || (securityProperties.getUnsubscribeUrl().equalsIgnoreCase(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("delete"))) {
            try {
                //校验验证码合法性
                // 从请求头中读取数据
                String mobile = request.getHeader("mobile");

                String inputCode = request.getHeader("smsCode");

                //再获取redis中对应手机号的验证码
                String code = (String) redisTemplate.opsForValue().get("mobile:"+mobile);

                if (code == null){
                    throw new SmsVerifyCodeExpection("验证码已过期，请重新请求验证码");
                }
                if (StringUtils.isBlank(inputCode)){
                    throw new SmsVerifyCodeExpection("验证码不能为空");
                }
                if (!inputCode.equalsIgnoreCase(code)){
                    throw new SmsVerifyCodeExpection("验证码输入错误");
                }
                //验证通过就删除redis中的验证码
                redisTemplate.delete("mobile:"+mobile);

            } catch (AuthenticationException e) {
                //将验证失败的抛出的异常交给自定义认证失败处理器处理异常
                definedAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }

        }
        //放行
        filterChain.doFilter(request,response);

    }
}
