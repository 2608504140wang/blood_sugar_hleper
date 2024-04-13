package com.itwh.serve.service.customer;

import com.itwh.pojo.dto.SaveFoodDetailDTO;
import com.itwh.pojo.dto.UpdateFoodDetailDTO;
import com.itwh.pojo.entity.FoodDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodDetailCustomerService {

    /**
     * 添加食物表数据
     * @param saveFoodDetailDTO
     * @return
     */
    void saveFoodDetail(SaveFoodDetailDTO saveFoodDetailDTO);

    /**
     * 修改食物信息
     * @param updateFoodDetailDTO
     * @return
     */
    void updateFoodDetail(UpdateFoodDetailDTO updateFoodDetailDTO);

    /**
     * 根据食物id获取信息
     * @param id
     * @return
     */
    FoodDetail listById(Long id);

    /**
     * 根据食物类别获取信息
     * @return
     */
    List<FoodDetail> listByType(String type);

    /**
     * 根据食物名称模糊查询获取信息
     * @return
     */
    List<FoodDetail> listByName(String name);

    /**
     * 根据id删除食物
     * @param id
     * @return
     */
    void deleteById(Long id);
}
