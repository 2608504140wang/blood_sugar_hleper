package com.itwh.serve.mapper;

import com.itwh.pojo.entity.MedicineClock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MedicineClockMapper {

    /**
     * 新增用药提醒闹钟
     * @param medicineClock
     * @return
     */
    void save(MedicineClock medicineClock);

    /**
     * 修改用药提醒闹钟
     * @param medicineClock
     * @return
     */
    void update(MedicineClock medicineClock);

    /**
     * 获取用药提醒闹钟信息
     * @param medicineClock
     * @return
     */
    List<MedicineClock> list(MedicineClock medicineClock);

    /**
     * 删除用药提醒闹钟
     * @param medicineClock
     * @return
     */
    void delete(MedicineClock medicineClock);
}
