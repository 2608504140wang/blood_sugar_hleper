package com.itwh.pojo.dto;

import com.itwh.pojo.entity.TempMedicine;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaveMedicineRecordDTO {

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //药品
    private List<TempMedicine> tempMedicines;

}
