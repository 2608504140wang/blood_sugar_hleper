package com.itwh.serve.service;

import com.itwh.pojo.dto.SaveDietRecordDTO;
import com.itwh.pojo.dto.UpdateDietRecordDTO;
import com.itwh.pojo.vo.DietRecordVO;
import com.itwh.pojo.vo.ListDietRecordVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DietRecordService {

    /**
     * 提交饮食记录
     * @return
     */
    void saveDietRecord(SaveDietRecordDTO saveDietRecordDTO);

    /**
     * 修改饮食记录
     * @param updateDietRecordDTO
     * @return
     */
    void updateDietRecord(UpdateDietRecordDTO updateDietRecordDTO);

    /**
     * 查询指定时间范围内的饮食记录
     * @param begin
     * @param end
     * @return
     */
    ListDietRecordVO listDietRecordByDate(LocalDate begin, LocalDate end);

    /**
     * 根据饮食记录id删除饮食记录
     * @param id
     * @return
     */
    void deleteDietRecord(Long id);
}
