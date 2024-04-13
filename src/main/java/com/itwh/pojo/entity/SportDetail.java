package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportDetail {

    private Long id;

    //运动名称
    private String name;

    //运动图片
    private String picture;

    //运动类别
    private String type;

    //运动消耗(60分钟) 大卡
    private Double consumption;

    //管理员审核结果 0审核拒绝，1审核通过，2待审核
    private Long pass;

}
