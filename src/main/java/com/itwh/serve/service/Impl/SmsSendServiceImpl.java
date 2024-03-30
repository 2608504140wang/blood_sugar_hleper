package com.itwh.serve.service.Impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.itwh.common.properties.SmsProperties;
import com.itwh.common.result.Result;
import com.itwh.serve.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    public Result sendSms(String mobile, HttpServletRequest request) {
        //1.生成一个6位数手机短信验证码
        String code = RandomUtil.randomNumbers(6);

        //2.将验证码存到redis中 ,验证码只有60秒有效
        redisTemplate.opsForValue().set("mobile:"+ mobile, code, 60L, TimeUnit.SECONDS);

        //3.发送短信
        SendSmsResponse response = null;
        try {
            response = toSendSms(mobile, code);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
        if (response.getCode().equals("OK")){
            return Result.success("获取验证码成功", code);
        }else {
            return Result.error(500,"获取验证码失败");
        }
    }

    public  SendSmsResponse toSendSms(String PhoneNumbers, String code) throws ClientException {

        //设置Java网络连接的默认超时时间,在指定的时间内没有建立连接或读取数据，那么将会抛出一个超时异常。（毫秒）
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint(smsProperties.getRegionId(), smsProperties.getRegionId(), smsProperties.getProduct(), smsProperties.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(PhoneNumbers);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(smsProperties.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsProperties.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发送短信失败");
        }
        return sendSmsResponse;
    }
}
