package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单个用户的常用药品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDetail {

    private Long id;

    //所属用户id
    private Long userId;

    //药品名称
    private String name;

    //药品用途
    private String application;

    //药品照片
    private String picture;

    //药品剂量
    private String dosage;

    //备注
    private String remark;

}
