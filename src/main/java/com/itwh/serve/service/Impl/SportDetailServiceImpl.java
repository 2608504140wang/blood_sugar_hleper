package com.itwh.serve.service.Impl;

import com.itwh.pojo.dto.SaveSportDetailDTO;
import com.itwh.pojo.dto.UpdateSportDetailDTO;
import com.itwh.pojo.entity.SportDetail;
import com.itwh.serve.mapper.SportDetailMapper;
import com.itwh.serve.service.SportDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportDetailServiceImpl implements SportDetailService {

    @Autowired
    private SportDetailMapper sportDetailMapper;

    /**
     * 新增运动信息
     * @param saveSportDetailDTO
     * @return
     */
    public void saveSportDetail(SaveSportDetailDTO saveSportDetailDTO) {

        SportDetail sportDetail = new SportDetail();
        BeanUtils.copyProperties(saveSportDetailDTO, sportDetail);
        sportDetail.setPass(2L);
        sportDetailMapper.save(sportDetail);
    }

    /**
     * 修改运动信息
     * @param updateSportDetailDTO
     * @return
     */
    public void updateSportDetail(UpdateSportDetailDTO updateSportDetailDTO) {
        SportDetail sportDetail = new SportDetail();
        BeanUtils.copyProperties(updateSportDetailDTO, sportDetail);
        sportDetail.setPass(2L);
        sportDetailMapper.update(sportDetail);
    }

    /**
     * 根据运动信息id获取运动信息
     *
     * @param id
     * @return
     */
    public List<SportDetail> listSportDetailById(Long id) {
        SportDetail sportDetail = new SportDetail();
        sportDetail.setId(id);

        return sportDetailMapper.list(sportDetail);
    }

    /**
     * 根据运动类别获取运动信息
     * @param type
     * @return
     */
    public List<SportDetail> listSportDetailByType(String type) {
        SportDetail sportDetail = new SportDetail();
        sportDetail.setType(type);

        return sportDetailMapper.list(sportDetail);
    }

    /**
     * 根据运动名称获取运动信息
     * @param name
     * @return
     */
    public List<SportDetail> listSportDetailByName(String name) {
        SportDetail sportDetail = new SportDetail();
        sportDetail.setName(name);

        return sportDetailMapper.list(sportDetail);
    }

    /**
     * 根据运动信息id删除运动信息
     * @param id
     * @return
     */
    public void deleteportDetailById(Long id) {
        SportDetail sportDetail = new SportDetail();
        sportDetail.setId(id);

        sportDetailMapper.delete(sportDetail);
    }

}
