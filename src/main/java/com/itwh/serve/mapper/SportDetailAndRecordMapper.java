package com.itwh.serve.mapper;

import com.itwh.pojo.entity.SportDetailAndRecord;
import com.itwh.pojo.entity.SportRecord;
import com.itwh.pojo.vo.SportDetailVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SportDetailAndRecordMapper {

    /**
     *批量插入运动记录连接信息
     * @param sportDetailAndRecords
     */
    void saveBatch(List<SportDetailAndRecord> sportDetailAndRecords);

    /**
     * 删除运动类别
     * @param removeSportDetailAndRecords
     */
    void delete(List<Long> removeSportDetailAndRecords);

    /**
     * 根据运动记录id查询该条记录的每种运动的运动时间和信息
     * @param sportRecordId
     * @return
     */
    List<SportDetailVO> listBySportRecordId(Long sportRecordId);

    /**
     * 删除运动记录id对应的所有运动记录
     * @param recordId
     */
    @Delete("delete from sport_detail_and_record where record_id = #{recordId}")
    void deleteByRecordId(Long recordId);
}
