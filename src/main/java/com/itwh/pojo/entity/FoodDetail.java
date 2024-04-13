package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 食物分析表(每100克含量) 客户和管理员都能加，共享数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetail {

    private Long id;

    //食物名称
    private String name;

    //食物图片
    private String picture;

    //食物类别
    private String type;

    //GI(升糖指数)
    private Double GI;

    //GL(升糖负荷)
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

    //管理员审核结果 0审核拒绝，1审核通过，2待审核
    private Long pass;

}
