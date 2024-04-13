package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.dto.SaveDietRecordDTO;
import com.itwh.pojo.dto.UpdateDietRecordDTO;
import com.itwh.pojo.entity.CustomerFood;
import com.itwh.pojo.entity.DietRecord;
import com.itwh.pojo.entity.FoodAndDiet;
import com.itwh.pojo.vo.DietRecordVO;
import com.itwh.pojo.vo.FoodDetailVO;
import com.itwh.pojo.vo.ListDietRecordVO;
import com.itwh.serve.mapper.CustomerFoodMapper;
import com.itwh.serve.mapper.DietRecordMapper;
import com.itwh.serve.mapper.FoodAndDietMapper;
import com.itwh.serve.service.DietRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DietRecordServiceImpl implements DietRecordService {

    @Autowired
    private DietRecordMapper dietRecordMapper;

    @Autowired
    private FoodAndDietMapper foodAndDietMapper;

    @Autowired
    private CustomerFoodMapper customerFoodMapper;

    /**
     * 提交饮食记录
     * @return
     */
    public void saveDietRecord(SaveDietRecordDTO saveDietRecordDTO) {
        //插入饮食记录
        DietRecord dietRecord = new DietRecord();
        BeanUtils.copyProperties(saveDietRecordDTO, dietRecord);
        dietRecord.setCreateTime(LocalDateTime.now());
        dietRecord.setUserId(BaseContext.getCurrentId());
        dietRecordMapper.save(dietRecord);

        //插入饮食记录和食物连接信息
        List<FoodAndDiet> foodAndDiets = saveDietRecordDTO.getFoodAndDiets();
        foodAndDiets.forEach(foodAndDiet -> foodAndDiet.setDietId(dietRecord.getId()));
        foodAndDietMapper.saveBatch(foodAndDiets);

        Long userId = BaseContext.getCurrentId();
        LocalDateTime recordTime = saveDietRecordDTO.getRecordTime();
        //更新用户食物表
        for (FoodAndDiet foodAndDiet : foodAndDiets){
            //检查用户是否选择过该食物
            CustomerFood customerFood = new CustomerFood();
            customerFood.setFoodId(foodAndDiet.getFoodId());
            customerFood.setUserId(userId);
            CustomerFood customerFood1 = customerFoodMapper.list(customerFood);
            if (customerFood1 != null){
                //用户之前选择过该食物
                customerFood1.setSum(customerFood1.getSum()+1);
                //该记录时间晚于该食物上一次时间才需要修改时间
                if (customerFood1.getLast().isBefore(recordTime)){
                    customerFood1.setLast(recordTime);
                }
                //修改用户食物表数据
                customerFoodMapper.update(customerFood1);
            }else {
                //用户之前没有选择过该食物
                CustomerFood customerFood2 = CustomerFood.builder()
                        .userId(userId)
                        .foodId(foodAndDiet.getFoodId())
                        .sum(1L)
                        .first(recordTime)
                        .last(recordTime)
                        .build();
                //新增用户食物表数据
                customerFoodMapper.save(customerFood2);
            }
        }
    }

    /**
     * 修改饮食记录
     * @param updateDietRecordDTO
     * @return
     */
    public void updateDietRecord(UpdateDietRecordDTO updateDietRecordDTO) {

        //添加食物
        List<FoodAndDiet> addFoodAndDiets = updateDietRecordDTO.getAddFoodAndDiets();
        if (addFoodAndDiets != null && addFoodAndDiets.size() > 0){
            addFoodAndDiets.forEach(foodAndDiet -> foodAndDiet.setDietId(updateDietRecordDTO.getId()));
            foodAndDietMapper.saveBatch(addFoodAndDiets);
        }

        Long userId = BaseContext.getCurrentId();
        LocalDateTime recordTime = updateDietRecordDTO.getRecordTime();
        //更新用户食物表
        for (FoodAndDiet foodAndDiet : addFoodAndDiets) {
            //检查用户是否选择过该食物
            CustomerFood customerFood = new CustomerFood();
            customerFood.setFoodId(foodAndDiet.getFoodId());
            customerFood.setUserId(userId);
            CustomerFood customerFood1 = customerFoodMapper.list(customerFood);
            if (customerFood1 != null) {
                //用户之前选择过该食物
                customerFood1.setSum(customerFood1.getSum() + 1);
                //该记录时间晚于该食物上一次时间才需要修改时间
                if (customerFood1.getLast().isBefore(recordTime)) {
                    customerFood1.setLast(recordTime);
                }
                //修改用户食物表数据
                customerFoodMapper.update(customerFood1);
            } else {
                //用户之前没有选择过该食物
                CustomerFood customerFood2 = CustomerFood.builder()
                        .userId(userId)
                        .foodId(foodAndDiet.getFoodId())
                        .sum(1L)
                        .first(recordTime)
                        .last(recordTime)
                        .build();
                //新增用户食物表数据
                customerFoodMapper.save(customerFood2);
            }
        }

        //删除食物
        List<Long> removeFoodAndDietIds = updateDietRecordDTO.getRemoveFoodAndDietIds();
        if (removeFoodAndDietIds != null && removeFoodAndDietIds.size() > 0){
            Long dietId = updateDietRecordDTO.getId();
            //删除该条记录中这些食物,并减去用户对该食物的选择次数
            for (Long foodId : removeFoodAndDietIds){
                foodAndDietMapper.delete(foodId, dietId);
                //查找该食物的记录信息
                CustomerFood customerFood = new CustomerFood();
                customerFood.setFoodId(foodId);
                customerFood.setUserId(userId);
                customerFood = customerFoodMapper.list(customerFood);
                //由于之前的时间被覆盖，所以还是记录该食物被选择的最后一次时间
                //减少一次食物被选择的次数
                customerFood.setSum(customerFood.getSum()-1);
                customerFoodMapper.update(customerFood);
            }
        }

        //更新用户食物表
        //查出该条记录之前的记录时间，对比记录时间是否要修改
        DietRecord dietRecord1 = dietRecordMapper.listByDietId(updateDietRecordDTO.getId());
        LocalDateTime recordTime1 = dietRecord1.getRecordTime();
        //判断是否要修改该条记录的时间
        if (recordTime != null){
            //需要修改记录时间
            //查出该条记录之前所有的食物，修改记录时间
            List<FoodDetailVO> foodDetailVOS = foodAndDietMapper.listBydietId(updateDietRecordDTO.getId());
            for (FoodDetailVO foodDetailVO : foodDetailVOS){
                //先判断该食物的时间需不需要修改
                CustomerFood customerFood = new CustomerFood();
                customerFood.setUserId(userId);
                customerFood.setFoodId(foodDetailVO.getId());
                CustomerFood customerFood1 = customerFoodMapper.list(customerFood);
                //如果该食物的最后一次选择时间等于之前那个记录时间并且不准备删除该食物,就要修改为新的记录时间
                if (customerFood1.getLast().isEqual(recordTime1) && !removeFoodAndDietIds.contains(foodDetailVO.getId())){
                    customerFood1.setLast(recordTime1);
                    customerFoodMapper.update(customerFood1);
                }
            }
        }

        //修改饮食记录
        DietRecord dietRecord = new DietRecord();
        BeanUtils.copyProperties(updateDietRecordDTO, dietRecord);
        dietRecord.setUserId(BaseContext.getCurrentId());
        dietRecordMapper.update(dietRecord);
    }

    /**
     * 查询指定时间范围内的饮食记录
     * @param begin
     * @param end
     * @return
     */
    public ListDietRecordVO listDietRecordByDate(LocalDate begin, LocalDate end) {
        //存放从begin到end的所有日期
        List<LocalDate> localDateList = new ArrayList<>();

        localDateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }

        //存放每天的饮食记录详细信息
        List<List<DietRecordVO>> dietRecordVOList = new ArrayList<>();
        //遍历每天，拿到每天的饮食记录
        for (LocalDate date : localDateList){
            List<DietRecordVO> dietRecordVOS = new ArrayList<>();
            //每天的开始和结束时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            List<DietRecord> dietRecords = dietRecordMapper.list(beginTime, endTime);
            //复制记录
            for (DietRecord dietRecord : dietRecords){
                DietRecordVO dietRecordVO = new DietRecordVO();
                BeanUtils.copyProperties(dietRecord, dietRecordVO);
                dietRecordVOS.add(dietRecordVO);
            }

            //遍历某天的每条饮食记录，拿到每条饮食记录的所有食物及其克数
            for (DietRecordVO dietRecordVO1 : dietRecordVOS){
                //根据饮食记录id查询该条记录的所有食物及其克数
                List<FoodDetailVO> foodDetailVOS = foodAndDietMapper.listBydietId(dietRecordVO1.getId());
                dietRecordVO1.setFoodDetailVOS(foodDetailVOS);
            }
            //加入某天的所有饮食记录
            dietRecordVOList.add(dietRecordVOS);
        }
        return ListDietRecordVO.builder()
                .localDateList(localDateList)
                .dietRecordVOList(dietRecordVOList)
                .build();
    }

    /**
     * 根据饮食记录id删除饮食记录
     * @param id
     * @return
     */
    public void deleteDietRecord(Long id) {
        Long userId = BaseContext.getCurrentId();

        //查出该条记录之前所有的食物，修改其选择次数
        List<FoodDetailVO> foodDetailVOS = foodAndDietMapper.listBydietId(id);
        for (FoodDetailVO foodDetailVO : foodDetailVOS){
            //查找该食物的记录信息
            CustomerFood customerFood = new CustomerFood();
            customerFood.setFoodId(foodDetailVO.getId());
            customerFood.setUserId(userId);
            customerFood = customerFoodMapper.list(customerFood);
            //由于之前的时间被覆盖，所以还是记录该食物被选择的最后一次时间
            //减少一次食物被选择的次数
            customerFood.setSum(customerFood.getSum()-1);
            customerFoodMapper.update(customerFood);
        }

        //删除饮食记录
        dietRecordMapper.delete(id);

        //删除该饮食记录对应的食物记录
        foodAndDietMapper.deleteByDietId(id);

    }


}
