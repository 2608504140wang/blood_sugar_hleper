package com.itwh.serve.controller;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import com.itwh.common.context.BaseContext;
import com.itwh.common.result.Result;
import com.itwh.common.utils.AliOssUtils;
import com.itwh.pojo.dto.*;
import com.itwh.pojo.entity.SysUser;
import com.itwh.pojo.vo.LoginUsernameVO;
import com.itwh.serve.service.SmsSendService;
import com.itwh.serve.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsSendService smsSendService;

    @Autowired
    private ImageCaptchaApplication imageCaptchaApplication;

    @Autowired
    private AliOssUtils aliOssUtils;

    /**
     * 用户账号密码登录
     * @return
     */
    @PostMapping("/login/username")
    public Result loginByUsername(@RequestBody LoginUsernameDTO loginUsernameDTO){

        LoginUsernameVO loginUsernameVO = userService.listByUsername(loginUsernameDTO);
        return Result.success("登录认证成功", loginUsernameVO);
    }

    /**
     * 用户账号登出
     * @return
     */
    @PostMapping("/logout")
    public Result logout(){
        userService.logout();

        return Result.success("用户账号登出成功！");
    }

    /**
     * 用户账号注册 用手机号短信验证码注册账号
     * @param registerDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO, HttpServletRequest request){
        registerDTO.setMobile(request.getHeader("mobile"));
        userService.register(registerDTO);

        return Result.success("注册成功");
    }

    /**
     * 账号注销（软删除）
     * @return
     */
    @DeleteMapping("/unsubscribe")
    public Result writeOff(){
        Long currentId = BaseContext.getCurrentId();
        userService.unsubscribeById(currentId);
        return Result.success("账号注销成功");
    }

    /**
     * 获取短信验证码
     * @param mobile
     * @param request
     * @return
     */
    @GetMapping("/code/mobile")
    public Result smsCodeSend(String mobile, HttpServletRequest request){
        return smsSendService.sendSms(mobile, request);
    }

    /**
     * 获取行为验证码
     * @param type
     * @return
     */
    @GetMapping ("/captcha/get")
    public Result genCaptcha(String type) {
        if (StringUtils.isBlank(type)) {
            type = CaptchaTypeConstant.SLIDER;
        }
        if ("RANDOM".equals(type)) {
            int i = ThreadLocalRandom.current().nextInt(0, 4);
            if (i == 0) {
                type = CaptchaTypeConstant.SLIDER;
            } else if (i == 1) {
                type = CaptchaTypeConstant.CONCAT;
            } else if (i == 2) {
                type = CaptchaTypeConstant.ROTATE;
            } else{
                type = CaptchaTypeConstant.WORD_IMAGE_CLICK;
            }
        }
        //生成验证码并将该验证码相关信息返回给前端
        CaptchaResponse<ImageCaptchaVO> response = imageCaptchaApplication.generateCaptcha(type);
        return Result.success(response);
    }

    /**
     * 行为验证码检验
     * @param captchaData
     * @return
     */
    @PostMapping("/captcha/check")
    public Result checkCaptcha(@RequestBody CaptchaData captchaData){
        ApiResponse<?> response = imageCaptchaApplication.matching(captchaData.getId(), captchaData.getData());
        if (response.isSuccess()) {
            return Result.success(ApiResponse.ofSuccess(Collections.singletonMap("id", captchaData.getId())));
        }
        return Result.success(response);
    }

    /**
     * 忘记密码(没登录时修改密码)
     * @return
     */
    @PutMapping("/password/update1")
    public Result updatePassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO){
        if (!forgetPasswordDTO.getNewPassword1().equals(forgetPasswordDTO.getNewPassword2())){
            return new Result("两次密码输入不一致，请检查后重新输入");
        }
        SysUser user = SysUser.builder()
                .userName(forgetPasswordDTO.getUserName())
                .build();
        //查找该用户
        user = userService.listByMobileAndUserName(user);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(forgetPasswordDTO.getNewPassword1()));
        userService.updatePassword(user);
        return Result.success("密码修改成功，请重新登录。");
    }

    /**
     * 登录后修改密码
     * @param updatePasswordDTO
     * @return
     */
    @PutMapping("/password/update2")
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){
        if (!updatePasswordDTO.getNewPassword1().equals(updatePasswordDTO.getNewPassword2())){
            return new Result("两次密码输入不一致，请检查后重新输入");
        }
        //查找该用户
        SysUser user = userService.listById(BaseContext.getCurrentId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(updatePasswordDTO.getNewPassword1()));
        userService.updatePassword(user);
        return Result.success("密码修改成功，请重新登录。");
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file){
        String fileName = aliOssUtils.uploadOneFile(file);
        if (fileName != null){
            return Result.success("头像上传成功",fileName);
        }else {
            return Result.success("头像上传失败");
        }
    }

    /**
     * 用户修改手机号
     * @return
     */
    @PutMapping("/mobile/update")
    public Result updateMobile(HttpServletRequest request){
        SysUser user = SysUser.builder()
                .mobile(request.getHeader("mobile"))
                .id(BaseContext.getCurrentId())
                .build();
        userService.updateMobile(user);
        return Result.success("绑定的手机号修改成功");
    }

}
