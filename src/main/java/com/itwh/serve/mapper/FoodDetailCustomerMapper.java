package com.itwh.serve.mapper;

import com.itwh.pojo.entity.FoodDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodDetailCustomerMapper {

    /**
     * 新增食物表信息
     * @param foodDetail
     */
    void save(FoodDetail foodDetail);

    /**
     * 修改食物信息
     * @param foodDetail
     */
    void update(FoodDetail foodDetail);

    /**
     * 获取食物信息
     * @param foodDetail
     * @return
     */
    List<FoodDetail> list(FoodDetail foodDetail);

    /**
     * 删除食物信息
     * @param foodDetail
     */
    void delete(FoodDetail foodDetail);
}
