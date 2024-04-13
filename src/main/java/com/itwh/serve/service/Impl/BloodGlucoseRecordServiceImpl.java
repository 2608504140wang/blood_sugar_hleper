package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.dto.SaveBloodGlucoseRecordDTO;
import com.itwh.pojo.dto.UpdateBloodGlucoseRecordDTO;
import com.itwh.pojo.entity.BloodGlucoseRecord;
import com.itwh.pojo.vo.BloodGlucoseRecordVO;
import com.itwh.serve.mapper.BloodGlucoseRecordMapper;
import com.itwh.serve.service.BloodGlucoseRecordService;
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
public class BloodGlucoseRecordServiceImpl implements BloodGlucoseRecordService {


    @Autowired
    private BloodGlucoseRecordMapper bloodGlucoseRecordMapper;

    /**
     * 提交血糖记录
     * @param saveBloodGlucoseRecordDTO
     * @return
     */
    public void saveBloodGlucoseRecord(SaveBloodGlucoseRecordDTO saveBloodGlucoseRecordDTO) {
        BloodGlucoseRecord bloodGlucoseRecord = new BloodGlucoseRecord();
        BeanUtils.copyProperties(saveBloodGlucoseRecordDTO, bloodGlucoseRecord);
        bloodGlucoseRecord.setRecordUser(BaseContext.getCurrentId());
        bloodGlucoseRecord.setCreateTime(LocalDateTime.now());

        bloodGlucoseRecordMapper.save(bloodGlucoseRecord);

    }

    /**
     * 修改血糖记录
     * @param updateBloodGlucoseRecordDTO
     * @return
     */
    public void updateBloodGlucoseRecord(UpdateBloodGlucoseRecordDTO updateBloodGlucoseRecordDTO) {
        BloodGlucoseRecord bloodGlucoseRecord = new BloodGlucoseRecord();
        BeanUtils.copyProperties(updateBloodGlucoseRecordDTO, bloodGlucoseRecord);
        bloodGlucoseRecord.setRecordUser(BaseContext.getCurrentId());

        bloodGlucoseRecordMapper.update(bloodGlucoseRecord);
    }

    /**
     * 删除血糖记录
     * @param ids
     * @return
     */
    public void deleteBloodGlucoseRecord(List<Long> ids) {
        for (Long id : ids){
            bloodGlucoseRecordMapper.delete(id);
        }
    }

    /**
     * 查看血糖记录
     * @param begin
     * @param end
     * @return
     */
    public BloodGlucoseRecordVO listBloodGlucoseRecordByDate(LocalDate begin, LocalDate end) {
        //存放从begin到end的所有日期
        List<LocalDate> localDateList = new ArrayList<>();

        localDateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }

        //存放每天的血糖记录信息
        List<List<BloodGlucoseRecord>> bloodGlucoseRecordsList = new ArrayList<>();
        for (LocalDate date : localDateList){
            //每天的开始和结束时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            List<BloodGlucoseRecord> bloodGlucoseRecords = bloodGlucoseRecordMapper.list(beginTime, endTime);
            bloodGlucoseRecordsList.add(bloodGlucoseRecords);
        }

        return BloodGlucoseRecordVO.builder()
                .localDateList(localDateList)
                .bloodGlucoseRecordsList(bloodGlucoseRecordsList)
                .build();
    }
}
