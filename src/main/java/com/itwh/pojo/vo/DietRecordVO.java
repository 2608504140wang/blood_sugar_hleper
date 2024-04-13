package com.itwh.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietRecordVO {

    //饮食记录id
    private Long id;

    //创建记录时间
    private LocalDateTime createTime;

    //记录人id
    private Long userId;

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //备注
    private String remark;

    //食物集
    private List<FoodDetailVO> foodDetailVOS;

}
