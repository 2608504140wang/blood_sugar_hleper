package com.itwh.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 短信验证码异常类
 */
public class SmsVerifyCodeExpection extends AuthenticationException {
    public SmsVerifyCodeExpection(String msg, Throwable t) {
        super(msg, t);
    }

    public SmsVerifyCodeExpection(String msg) {
        super(msg);
    }
}
