package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.entity.MedicineClock;
import com.itwh.serve.mapper.MedicineClockMapper;
import com.itwh.serve.service.MedicineClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineClockServiceImpl implements MedicineClockService {

    @Autowired
    private MedicineClockMapper medicineClockMapper;


    /**
     * 新增用药提醒闹钟
     * @param medicineClock
     * @return
     */
    public void saveMedicineClock(MedicineClock medicineClock) {
        medicineClock.setUserId(BaseContext.getCurrentId());
        medicineClockMapper.save(medicineClock);
    }

    /**
     * 修改用药提醒闹钟
     * @param medicineClock
     * @return
     */
    public void updateMedicineClock(MedicineClock medicineClock) {
        medicineClock.setUserId(BaseContext.getCurrentId());
        medicineClockMapper.update(medicineClock);
    }

    /**
     * 获取用户所有用药提醒闹钟
     * @return
     */
    public List<MedicineClock> listAllMedicineClock() {
        MedicineClock medicineClock = new MedicineClock();
        medicineClock.setUserId(BaseContext.getCurrentId());
        return medicineClockMapper.list(medicineClock);
    }

    /**
     * 删除用药提醒闹钟
     * @param id
     * @return
     */
    public void deleteMedicineClock(Long id) {
        MedicineClock medicineClock = new MedicineClock();
        medicineClock.setUserId(BaseContext.getCurrentId());
        medicineClock.setId(id);
        medicineClockMapper.delete(medicineClock);
    }


}
