package com.itwh.serve.mapper;

import com.itwh.pojo.entity.UserAndRole;
import org.apache.ibatis.annotations.Mapper;

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
    String listRoleByUserId(String userId);

}
