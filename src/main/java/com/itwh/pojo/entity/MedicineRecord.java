package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用药记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRecord {

    private Long id;

    //记录人id
    private Long userId;

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //创建记录时间
    private LocalDateTime createTime;

}