package com.itwh.serve.service;

import com.itwh.pojo.dto.SaveSportRecordDTO;
import com.itwh.pojo.dto.UpdateSportRecordDTO;
import com.itwh.pojo.vo.ListSportRecordVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface SportRecordService {

    /**
     * 新增运动记录
     * @param saveSportRecordDTO
     * @return
     */
    void saveSportRecord(SaveSportRecordDTO saveSportRecordDTO);

    /**
     * 修改运动信息
     * @param updateSportRecordDTO
     * @return
     */
    void updateSportRecord(UpdateSportRecordDTO updateSportRecordDTO);

    /**
     * 查询指定时间范围内的运动记录
     * @param begin
     * @param end
     * @return
     */
    ListSportRecordVO listSportRecord(LocalDate begin, LocalDate end);

    /**
     * 根据饮食记录id删除饮食记录
     * @param id
     * @return
     */
    void deleteSportRecord(Long id);
}
