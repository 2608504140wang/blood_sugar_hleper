package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodAndDiet {

    private Long id;

    //食物id
    private Long foodId;

    //饮食记录id
    private Long dietId;

    //食物克数
    private Long foodNum;

}
