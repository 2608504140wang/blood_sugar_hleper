package com.itwh.pojo.dto;

import com.itwh.pojo.entity.FoodAndDiet;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateDietRecordDTO {

    //饮食记录id
    private Long id;

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //备注
    private String remark;

    //添加食物集
    private List<FoodAndDiet> addFoodAndDiets;

    //删除食物id集
    private List<Long> removeFoodAndDietIds;

}
