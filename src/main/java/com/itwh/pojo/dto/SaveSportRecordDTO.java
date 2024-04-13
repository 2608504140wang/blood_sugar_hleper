package com.itwh.pojo.dto;

import com.itwh.pojo.entity.SportDetailAndRecord;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaveSportRecordDTO {

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //备注
    private String remark;

    //运动集
    List<SportDetailAndRecord> sportDetailAndRecords;

}
