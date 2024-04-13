package com.itwh.pojo.dto;

import com.itwh.pojo.entity.FoodAndDiet;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaveDietRecordDTO {

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //备注
    private String remark;

    //食物集
    private List<FoodAndDiet> foodAndDiets;

}
