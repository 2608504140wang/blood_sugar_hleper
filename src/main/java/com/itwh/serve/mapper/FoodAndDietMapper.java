package com.itwh.serve.mapper;

import com.itwh.pojo.entity.FoodAndDiet;
import com.itwh.pojo.vo.FoodDetailVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodAndDietMapper {

    /**
     * 批量插入食物饮食记录连接信息
     * @param foodAndDiets
     */
    void saveBatch(List<FoodAndDiet> foodAndDiets);

    /**
     * 删除食物饮食记录连接信息
     *
     */
    void delete(Long foodId, Long dietId);

    /**
     * 根据饮食记录id查询该条记录的所有食物及其克数
     *
     * @param dietId
     * @return
     */
    List<FoodDetailVO> listBydietId(Long dietId);

    /**
     * 删除饮食记录id对应的所有食物记录
     * @param dietId
     */
    @Delete("delete from food_and_diet where diet_id = #{dietId}")
    void deleteByDietId(Long dietId);

}
