package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.dto.SaveMedicineRecordDTO;
import com.itwh.pojo.dto.UpdateMedicineRecordDTO;
import com.itwh.pojo.entity.MedicineRecord;
import com.itwh.pojo.entity.TempMedicine;
import com.itwh.pojo.vo.ListMedicineRecordVO;
import com.itwh.pojo.vo.MedicineRecordVO;
import com.itwh.serve.mapper.MedicineRecordMapper;
import com.itwh.serve.mapper.TempMedineMapper;
import com.itwh.serve.service.MedicineRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MedicineRecordServiceImpl implements MedicineRecordService {

    @Autowired
    private MedicineRecordMapper medicineRecordMapper;

    @Autowired
    private TempMedineMapper tempMedineMapper;

    /**
     * 新增用药记录
     * @param saveMedicineRecordDTO
     * @return
     */
    public void saveMedicineRecord(SaveMedicineRecordDTO saveMedicineRecordDTO) {
        //新增用药记录
        MedicineRecord medicineRecord = new MedicineRecord();
        BeanUtils.copyProperties(saveMedicineRecordDTO, medicineRecord);
        medicineRecord.setUserId(BaseContext.getCurrentId());
        medicineRecord.setCreateTime(LocalDateTime.now());
        medicineRecordMapper.save(medicineRecord);

        //添加记录对应的药品
        List<TempMedicine> tempMedicines = saveMedicineRecordDTO.getTempMedicines();
        tempMedicines.forEach(tempMedicine -> tempMedicine.setRecordId(medicineRecord.getId()));
        tempMedineMapper.saveBatch(tempMedicines);
    }

    /**
     * 修改用药记录
     * @param updateMedicineRecordDTO
     * @return
     */
    public void updateMedicineRecord(UpdateMedicineRecordDTO updateMedicineRecordDTO) {
        //修改用药记录
        MedicineRecord medicineRecord = new MedicineRecord();
        BeanUtils.copyProperties(updateMedicineRecordDTO, medicineRecord);
        medicineRecord.setUserId(BaseContext.getCurrentId());
        medicineRecordMapper.update(medicineRecord);

        //添加记录对应的药品
        List<TempMedicine> addTempMedicines = updateMedicineRecordDTO.getAddTempMedicines();
        if (addTempMedicines != null && addTempMedicines.size() > 0){
            addTempMedicines.forEach(tempMedicine -> tempMedicine.setRecordId(updateMedicineRecordDTO.getId()));
            tempMedineMapper.saveBatch(addTempMedicines);
        }

        //删除记录对应的药品
        List<Long> removeIds = updateMedicineRecordDTO.getRemoveIds();
        if (removeIds != null && removeIds.size() > 0){
            tempMedineMapper.delete(removeIds);
        }
    }

    /**
     * 获取指定日期内的用药记录
     * @param begin
     * @param end
     * @return
     */
    public ListMedicineRecordVO listMedicineRecord(LocalDate begin, LocalDate end) {
        //存放从begin到end的所有日期
        List<LocalDate> localDateList = new ArrayList<>();

        localDateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }

        //存放每天的用药记录
        List<List<MedicineRecordVO>> medicineRecordVOList = new ArrayList<>();
        for (LocalDate date : localDateList){
            //循环每天，获取每一天的用药记录
            List<MedicineRecordVO> medicineRecordVOS = new ArrayList<>();
            //每天的开始和结束时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            List<MedicineRecord> medicineRecords = medicineRecordMapper.listByDate(beginTime, endTime);
            //复制药品记录信息
            for (MedicineRecord medicineRecord : medicineRecords){
                MedicineRecordVO medicineRecordVO = new MedicineRecordVO();
                BeanUtils.copyProperties(medicineRecord, medicineRecordVO);
                medicineRecordVOS.add(medicineRecordVO);
            }

            //遍历一天中的每一天用药记录，获取所有使用的药品
            for (MedicineRecordVO medicineRecordVO : medicineRecordVOS){
                TempMedicine tempMedicine = new TempMedicine();
                tempMedicine.setRecordId(medicineRecordVO.getId());
                List<TempMedicine> tempMedicines = tempMedineMapper.listByRecordId(tempMedicine);
                medicineRecordVO.setTempMedicines(tempMedicines);
            }
            //加入某天的所有用药记录
            medicineRecordVOList.add(medicineRecordVOS);
        }
        return ListMedicineRecordVO.builder()
                .localDateList(localDateList)
                .MedicineRecordVOList(medicineRecordVOList)
                .build();
    }

    /**
     * 删除用药记录
     * @param id
     * @return
     */
    public void deleteMedicineRecord(Long id) {
        //删除用药记录
        MedicineRecord medicineRecord = new MedicineRecord();
        medicineRecord.setId(id);
        medicineRecordMapper.delete(medicineRecord);

        //删除该用药记录对应的药品记录
        tempMedineMapper.deleteByRecordId(id);
    }

}
