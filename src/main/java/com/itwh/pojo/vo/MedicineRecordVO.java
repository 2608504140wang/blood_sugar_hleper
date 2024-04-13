package com.itwh.pojo.vo;

import com.itwh.pojo.entity.TempMedicine;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MedicineRecordVO {

    //用药记录id
    private Long id;

    //记录人id
    private Long userId;

    //时段标签
    private String periodLabel;

    //记录时间
    private LocalDateTime recordTime;

    //创建记录时间
    private LocalDateTime createTime;

    //使用的药品集
    private List<TempMedicine> tempMedicines;

}
