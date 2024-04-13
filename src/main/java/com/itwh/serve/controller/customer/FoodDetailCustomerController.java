package com.itwh.serve.controller.customer;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.SaveFoodDetailDTO;
import com.itwh.pojo.dto.UpdateFoodDetailDTO;
import com.itwh.pojo.entity.FoodDetail;
import com.itwh.serve.service.customer.FoodDetailCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/food")
public class FoodDetailCustomerController {

    @Autowired
    private FoodDetailCustomerService foodDetailService;

    /**
     * 添加食物表数据
     * @param saveFoodDetailDTO
     * @return
     */
    @PostMapping("/save")
    public Result saveFoodDetail(@RequestBody SaveFoodDetailDTO saveFoodDetailDTO){
        foodDetailService.saveFoodDetail(saveFoodDetailDTO);
        return Result.success("食物信息提交成功");

    }

    /**
     * 修改食物信息
     * @param updateFoodDetailDTO
     * @return
     */
    @PutMapping("/update")
    public Result updateFoodDetail(@RequestBody UpdateFoodDetailDTO updateFoodDetailDTO){
        foodDetailService.updateFoodDetail(updateFoodDetailDTO);
        return Result.success("食物信息修改成功");
    }

    /**
     * 根据食物id获取信息
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    public Result listById(@PathVariable Long id){
        FoodDetail foodDetail = foodDetailService.listById(id);
        return Result.success("id为"+id+"的食物的信息获取成功", foodDetail);
    }

    /**
     * 根据食物类别获取信息
     * @return
     */
    @GetMapping("/list/type")
    public Result listByType(String type){
        List<FoodDetail> foodDetails = foodDetailService.listByType(type);
        return Result.success("所有食物信息获取成功", foodDetails);
    }

    /**
     * 根据食物名称模糊查询获取信息
     * @return
     */
    @GetMapping("/list/name")
    public Result listByName(String name){
        List<FoodDetail> foodDetails = foodDetailService.listByName(name);
        return Result.success("根据食物名称模糊查询获取信息获取成功", foodDetails);
    }

    /**
     * 根据id删除食物
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Long id){
        foodDetailService.deleteById(id);
        return Result.success("id为"+id+"的食物的信息删除成功");
    }

}
