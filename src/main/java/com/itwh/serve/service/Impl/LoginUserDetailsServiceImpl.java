package com.itwh.serve.service.Impl;

import com.itwh.common.constant.MessageConstant;
import com.itwh.common.exception.AccountNotFoundException;
import com.itwh.pojo.entity.LoginUser;
import com.itwh.pojo.entity.SysUser;
import com.itwh.serve.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        SysUser user = new SysUser();
        user.setUserName(userName);
        List<SysUser> list = userMapper.list(user);
        //如果查询不到,数据就通过抛出异常来给出提示
        if(list.size() == 0 || list == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //判断用户是否删除了
        if (list.get(0).getIsDelete() == 1){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //如果查到了，封装成LoginUser对象返回
        LoginUser loginUser = new LoginUser(list.get(0));

        //TODO 根据用户查询权限信息 添加到loginUser中


        return loginUser;
    }

}
