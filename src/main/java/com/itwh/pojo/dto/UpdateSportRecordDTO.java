package com.itwh.pojo.dto;

import com.itwh.pojo.entity.SportDetailAndRecord;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateSportRecordDTO {

    //运动记录id
    private Long id;

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //备注
    private String remark;

    //添加运动集
    private List<SportDetailAndRecord> addSportDetailAndRecords;

    //删除运动id集
    private List<Long> removeSportDetailAndRecords;

}
