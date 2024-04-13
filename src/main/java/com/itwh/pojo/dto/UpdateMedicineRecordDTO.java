package com.itwh.pojo.dto;

import com.itwh.pojo.entity.TempMedicine;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateMedicineRecordDTO {

    //用药记录id
    private Long id;

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //新增药品集
    private List<TempMedicine> addTempMedicines;

    //删除药品tempMedicine的id集
    private List<Long> removeIds;
}
