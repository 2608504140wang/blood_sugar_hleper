package com.itwh.serve.handler;

import com.alibaba.fastjson.JSON;
import com.itwh.common.result.Result;
import com.itwh.common.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new  Result(HttpStatus.UNAUTHORIZED.value(), "登录认证失败，请重新登录！");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}

