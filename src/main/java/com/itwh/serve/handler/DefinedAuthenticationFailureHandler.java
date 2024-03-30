package com.itwh.serve.handler;

import com.itwh.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理器
 */
@Component
public class DefinedAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 设置 HTTP 响应的内容类型为json格式数据
        response.setContentType("application/json;charset=UTF-8");

        //认证失败后响应json数据给前端
        Result result = Result.error(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        String s = result.toJsonString();
        response.getWriter().write(s);
    }

}