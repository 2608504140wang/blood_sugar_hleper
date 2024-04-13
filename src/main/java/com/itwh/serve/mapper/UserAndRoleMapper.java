package com.itwh.serve.mapper;

import com.itwh.pojo.entity.UserAndRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAndRoleMapper {

    /**
     * 增加用户角色关联
     * @param userAndRole
     */
    void save(UserAndRole userAndRole);


    /**
     * 根据用户id查询用户的角色
     *
     * @param userId
     */
    String listRoleByUserId(Long userId);


    /**
     * 获取所有用户的id
     *
     * @param role
     * @return
     */
    List<Long> listUserIdByRole(String role);
}
