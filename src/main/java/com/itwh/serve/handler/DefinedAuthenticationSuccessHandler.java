package com.itwh.serve.handler;

import com.itwh.common.constant.JwtClaimsConstant;
import com.itwh.common.properties.JwtProperties;
import com.itwh.common.result.Result;
import com.itwh.common.utils.JwtUtil;
import com.itwh.pojo.entity.LoginUser;
import com.itwh.pojo.vo.LoginUsernameVO;
import com.itwh.serve.mapper.UserAndRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的认证成功处理器
 * 1.决定响应json还是跳转页面，或者认证成功进行其他处理
 */
@Component
public class DefinedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //认证成功后响应json数据给前端
        //认证成功并传递token给前端

        //使用userid生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        //authenticate存入redis
        redisTemplate.opsForValue().set("login:"+userId, loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        //通过用户id去查询用户的角色
        String role = userAndRoleMapper.listRoleByUserId(userId);

        LoginUsernameVO loginUsernameVO = LoginUsernameVO.builder()
                .token(token)
                .role(role)
                .build();
        Result result = new Result(200,"登录认证成功", loginUsernameVO);
        String s = result.toJsonString();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(s);
    }
}
