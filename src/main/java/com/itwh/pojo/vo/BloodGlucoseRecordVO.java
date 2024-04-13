package com.itwh.pojo.vo;

import com.itwh.pojo.entity.BloodGlucoseRecord;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BloodGlucoseRecordVO {
    //从begin到end每天日期的集合
    private List<LocalDate> localDateList;

    //每天的数据
    private List<List<BloodGlucoseRecord>> bloodGlucoseRecordsList;

}
