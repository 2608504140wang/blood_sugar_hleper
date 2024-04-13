package com.itwh.serve.controller;

import com.itwh.common.exception.BaseException;
import com.itwh.common.result.Result;
import com.itwh.pojo.dto.SaveDietRecordDTO;
import com.itwh.pojo.dto.UpdateDietRecordDTO;
import com.itwh.pojo.vo.ListDietRecordVO;
import com.itwh.serve.service.DietRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/customer/diet")
public class DietRecordController {

    @Autowired
    private DietRecordService dietRecordService;

    /**
     * 提交饮食记录
     * @return
     */
    @PostMapping("/save")
    public Result saveDietRecord(@RequestBody SaveDietRecordDTO saveDietRecordDTO){
        if (saveDietRecordDTO.getFoodAndDiets() == null || saveDietRecordDTO.getFoodAndDiets().size() == 0){
            throw new BaseException("添加食物信息不能为空");
        }
        dietRecordService.saveDietRecord(saveDietRecordDTO);
        return Result.success("饮食记录提交成功！");
    }

    /**
     * 修改饮食记录
     * @param updateDietRecordDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateDietRecord(@RequestBody UpdateDietRecordDTO updateDietRecordDTO){
        dietRecordService.updateDietRecord(updateDietRecordDTO);
        return Result.success("饮食记录修改成功！");
    }

    /**
     * 查询指定时间范围内的饮食记录
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/list")
    public Result listDietRecordByDate(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        ListDietRecordVO listDietRecordVOS = dietRecordService.listDietRecordByDate(begin, end);
        return Result.success("指定时间范围内的饮食记录获取成功！", listDietRecordVOS);
    }

    /**
     * 根据饮食记录id删除饮食记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public Result deleteDietRecord(Long id){
        dietRecordService.deleteDietRecord(id);
        return Result.success("该条饮食记录删除成功！");
    }

}
