package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.dto.SaveFoodDetailDTO;
import com.itwh.pojo.dto.UpdateFoodDetailDTO;
import com.itwh.pojo.entity.FoodDetail;
import com.itwh.serve.mapper.FoodDetailCustomerMapper;
import com.itwh.serve.mapper.UserAndRoleMapper;
import com.itwh.serve.service.customer.FoodDetailCustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FoodDetailCustomerServiceImpl implements FoodDetailCustomerService {

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;

    @Autowired
    private FoodDetailCustomerMapper foodDetailCustomerMapper;


    /**
     * 添加食物表数据
     * @param saveFoodDetailDTO
     * @return
     */
    public void saveFoodDetail(SaveFoodDetailDTO saveFoodDetailDTO) {

        FoodDetail foodDetail = new FoodDetail();
        BeanUtils.copyProperties(saveFoodDetailDTO, foodDetail);

        //获取该用户的身份
        String roleName = userAndRoleMapper.listRoleByUserId(BaseContext.getCurrentId());
        if (roleName.equals("管理员")){
            foodDetail.setPass(1L);
        }else {
            foodDetail.setPass(2L);
        }
        foodDetailCustomerMapper.save(foodDetail);

    }

    /**
     * 修改食物信息
     * @param updateFoodDetailDTO
     * @return
     */
    public void updateFoodDetail(UpdateFoodDetailDTO updateFoodDetailDTO) {
        FoodDetail foodDetail = new FoodDetail();
        BeanUtils.copyProperties(updateFoodDetailDTO, foodDetail);
        //修改后提交审核
        //获取该用户的身份
        String roleName = userAndRoleMapper.listRoleByUserId(BaseContext.getCurrentId());
        if (roleName.equals("管理员")){
            foodDetail.setPass(1L);
        }else {
            foodDetail.setPass(2L);
        }
        foodDetailCustomerMapper.update(foodDetail);
    }

    /**
     * 根据食物id获取信息
     * @param id
     * @return
     */
    public FoodDetail listById(Long id) {
        FoodDetail foodDetail = FoodDetail.builder()
                .id(id)
                .build();
        return foodDetailCustomerMapper.list(foodDetail).get(0);
    }

    /**
     * 根据食物类别获取信息
     * @return
     */
    public List<FoodDetail> listByType(String type) {
        FoodDetail foodDetail = FoodDetail.builder()
                .type(type)
                .build();
        return foodDetailCustomerMapper.list(foodDetail);
    }

    /**
     * 根据食物名称模糊查询获取信息
     * @return
     */
    public List<FoodDetail> listByName(String name) {
        FoodDetail foodDetail = FoodDetail.builder()
                .name(name)
                .build();
        return foodDetailCustomerMapper.list(foodDetail);
    }

    /**
     * 根据id删除食物
     * @param id
     * @return
     */
    public void deleteById(Long id) {
        FoodDetail foodDetail = FoodDetail.builder()
                .id(id)
                .build();
        foodDetailCustomerMapper.delete(foodDetail);
    }


}
