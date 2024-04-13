package com.itwh.serve.mapper;

import com.itwh.pojo.entity.FeedBackPicture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedBackPictureMapper {
    /**
     * 批量插入反馈图片
     * @param feedBackPictures
     */
    void saveBatch(List<FeedBackPicture> feedBackPictures);

    /**
     * 查询反馈的图片
     * @param feedBackId
     * @return
     */
    @Select("select * from feed_back_picture where feed_back_id=#{feedBackId}")
    List<FeedBackPicture> list(Long feedBackId);

    /**
     * 删除反馈信息对应的图片
     * @param feedBackId
     */
    @Delete("delete from feed_back_picture where feed_back_id=#{feedBackId}")
    void delete(Long feedBackId);
}
