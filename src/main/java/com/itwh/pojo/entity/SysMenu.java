package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //权限名
    private String menuName;

    //权限创建时间
    private LocalDateTime createTime;

    //权限修改时间
    private LocalDateTime updateTime;

    //权限创建人
    private Long createUser;

    //权限修改人
    private Long updateUser;

}
