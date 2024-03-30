package com.itwh.serve.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 封装手机认证Token
 *  实现同UsernamePasswordAuthenticationToken
 */
public  class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    //认证之前存放机号，认证之后放用户信息
    private final Object principal;
    /**
     * 开始认证时,创建一个MobileAuthenticationToken实例 接收的是手机号码, 并且标识未认证
     * @param principal 手机号
     */
    public MobileAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;  // 手机号
        this.setAuthenticated(false);
    }

    /**
     * 当认证通过后,会重新创建一个新的MobileAuthenticationToken,来标识它已经认证通过,
     *
     * @param principal   用户信息
     * @param authorities 用户权限
     */
    public MobileAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;// 用户信息
        super.setAuthenticated(true);  // 标识已经认证通过
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
