package com.itwh.serve.mapper;

import com.itwh.pojo.entity.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    /**
     * 发布公告
     * @param notice
     * @return
     */
    void save(Notice notice);

    /**
     * 查询公告内容
     * @param id
     * @return
     */
    List<Notice> list(Long id);

    /**
     * 删除公告
     * @param id
     * @return
     */
    @Delete("delete from notice where id=#{id}")
    void delete(Long id);
}
