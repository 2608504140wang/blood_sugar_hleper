package com.itwh.serve.service;

import com.itwh.pojo.dto.LoginUsernameDTO;
import com.itwh.pojo.dto.RegisterDTO;
import com.itwh.pojo.entity.SysUser;
import com.itwh.pojo.vo.LoginUsernameVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @return
     */
    LoginUsernameVO listByUsername(LoginUsernameDTO loginUsernameDTO);

    /**
     * 用户账号注册 用手机号短信验证码注册账号
     * @param registerDTO
     * @return
     */
    void register(RegisterDTO registerDTO);

    /**
     * 用户手机号短信验证码登录
     * @return
     */
    void logout();

    /**
     * 根据手机号和账号查询用户信息
     * @param user
     * @return
     */
    SysUser listByMobileAndUserName(SysUser user);

    /**
     * 修改密码
     * @param user
     */
    void updatePassword(SysUser user);

    /**
     * 账号注销
     * @return
     */
    void unsubscribeById(Long currentId);

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    SysUser listById(Long id);

    /**
     * 修改用户手机号
     * @param user
     */
    void updateMobile(SysUser user);
}
