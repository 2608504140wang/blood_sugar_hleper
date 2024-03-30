package com.itwh.serve.service.Impl;

import com.itwh.common.exception.AccountNotFoundException;
import com.itwh.pojo.entity.LoginUser;
import com.itwh.pojo.entity.SysUser;
import com.itwh.serve.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通过手机号获取用户信息和权限信息
 */
@Component
public class MobileUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // 1. 通过手机号查询用户信息（查询数据库）
        SysUser user = new SysUser();
        user.setMobile(mobile);

        //只可能有1个或0个用户
        List<SysUser> list = userMapper.list(user);

        if (list == null || list.size() == 0){
            throw new AccountNotFoundException("找不到对应手机号的用户");
        }
        SysUser user1 = list.get(0);
        //判断用户是否被删除了
        if (user1.getIsDelete() == 1){
            throw new AccountNotFoundException("找不到对应手机号的用户");
        }
        LoginUser loginUser = new LoginUser(user1);

        //todo 2. 如果有此用户，则查询用户权限并封装到loginUser


        //3. 将用户信息loginUser存入redis, 便于下次用户带token查询信息
        String userId = loginUser.getUser().getId().toString();
        redisTemplate.opsForValue().set("login:"+userId, loginUser);


        // 4. 封装用户信息
        return loginUser;
    }
}