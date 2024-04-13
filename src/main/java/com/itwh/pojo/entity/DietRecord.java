package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 饮食记录表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietRecord {

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
}
