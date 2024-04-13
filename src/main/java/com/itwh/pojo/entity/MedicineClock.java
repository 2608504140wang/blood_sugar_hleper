package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineClock {

    private Long id;

    //所属用户id
    private Long userId;

    //药品名称
    private String name;

    //药品用途
    private String application;

    //药品照片
    private String picture;

    //药品用量
    private String dosage;

    //备注
    private String remark;

    //提醒时间
    private LocalDateTime clock;

}
