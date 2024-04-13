package com.itwh.serve.mapper;

import com.itwh.pojo.entity.SportRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SportRecordMapper {

    /**
     * 新增运动记录
     * @param sportRecord
     * @return
     */
    void save(SportRecord sportRecord);

    /**
     * 修改运动信息
     * @param sportRecord
     * @return
     */
    void update(SportRecord sportRecord);

    /**
     * 查询指定时间内所有运动记录
     * @param beginTime
     * @param endTime
     * @return
     */
    List<SportRecord> list(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 删除运动记录
     * @param id
     */
    @Delete("delete from sport_record where id = #{id}")
    void delete(Long id);
}
