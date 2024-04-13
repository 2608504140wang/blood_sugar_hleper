package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempMedicine {

    private Long id;

    //药品记录id
    private Long recordId;

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
