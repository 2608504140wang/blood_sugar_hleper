package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.entity.MedicineClock;
import com.itwh.serve.service.MedicineClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/medicine/clock")
public class MedicineClockController {

    @Autowired
    private MedicineClockService medicineClockService;

    /**
     * 新增用药提醒闹钟
     * @param medicineClock
     * @return
     */
    @PostMapping("/save")
    public Result saveMedicineClock(@RequestBody MedicineClock medicineClock){
        medicineClockService.saveMedicineClock(medicineClock);
        return Result.success("用药提醒闹钟设置成功");
    }

    /**
     * 修改用药提醒闹钟
     * @param medicineClock
     * @return
     */
    @PutMapping("/update")
    public Result updateMedicineClock(@RequestBody MedicineClock medicineClock){
        medicineClockService.updateMedicineClock(medicineClock);
        return Result.success("用药提醒闹钟修改成功");
    }

    /**
     * 获取用户所有用药提醒闹钟
     * @return
     */
    @GetMapping("/list")
    public Result listAllMedicineClock(){
        List<MedicineClock> medicineClocks = medicineClockService.listAllMedicineClock();
        return Result.success("用户所有用药提醒闹钟信息获取成功", medicineClocks);
    }

    /**
     * 删除用药提醒闹钟
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteMedicineClock(@PathVariable Long id){
        medicineClockService.deleteMedicineClock(id);
        return Result.success("用药提醒闹钟删除成功");
    }

}
