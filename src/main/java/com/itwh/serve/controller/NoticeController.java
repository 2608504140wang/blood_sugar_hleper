package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.entity.Notice;
import com.itwh.serve.service.NoticeService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 发布公告
     * @param notice
     * @return
     */
    @PostMapping("/admin/notice/save")
    public Result saveNotice(@RequestBody Notice notice){
        noticeService.saveNotice(notice);
        return Result.success("公告发布成功");
    }

    /**
     * 查询公告内容
     * @param id
     * @return
     */
    @GetMapping("/notice/list")
    public Result listNotice(Long id){
        List<Notice> notices = noticeService.listNotice(id);
        return Result.success("公告内容查询成功", notices);
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    @DeleteMapping("/admin/notice/delete/{id}")
    public Result deleteNotice(@PathVariable Long id){
        noticeService.deleteNotice(id);
        return Result.success("公告删除成功");
    }

}
