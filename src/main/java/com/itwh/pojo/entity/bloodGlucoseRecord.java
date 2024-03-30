package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class bloodGlucoseRecord {

    private Long id;

    //血糖值
    private Long glucoseLevel;

    //记录时间
    private LocalDateTime recordTime;

    //记录人id
    private Long recordUser;

    //时段标签
    private String periodLabel;

    //备注
    private String remark;

}
