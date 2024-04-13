package com.itwh.serve.mapper;

import com.itwh.pojo.entity.MedicineRecord;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MedicineRecordMapper {

    /**
     * 新增用药记录
     * @param medicineRecord
     * @return
     */
    void save(MedicineRecord medicineRecord);

    /**
     * 修改用药记录
     * @param medicineRecord
     * @return
     */
    void update(MedicineRecord medicineRecord);

    /**
     * 获取指定时间内的所有用药记录
     * @param beginTime
     * @param endTime
     * @return
     */
    List<MedicineRecord> listByDate(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 删除用药记录
     * @param medicineRecord
     * @return
     */
    void delete(MedicineRecord medicineRecord);
}
