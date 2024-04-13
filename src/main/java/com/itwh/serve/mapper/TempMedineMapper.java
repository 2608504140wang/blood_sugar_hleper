package com.itwh.serve.mapper;

import com.itwh.pojo.entity.TempMedicine;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TempMedineMapper {

    /**
     * 新增记录对应的多个药品
     * @param tempMedicines
     */
    void saveBatch(List<TempMedicine> tempMedicines);

    /**
     * 删除记录对应的药品集
     * @param removeIds
     */
    void delete(List<Long> removeIds);

    /**
     * 获取一个用药记录的所有药品
     * @param tempMedicine
     * @return
     */
    List<TempMedicine> listByRecordId(TempMedicine tempMedicine);

    /**
     * 删除用药记录id对应的药品记录
     * @param recordId
     */
    @Delete("delete from temp_medicine where record_id=#{recordId}")
    void deleteByRecordId(Long recordId);
}
