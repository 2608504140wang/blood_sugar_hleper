package com.itwh.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 行为验证码异常类
 */
public class CheckCaptchaException extends AuthenticationException {
    public CheckCaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public CheckCaptchaException(String msg) {
        super(msg);
    }
}
