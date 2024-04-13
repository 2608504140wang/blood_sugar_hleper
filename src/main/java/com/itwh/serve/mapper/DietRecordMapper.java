package com.itwh.serve.mapper;

import com.itwh.pojo.entity.DietRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DietRecordMapper {

    /**
     * 新建饮食记录
     * @param dietRecord
     */
    void save(DietRecord dietRecord);

    /**
     * 修改饮食记录
     * @param dietRecord
     */
    void update(DietRecord dietRecord);

    /**
     * 查询当天的饮食记录
     * @param beginTime
     * @param endTime
     * @return
     */
    List<DietRecord> list(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 删除饮食记录
     * @param id
     */
    @Delete("delete from diet_record where id = #{id}")
    void delete(Long id);

    /**
     * 根据id查询饮食记录信息
     * @param id
     * @return
     */
    @Select("select * from diet_record where id=#{id}")
    DietRecord listByDietId(Long id);
}
