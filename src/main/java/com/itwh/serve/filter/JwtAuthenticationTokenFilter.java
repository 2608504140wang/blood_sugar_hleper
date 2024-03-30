package com.itwh.serve.filter;

import com.itwh.common.constant.JwtClaimsConstant;
import com.itwh.common.context.BaseContext;
import com.itwh.common.properties.JwtProperties;
import com.itwh.common.utils.JwtUtil;
import com.itwh.pojo.entity.LoginUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(jwtProperties.getTokenName());
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return; //防止响应回来会接着执行下面的代码
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            userid = (String) claims.get(JwtClaimsConstant.USER_ID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(redisKey);

        //redis中所存的token对应的userid过期了，即登录超时
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录或登录超时，请重新登录！");
        }
        BaseContext.setCurrentId(loginUser.getUser().getId());

        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request, response);
    }
}
