package com.itwh.serve.service.Impl;

import com.itwh.common.constant.JwtClaimsConstant;
import com.itwh.common.context.BaseContext;
import com.itwh.common.exception.AccountNotFoundException;
import com.itwh.common.exception.DuplicateRegistrationException;
import com.itwh.common.properties.JwtProperties;
import com.itwh.common.utils.JwtUtil;
import com.itwh.pojo.dto.LoginUsernameDTO;
import com.itwh.pojo.dto.RegisterDTO;
import com.itwh.pojo.entity.LoginUser;
import com.itwh.pojo.entity.SysRole;
import com.itwh.pojo.entity.SysUser;
import com.itwh.pojo.entity.UserAndRole;
import com.itwh.pojo.vo.LoginUsernameVO;
import com.itwh.serve.mapper.RoleMapper;
import com.itwh.serve.mapper.UserAndRoleMapper;
import com.itwh.serve.mapper.UserMapper;
import com.itwh.serve.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;

    /**
     * 根据用户名查询用户信息
     * @return
     */
    @Transactional
    public LoginUsernameVO listByUsername(LoginUsernameDTO loginUsernameDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUsernameDTO.getUserName(),loginUsernameDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //authenticate(用户信息)存入redis
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        redisTemplate.opsForValue().set("login:"+userId, loginUser);

        //使用userid生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        //通过用户id去查询用户的角色
        String role = userAndRoleMapper.listRoleByUserId(Long.parseLong(userId));

        LoginUsernameVO loginUsernameVO = LoginUsernameVO.builder()
                .token(token)
                .role(role)
                .build();
        return loginUsernameVO;
    }

    /**
     * 用户账号注册 用手机号短信验证码注册账号
     * @param registerDTO
     * @return
     */
    @Transactional
    public void register(RegisterDTO registerDTO) {

        SysUser user = new SysUser();
        BeanUtils.copyProperties(registerDTO, user);

        //先检查该手机号是否注册过账号，一个手机号只能注册一个账号
        SysUser user1 = new SysUser();
        user1.setMobile(registerDTO.getMobile());
        List<SysUser> list = userMapper.list(user1);
        if (list.size() > 0){
            throw new DuplicateRegistrationException("该手机号已注册过账号，请使用已有账号");
        }

        //生成不重复的账号
        SysUser user2 = new SysUser();
        String userName;
        while (true) {
            userName = getNumber(9);
            user2.setUserName(userName);
            List<SysUser> list1 = userMapper.list(user1);
            if (list1.size() == 0 || list1 == null){
                break;
            }
        }
        user.setUserName(userName);

        //密码加密储存
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        //账号注册时默认状态正常
        user.setStatus(1);

        //设置软删除为0
        user.setIsDelete(0);

        //默认头像
        user.setAvatar("https://veteran-takeout.oss-cn-beijing.aliyuncs.com/d0d64e6d-f6dd-40b5-b1eb-70e1af7daab5.jpg");

        userMapper.save(user);

        //设置该用户的角色
        //获取角色的id
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(registerDTO.getRoleName());
        List<SysRole> roles = roleMapper.list(sysRole);
        if (roles == null || roles.size() == 0){
            throw new RuntimeException("该种角色不存在");
        }
        sysRole = roles.get(0);
        UserAndRole userAndRole = UserAndRole.builder()
                .userId(user.getId())
                .roleId(sysRole.getId())
                .build();
        userAndRoleMapper.save(userAndRole);

    }

    /**
     * 用户手机号短信验证码登录
     * @return
     */
    @Transactional
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        //从redis中删除用户信息
        redisTemplate.delete("login:"+ userid);
    }

    /**
     * 根据手机号和账号查询用户信息
     * @param user
     * @return
     */
    @Transactional
    public SysUser listByMobileAndUserName(SysUser user) {
        //查询结果只可能是一个或零个
        List<SysUser> list = userMapper.list(user);
        if(list == null || list.size() == 0){
            throw new AccountNotFoundException("该账号不存在");
        }
        SysUser user1 = list.get(0);
        //判断用户是否被删除
        if (user1.getIsDelete() == 1){
            throw new AccountNotFoundException("该账号不存在");
        }
        return user1;
    }

    /**
     * 修改密码
     * @param user
     */
    @Transactional
    public void updatePassword(SysUser user) {
        userMapper.update(user);

        redisTemplate.delete("login:"+ BaseContext.getCurrentId());
    }

    /**
     * 账号注销
     * @return
     */
    @Transactional
    public void unsubscribeById(Long currentId) {
        userMapper.unsubscribeById(currentId);
    }

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    public SysUser listById(Long id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        List<SysUser> list = userMapper.list(sysUser);
        //只可能存在1个或0个
        return list.get(0);
    }

    /**
     * 修改用户手机号
     * @param user
     */
    public void updateMobile(SysUser user) {
        userMapper.update(user);
    }

    /**
     * 随机生成长为length的纯数字字符串
     * @param length
     * @return
     */
    public static String getNumber(Integer length) {
        String uid = "";
        for (Integer i = 0; i < length; i++) {
            String randChar = String.valueOf(Math.round(Math.random() * 9));
            uid = uid.concat(randChar);
        }
        return uid;
    }
}
