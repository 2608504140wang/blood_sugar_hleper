package com.itwh.serve.mapper;

import com.itwh.pojo.entity.MedicineDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MedicineDetailMapper {

    /**
     * 用户新增常用药品
     * @param medicineDetail
     * @return
     */
    void save(MedicineDetail medicineDetail);

    /**
     *修改用户常用药品
     * @param medicineDetail
     * @return
     */
    void update(MedicineDetail medicineDetail);

    /**
     * 根据用户id获取用户常用药品信息
     *
     * @param userId
     * @return
     */
    @Select("select * from medicine_detail where user_id=#{userId}")
    List<MedicineDetail> listByUserId(Long userId);

    /**
     * 根据常用药品id获取用户常用药品信息
     * @param id
     * @return
     */
    @Select("select * from medicine_detail where id=#{id}")
    MedicineDetail listById(Long id);

    /**
     * 删除用户常用药品信息
     * @param medicineDetail
     * @return
     */
    void delete(MedicineDetail medicineDetail);
}
