package com.itwh.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateFoodDetailDTO {

    private Long id;

    //食物名称
    private String name;

    //食物图片
    private String picture;

    //食物类别
    private String type;

    //GI(升糖指数)
    @JsonProperty("GI")
    private Double GI;

    //GL(升糖负荷)
    @JsonProperty("GL")
    private Double GL;

    //含糖量 克
    private Double glucose;

    //热量 大卡
    private Double heat;

    //蛋白质 克
    private Double protein;

    //脂肪 克
    private Double fat;

    //碳水化合物 克
    private Double carbohydrate;

}
