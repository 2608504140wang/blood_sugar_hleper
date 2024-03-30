package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //账号
    private String userName;

    //密码
    private String password;

    //昵称
    private String nickName;

    //姓名
    private String name;

    //手机号
    private String mobile;

    //性别
    private String gender;

    //账号状态
    private Integer status;

    //软删除 0表示没删，1表示删了
    private Integer isDelete;

    //头像
    private String avatar;

    //账号创建时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //账号修改时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    //账号创建人 0表示当前账号本身
    private Long createUser;

    //账号修改人 0表示当前账号本身
    private Long updateUser;
}
