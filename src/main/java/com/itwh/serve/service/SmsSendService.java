package com.itwh.serve.service;

import com.itwh.common.result.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 短信发送接口
 */
@Service
public interface SmsSendService {

    /**
     * 短信发送
     * @param mobile 手机号
     * @return
     */
    Result sendSms(String mobile, HttpServletRequest request);
}
