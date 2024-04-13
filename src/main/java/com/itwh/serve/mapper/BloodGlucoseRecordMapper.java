package com.itwh.serve.mapper;

import com.itwh.pojo.entity.BloodGlucoseRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BloodGlucoseRecordMapper {

    /**
     * 新增血糖记录
     * @param bloodGlucoseRecord
     */
    void save(BloodGlucoseRecord bloodGlucoseRecord);

    /**
     * 修改血糖记录
     * @param bloodGlucoseRecord
     */
    void update(BloodGlucoseRecord bloodGlucoseRecord);

    /**
     * 根据血糖记录id删除血糖记录
     * @param id
     * @return
     */
    @Delete("delete from blood_glucose_record where id=#{id}")
    void delete(Long id);

    /**
     * 查询当天的血糖记录
     * @param beginTime
     * @param endTime
     * @return
     */
    List<BloodGlucoseRecord> list(LocalDateTime beginTime, LocalDateTime endTime);
}
