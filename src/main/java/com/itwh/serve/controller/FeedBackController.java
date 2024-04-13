package com.itwh.serve.controller;

import com.itwh.common.result.Result;
import com.itwh.pojo.dto.FeedBackDTO;
import com.itwh.pojo.vo.FeedBackVO;
import com.itwh.pojo.vo.ListFeedBackVO;
import com.itwh.serve.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    /**
     * 提交反馈
     * @param feedBackDTO
     * @return
     */
    @PostMapping("/customer/feed_back/save")
    public Result saveFeedBack(@RequestBody FeedBackDTO feedBackDTO){
        feedBackService.saveFeedBack(feedBackDTO);
        return Result.success("反馈提交成功！");
    }

    /**
     * 获取所有用户的反馈信息
     * @param
     * @return
     */
    @GetMapping("/admin/feed_back/list/all")
    public Result listAllFeedBack(){
        ListFeedBackVO listFeedBackVO = feedBackService.listAllFeedBack();
        return Result.success("反馈信息获取成功",listFeedBackVO);
    }

    /**
     * 获取单个用户的反馈信息
     * @param userId
     * @return
     */
    @GetMapping("/feed_back/list/{userId}")
    public Result listFeedBack(@PathVariable Long userId){
        List<FeedBackVO> feedBackVOS = feedBackService.listFeedBack(userId);
        return Result.success("反馈信息获取成功",feedBackVOS);
    }

    /**
     * 删除反馈记录
     * @return
     */
    @DeleteMapping("/feed_back/delete/{id}")
    public Result deleteFeedBack(@PathVariable Long id){
        feedBackService.deleteFeedBack(id);
        return Result.success("反馈信息删除成功！");
    }

}
