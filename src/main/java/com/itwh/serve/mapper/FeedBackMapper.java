package com.itwh.serve.mapper;

import com.itwh.pojo.entity.FeedBack;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedBackMapper {

    /**
     * 提交反馈
     * @param feedBack
     * @return
     */
    void save(FeedBack feedBack);

    /**
     * 获取反馈信息
     * @param feedBack
     * @return
     */
    List<FeedBack> list(FeedBack feedBack);

    /**
     * 删除反馈记录
     * @return
     */
    @Delete("delete from feed_back where id=#{id}")
    void delete(Long id);
}
