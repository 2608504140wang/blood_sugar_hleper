package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //角色名
    private String roleName;

    //角色状态 0为锁定，1为正常
    private Integer status;

    //角色创建时间
    private LocalDateTime createTime;

    //角色修改时间
    private LocalDateTime updateTime;

    //角色创建人
    private Long createUser;

    //角色修改人
    private Long updateUser;
}
