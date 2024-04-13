package com.itwh.pojo.dto;

import lombok.Data;

@Data
public class SaveSportDetailDTO {

    //运动名称
    private String name;

    //运动类别
    private String type;

    //运动图片
    private String picture;

    //运动消耗(60分钟) 大卡
    private Double consumption;

}
