package com.itwh.serve.service;

import com.itwh.pojo.entity.Notice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {

    /**
     * 发布公告
     * @param notice
     * @return
     */
    void saveNotice(Notice notice);

    /**
     * 查询公告内容
     * @param id
     * @return
     */
    List<Notice> listNotice(Long id);

    /**
     * 删除公告
     * @param id
     * @return
     */
    void deleteNotice(Long id);
}
