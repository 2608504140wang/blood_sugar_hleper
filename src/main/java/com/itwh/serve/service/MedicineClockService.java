package com.itwh.serve.service;

import com.itwh.pojo.entity.MedicineClock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineClockService {

    /**
     * 新增用药提醒闹钟
     * @param medicineClock
     * @return
     */
    void saveMedicineClock(MedicineClock medicineClock);

    /**
     * 修改用药提醒闹钟
     * @param medicineClock
     * @return
     */
    void updateMedicineClock(MedicineClock medicineClock);

    /**
     * 获取用户所有用药提醒闹钟
     * @return
     */
    List<MedicineClock> listAllMedicineClock();

    /**
     * 删除用药提醒闹钟
     * @param id
     * @return
     */
    void deleteMedicineClock(Long id);
}
