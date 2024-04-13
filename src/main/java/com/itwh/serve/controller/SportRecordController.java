package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.SaveSportRecordDTO;
import com.itwh.pojo.dto.UpdateSportRecordDTO;
import com.itwh.pojo.vo.ListSportRecordVO;
import com.itwh.serve.service.SportRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/customer/sport/record")
public class SportRecordController {

    @Autowired
    private SportRecordService sportRecordService;

    /**
     * 新增运动记录
     * @param saveSportRecordDTO
     * @return
     */
    @PostMapping("/save")
    public Result saveSportRecord(@RequestBody SaveSportRecordDTO saveSportRecordDTO){
        if (saveSportRecordDTO.getSportDetailAndRecords() == null || saveSportRecordDTO.getSportDetailAndRecords().size() == 0){
            return Result.error(500, "运动添加不能为空");
        }
        sportRecordService.saveSportRecord(saveSportRecordDTO);

        return Result.success("运动信息添加成功！");
    }

    /**
     * 修改运动信息
     * @param updateSportRecordDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateSportRecord(@RequestBody UpdateSportRecordDTO updateSportRecordDTO){
        sportRecordService.updateSportRecord(updateSportRecordDTO);
        return Result.success("运动信息修改成功！");
    }

    /**
     * 查询指定时间范围内的运动记录
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/list")
    public Result listSportRecord(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        ListSportRecordVO listSportRecordVO = sportRecordService.listSportRecord(begin, end);
        return Result.success("指定时间范围内的运动记录获取成功！", listSportRecordVO);
    }

    /**
     * 根据饮食记录id删除饮食记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteSportRecord(@PathVariable Long id){
        sportRecordService.deleteSportRecord(id);

        return Result.success("该条运动记录删除成功！");
    }
}
