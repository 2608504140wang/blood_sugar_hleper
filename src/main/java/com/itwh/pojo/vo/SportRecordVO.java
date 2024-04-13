package com.itwh.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SportRecordVO {

    //运动记录id
    private Long id;

    //记录人id
    private Long userId;

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //创建记录时间
    private LocalDateTime createTime;

    //备注
    private String remark;

    //运动集
    private List<SportDetailVO> sportDetailVOS;

}
