package com.itwh.serve.mapper;

import com.itwh.common.annotation.AutoFillUser;
import com.itwh.common.enumeration.OperationType;
import com.itwh.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 查询用户信息
     * @param user
     * @return
     */
    List<SysUser> list(SysUser user);

    /**
     * 新增用户
     * @param user
     */
    @AutoFillUser(value = OperationType.INSERT)
    void save(SysUser user);

    /**
     * 修改用户信息
     * @param user
     */
    @AutoFillUser(value = OperationType.UPDATE)
    void update(SysUser user);

    /**
     * 账号注销
     * @return
     */
    @AutoFillUser(value = OperationType.UPDATE)
    @Update("update sys_user set is_delete = 1, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    void unsubscribeById(Long id);
}
