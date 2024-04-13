package com.itwh.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 每天一一对应
 */
@Data
@Builder
public class ListDietRecordVO {

    //从begin到end每天日期的集合
    private List<LocalDate> localDateList;

    //每天的数据
    private List<List<DietRecordVO>> dietRecordVOList;
}
