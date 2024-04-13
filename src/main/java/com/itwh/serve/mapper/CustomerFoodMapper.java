package com.itwh.serve.mapper;

import com.itwh.pojo.entity.CustomerFood;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerFoodMapper {

    /**
     * 获取用户食物信息
     * @param customerFood
     * @return
     */
    CustomerFood list(CustomerFood customerFood);

    /**
     * 修改用户食物表数据
     * @param customerFood1
     */
    void update(CustomerFood customerFood1);

    /**
     * 新增用户食物表数据
     * @param customerFood2
     */
    void save(CustomerFood customerFood2);

}
