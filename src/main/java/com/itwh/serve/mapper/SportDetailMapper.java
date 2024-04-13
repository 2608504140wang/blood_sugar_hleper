package com.itwh.serve.mapper;

import com.itwh.pojo.entity.SportDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SportDetailMapper {

    /**
     * 新增运动信息
     * @param sportDetail
     */
    void save(SportDetail sportDetail);

    /**
     * 修改运动信息
     * @param sportDetail
     * @return
     */
    void update(SportDetail sportDetail);

    /**
     * 获取运动信息
     * @param sportDetail
     * @return
     */
    List<SportDetail> list(SportDetail sportDetail);

    /**
     * 根据运动信息id删除运动信息
     * @param sportDetail
     * @return
     */
    void delete(SportDetail sportDetail);
}
