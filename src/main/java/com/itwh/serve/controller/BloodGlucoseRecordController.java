package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.SaveBloodGlucoseRecordDTO;
import com.itwh.pojo.dto.UpdateBloodGlucoseRecordDTO;
import com.itwh.pojo.vo.BloodGlucoseRecordVO;
import com.itwh.serve.service.BloodGlucoseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer/glucose")
public class BloodGlucoseRecordController {

    @Autowired
    private BloodGlucoseRecordService bloodGlucoseRecordService;

    /**
     * 提交血糖记录
     * @param saveBloodGlucoseRecordDTO
     * @return
     */
    @PostMapping("/save")
    public Result saveBloodGlucoseRecord(@RequestBody SaveBloodGlucoseRecordDTO saveBloodGlucoseRecordDTO){
        bloodGlucoseRecordService.saveBloodGlucoseRecord(saveBloodGlucoseRecordDTO);
        return Result.success("血糖记录提交成功！");
    }


    /**
     * 修改血糖记录
     * @param updateBloodGlucoseRecordDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateBloodGlucoseRecord(@RequestBody UpdateBloodGlucoseRecordDTO updateBloodGlucoseRecordDTO){
        if (updateBloodGlucoseRecordDTO.getGlucoseLevel()==null && updateBloodGlucoseRecordDTO.getPeriodLabel()==null
            && updateBloodGlucoseRecordDTO.getRemark()==null){
            return Result.error(500, "修改信息不能空");
        }
        bloodGlucoseRecordService.updateBloodGlucoseRecord(updateBloodGlucoseRecordDTO);
        return Result.success("血糖记录修改成功！");
    }

    /**
     * 删除血糖记录
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public Result deleteBloodGlucoseRecord(@RequestBody List<Long> ids){
        if (ids == null || ids.size() == 0){
            return Result.error(500, "删除记录数不能为0");
        }
        bloodGlucoseRecordService.deleteBloodGlucoseRecord(ids);
        return Result.success("血糖记录删除成功");
    }

    /**
     * 查看血糖记录
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/list")
    public Result listBloodGlucoseRecordByDate(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        BloodGlucoseRecordVO bloodGlucoseRecordVO = bloodGlucoseRecordService.listBloodGlucoseRecordByDate(begin, end);
        return Result.success("血糖记录查询成功",bloodGlucoseRecordVO);
    }

}
