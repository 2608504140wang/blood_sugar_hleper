package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.UpdateMedicineDetailDTO;
import com.itwh.pojo.entity.MedicineDetail;
import com.itwh.pojo.entity.TempMedicine;
import com.itwh.serve.service.MedicineDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/medicine")
public class MedicineDetailController {

    @Autowired
    private MedicineDetailService medicineDetailService;

    /**
     * 新增用户常用药品
     * @param tempMedicine
     * @return
     */
    @PostMapping("/save")
    public Result saveMedicineDetail(@RequestBody TempMedicine tempMedicine){
        medicineDetailService.saveMedicineDetail(tempMedicine);
        return Result.success("常用药品新增成功");
    }

    /**
     *修改用户常用药品
     * @param updateMedicineDetailDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateMedicineDetail(@RequestBody UpdateMedicineDetailDTO updateMedicineDetailDTO){
        medicineDetailService.updateMedicineDetail(updateMedicineDetailDTO);
        return Result.success("用户常用药品信息修改成功");
    }

    /**
     * 根据用户id获取用户常用药品信息
     * @param userId
     * @return
     */
    @GetMapping("/list/userId/{userId}")
    public Result listMedicineDetailByUserId(@PathVariable Long userId){
        List<MedicineDetail> medicineDetails = medicineDetailService.listMedicineDetailByUserId(userId);
        return Result.success("根据用户id获取用户常用药品信息成功", medicineDetails);
    }

    /**
     * 根据常用药品id获取用户常用药品信息
     * @param id
     * @return
     */
    @GetMapping("/list/id/{id}")
    public Result listMedicineDetailById(@PathVariable Long id){
        MedicineDetail medicineDetail = medicineDetailService.listMedicineDetailById(id);
        return Result.success("根据常用药品id获取用户常用药品信息成功", medicineDetail);
    }

    /**
     * 删除用户常用药品信息
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteMedicineDetail(@PathVariable Long id){
        medicineDetailService.deleteMedicineDetail(id);
        return Result.success("用户常用药品信息删除成功");
    }

}
