package com.itwh.pojo.dto;

import lombok.Data;

@Data
public class UpdateMedicineDetailDTO {

    private Long id;

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
