package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.SaveMedicineRecordDTO;
import com.itwh.pojo.dto.UpdateMedicineRecordDTO;
import com.itwh.pojo.vo.ListMedicineRecordVO;
import com.itwh.serve.service.MedicineRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/customer/medicine_record")
public class MedicineRecordController {

    @Autowired
    private MedicineRecordService medicineRecordService;


    /**
     * 新增用药记录
     * @param saveMedicineRecordDTO
     * @return
     */
    @PostMapping("/save")
    public Result saveMedicineRecord(@RequestBody SaveMedicineRecordDTO saveMedicineRecordDTO){
        medicineRecordService.saveMedicineRecord(saveMedicineRecordDTO);
        return Result.success("用药记录新增成功");
    }

    /**
     * 修改用药记录
     * @param updateMedicineRecordDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateMedicineRecord(@RequestBody UpdateMedicineRecordDTO updateMedicineRecordDTO){
        medicineRecordService.updateMedicineRecord(updateMedicineRecordDTO);
        return Result.success("用药记录修改成功");
    }

    /**
     * 获取指定日期内的用药记录
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/list")
    public Result listMedicineRecord(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        ListMedicineRecordVO listMedicineRecordVO = medicineRecordService.listMedicineRecord(begin, end);
        return Result.success("指定日期内的用药记录信息获取成功", listMedicineRecordVO);
    }

    /**
     * 删除用药记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteMedicineRecord(@PathVariable Long id){
        medicineRecordService.deleteMedicineRecord(id);
        return Result.success("用药记录删除成功");
    }

}
