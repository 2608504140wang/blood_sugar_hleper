package com.itwh.serve.service;

import com.itwh.pojo.dto.FeedBackDTO;
import com.itwh.pojo.vo.FeedBackVO;
import com.itwh.pojo.vo.ListFeedBackVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedBackService {

    /**
     * 提交反馈
     *
     * @param feedBackDTO
     * @return
     */
    void saveFeedBack(FeedBackDTO feedBackDTO);

    /**
     * 获取单个用户的反馈信息
     * @param userId
     * @return
     */
    List<FeedBackVO> listFeedBack(Long userId);

    /**
     * 删除反馈记录
     * @return
     */
    void deleteFeedBack(Long id);

    /**
     * 获取所有用户的反馈信息
     * @param
     * @return
     */
    ListFeedBackVO listAllFeedBack();
}
