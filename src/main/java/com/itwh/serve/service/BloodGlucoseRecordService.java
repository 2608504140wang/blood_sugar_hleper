package com.itwh.serve.service;

import com.itwh.pojo.dto.SaveBloodGlucoseRecordDTO;
import com.itwh.pojo.dto.UpdateBloodGlucoseRecordDTO;
import com.itwh.pojo.vo.BloodGlucoseRecordVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface BloodGlucoseRecordService {

    /**
     * 提交血糖记录
     * @param saveBloodGlucoseRecordDTO
     * @return
     */
    void saveBloodGlucoseRecord(SaveBloodGlucoseRecordDTO saveBloodGlucoseRecordDTO);


    /**
     * 修改血糖记录
     * @param updateBloodGlucoseRecordDTO
     * @return
     */
    void updateBloodGlucoseRecord(UpdateBloodGlucoseRecordDTO updateBloodGlucoseRecordDTO);


    /**
     * 删除血糖记录
     * @param ids
     * @return
     */
    void deleteBloodGlucoseRecord(List<Long> ids);


    /**
     * 查看血糖记录
     * @param begin
     * @param end
     * @return
     */
    BloodGlucoseRecordVO listBloodGlucoseRecordByDate(LocalDate begin, LocalDate end);
}
