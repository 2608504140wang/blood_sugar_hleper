package com.itwh.serve.service;

import com.itwh.pojo.dto.SaveSportDetailDTO;
import com.itwh.pojo.dto.UpdateSportDetailDTO;
import com.itwh.pojo.entity.SportDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SportDetailService {

    /**
     * 新增运动信息
     * @param saveSportDetailDTO
     * @return
     */
    void saveSportDetail(SaveSportDetailDTO saveSportDetailDTO);

    /**
     * 修改运动信息
     * @param updateSportDetailDTO
     * @return
     */
    void updateSportDetail(UpdateSportDetailDTO updateSportDetailDTO);

    /**
     * 根据运动信息id获取运动信息
     *
     * @param id
     * @return
     */
    List<SportDetail> listSportDetailById(Long id);

    /**
     * 根据运动类别获取运动信息
     * @param type
     * @return
     */
    List<SportDetail> listSportDetailByType(String type);

    /**
     * 根据运动名称获取运动信息
     * @param name
     * @return
     */
    List<SportDetail> listSportDetailByName(String name);

    /**
     * 根据运动信息id删除运动信息
     * @param id
     * @return
     */
    void deleteportDetailById(Long id);
}
