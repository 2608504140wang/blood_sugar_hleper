package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.SaveSportDetailDTO;
import com.itwh.pojo.dto.UpdateSportDetailDTO;
import com.itwh.pojo.entity.SportDetail;
import com.itwh.serve.service.SportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/sport/detail")
public class SportDetailController {

    @Autowired
    private SportDetailService sportDetailService;


    /**
     * 新增运动信息
     * @param saveSportDetailDTO
     * @return
     */
    @PostMapping("/save")
    public Result saveSportDetail(@RequestBody SaveSportDetailDTO saveSportDetailDTO){
        sportDetailService.saveSportDetail(saveSportDetailDTO);
        return Result.success("运动信息新增申请提交成功！");
    }

    /**
     * 修改运动信息
     * @param updateSportDetailDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateSportDetail(@RequestBody UpdateSportDetailDTO updateSportDetailDTO){
        sportDetailService.updateSportDetail(updateSportDetailDTO);
        return Result.success("运动信息修改申请提交成功！");
    }

    /**
     * 根据运动信息id获取运动信息
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    public Result listSportDetailById(@PathVariable Long id){
        List<SportDetail> sportDetails = sportDetailService.listSportDetailById(id);
        if (sportDetails != null && sportDetails.size() > 0){
            return Result.success("信息获取成功！", sportDetails.get(0));
        }else {
            return Result.success("没有找到相关运动信息");
        }
    }

    /**
     * 根据运动类别获取运动信息
     * @param type
     * @return
     */
    @GetMapping("/list/type")
    public Result listSportDetailByType(String type){
        List<SportDetail> sportDetails = sportDetailService.listSportDetailByType(type);

        if (sportDetails != null && sportDetails.size() > 0){
            return Result.success("信息获取成功！", sportDetails);
        }else {
            return Result.success("没有找到相关运动信息");
        }
    }

    /**
     * 根据运动名称获取运动信息
     * @param name
     * @return
     */
    @GetMapping("/list/name")
    public Result listSportDetailByName(String name){
        List<SportDetail> sportDetails = sportDetailService.listSportDetailByName(name);

        if (sportDetails != null && sportDetails.size() > 0){
            return Result.success("信息获取成功！", sportDetails);
        }else {
            return Result.success("没有找到相关运动信息");
        }
    }

    /**
     * 根据运动信息id删除运动信息
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteportDetailById(@PathVariable Long id){
        sportDetailService.deleteportDetailById(id);
        return Result.success("运动信息删除成功！");
    }

}
