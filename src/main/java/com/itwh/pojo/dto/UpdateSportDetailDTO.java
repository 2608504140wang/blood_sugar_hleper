package com.itwh.pojo.dto;

import lombok.Data;

@Data
public class UpdateSportDetailDTO {

    //运动信息id
    private Long id;

    //运动名称
    private String name;

    //运动图片
    private String picture;

    //运动类别
    private String type;

    //运动消耗(60分钟) 大卡
    private Double consumption;

}
