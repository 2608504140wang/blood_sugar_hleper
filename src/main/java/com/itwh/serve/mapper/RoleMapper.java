package com.itwh.serve.mapper;

import com.itwh.pojo.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<SysRole> list(SysRole sysRole);
}
