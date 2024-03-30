package com.itwh.serve.provider;

import com.itwh.serve.token.MobileAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 实现手机认证提供者 MobileAuthenticationProvider提供给底层 ProviderManager 使用
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 认证处理：
     * 1. 通过 手机号 去数据库查询用户信息(UserDeatilsService)
     * 2. 再重新构建认证信息
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;

        // 获取用户输入的手机号
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        // 查询数据库
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        //未查询到用户信息
        if (userDetails == null) {
            throw new AuthenticationServiceException("该手机未注册");
        }

        // 查询到了用户信息, 则认证通过,就重新构建 MobileAuthenticationToken 实例
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationToken;

    }

    /**
     * 通过此方法,来判断 采用哪一个 AuthenticationProvider
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
