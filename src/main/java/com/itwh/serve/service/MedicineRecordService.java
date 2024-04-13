package com.itwh.serve.service;

import com.itwh.pojo.dto.SaveMedicineRecordDTO;
import com.itwh.pojo.dto.UpdateMedicineRecordDTO;
import com.itwh.pojo.vo.ListMedicineRecordVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface MedicineRecordService {

    /**
     * 新增用药记录
     * @param saveMedicineRecordDTO
     * @return
     */
    void saveMedicineRecord(SaveMedicineRecordDTO saveMedicineRecordDTO);

    /**
     * 修改用药记录
     * @param updateMedicineRecordDTO
     * @return
     */
    void updateMedicineRecord(UpdateMedicineRecordDTO updateMedicineRecordDTO);

    /**
     * 获取指定日期内的用药记录
     * @param begin
     * @param end
     * @return
     */
    ListMedicineRecordVO listMedicineRecord(LocalDate begin, LocalDate end);

    /**
     * 删除用药记录
     * @param id
     * @return
     */
    void deleteMedicineRecord(Long id);
}
