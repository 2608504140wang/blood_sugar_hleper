package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.dto.SaveSportRecordDTO;
import com.itwh.pojo.dto.UpdateSportRecordDTO;
import com.itwh.pojo.entity.SportDetailAndRecord;
import com.itwh.pojo.entity.SportRecord;
import com.itwh.pojo.vo.SportRecordVO;
import com.itwh.pojo.vo.ListSportRecordVO;
import com.itwh.pojo.vo.SportDetailVO;
import com.itwh.serve.mapper.SportDetailAndRecordMapper;
import com.itwh.serve.mapper.SportRecordMapper;
import com.itwh.serve.service.SportRecordService;
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
public class SportRecordServiceImpl implements SportRecordService {

    @Autowired
    private SportRecordMapper sportRecordMapper;

    @Autowired
    private SportDetailAndRecordMapper sportDetailAndRecordMapper;

    /**
     * 新增运动记录
     * @param saveSportRecordDTO
     * @return
     */
    public void saveSportRecord(SaveSportRecordDTO saveSportRecordDTO) {
        SportRecord sportRecord = new SportRecord();
        BeanUtils.copyProperties(saveSportRecordDTO, sportRecord);
        sportRecord.setUserId(BaseContext.getCurrentId());
        sportRecord.setCreateTime(LocalDateTime.now());
        //提交运动记录
        sportRecordMapper.save(sportRecord);

        List<SportDetailAndRecord> sportDetailAndRecords = saveSportRecordDTO.getSportDetailAndRecords();
        if (sportDetailAndRecords != null && sportDetailAndRecords.size() > 0){
            sportDetailAndRecords.forEach(sportDetailAndRecord -> sportDetailAndRecord.setRecordId(sportRecord.getId()));
            //提交运动记录连接信息
            sportDetailAndRecordMapper.saveBatch(sportDetailAndRecords);
        }
    }

    /**
     * 修改运动信息
     * @param updateSportRecordDTO
     * @return
     */
    public void updateSportRecord(UpdateSportRecordDTO updateSportRecordDTO) {
        //修改运动记录
        SportRecord sportRecord = new SportRecord();
        BeanUtils.copyProperties(updateSportRecordDTO, sportRecord);
        sportRecord.setUserId(BaseContext.getCurrentId());
        sportRecordMapper.update(sportRecord);

        //添加运动类别
        List<SportDetailAndRecord> addSportDetailAndRecords = updateSportRecordDTO.getAddSportDetailAndRecords();
        if (addSportDetailAndRecords != null && addSportDetailAndRecords.size() > 0){
            addSportDetailAndRecords.forEach(sportDetailAndRecord -> sportDetailAndRecord.setRecordId(updateSportRecordDTO.getId()));
            sportDetailAndRecordMapper.saveBatch(addSportDetailAndRecords);
        }

        //删除运动类别
        List<Long> removeSportDetailAndRecords = updateSportRecordDTO.getRemoveSportDetailAndRecords();
        if (removeSportDetailAndRecords != null && removeSportDetailAndRecords.size() > 0){
            sportDetailAndRecordMapper.delete(removeSportDetailAndRecords);
        }
    }

    /**
     * 查询指定时间范围内的运动记录
     * @param begin
     * @param end
     * @return
     */
    public ListSportRecordVO listSportRecord(LocalDate begin, LocalDate end) {
        //存放从begin到end的所有日期
        List<LocalDate> localDateList = new ArrayList<>();

        localDateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);
            localDateList.add(begin);
        }

        //存放每天的运动记录详细信息
        List<List<SportRecordVO>> sportRecordVOList = new ArrayList<>();
        //遍历每天，拿到每天的运动记录
        for (LocalDate date : localDateList){
            //存放每天的所有运动记录
            List<SportRecordVO> sportRecordVOS = new ArrayList<>();
            //每天的开始和结束时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            List<SportRecord> sportRecords = sportRecordMapper.list(beginTime, endTime);
            //复制运动记录信息
            for (SportRecord sportRecord : sportRecords){
                SportRecordVO sportRecordVO = new SportRecordVO();
                BeanUtils.copyProperties(sportRecord, sportRecordVO);
                sportRecordVOS.add(sportRecordVO);
            }

            //遍历某天的每条运动记录，拿到每条运动记录的每种运动的运动时间
            for (SportRecordVO sportRecordVO : sportRecordVOS){
                //根据运动记录id查询该条记录的每种运动的运动时间和信息
                List<SportDetailVO> sportDetailVOS = sportDetailAndRecordMapper.listBySportRecordId(sportRecordVO.getId());
                sportRecordVO.setSportDetailVOS(sportDetailVOS);
            }
            //加入某天的所有饮食记录
            sportRecordVOList.add(sportRecordVOS);
        }

        return ListSportRecordVO.builder()
                .localDateList(localDateList)
                .sportRecordVOList(sportRecordVOList)
                .build();
    }

    /**
     * 根据饮食记录id删除饮食记录
     * @param id
     * @return
     */
    public void deleteSportRecord(Long id) {
        //删除运动记录
        sportRecordMapper.delete(id);

        //删除运动记录对应的所有连接信息
        sportDetailAndRecordMapper.deleteByRecordId(id);
    }


}
