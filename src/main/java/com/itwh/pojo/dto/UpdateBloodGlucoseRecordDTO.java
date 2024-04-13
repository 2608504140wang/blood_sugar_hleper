package com.itwh.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UpdateBloodGlucoseRecordDTO {

    //血糖记录id
    private Long id;

    //血糖值
    private Long glucoseLevel;

    //记录时间
    private LocalDateTime recordTime;

    //时段标签
    private String periodLabel;

    //备注
    private String remark;
}
