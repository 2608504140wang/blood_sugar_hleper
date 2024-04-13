package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.entity.Notice;
import com.itwh.serve.mapper.NoticeMapper;
import com.itwh.serve.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 发布公告
     * @param notice
     * @return
     */
    public void saveNotice(Notice notice) {
        notice.setAdminId(BaseContext.getCurrentId());
        notice.setCreateTime(LocalDateTime.now());
        noticeMapper.save(notice);
    }

    /**
     * 查询公告内容
     * @param id
     * @return
     */
    public List<Notice> listNotice(Long id) {
        return noticeMapper.list(id);
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    public void deleteNotice(Long id) {
        noticeMapper.delete(id);
    }


}
