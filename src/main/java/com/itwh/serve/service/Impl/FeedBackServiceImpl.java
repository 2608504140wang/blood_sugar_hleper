package com.itwh.serve.service.Impl;

import com.itwh.common.context.BaseContext;
import com.itwh.pojo.dto.FeedBackDTO;
import com.itwh.pojo.entity.FeedBack;
import com.itwh.pojo.entity.FeedBackPicture;
import com.itwh.pojo.vo.FeedBackVO;
import com.itwh.pojo.vo.ListFeedBackVO;
import com.itwh.serve.mapper.FeedBackMapper;
import com.itwh.serve.mapper.FeedBackPictureMapper;
import com.itwh.serve.mapper.UserAndRoleMapper;
import com.itwh.serve.service.FeedBackService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackMapper feedBackMapper;

    @Autowired
    private FeedBackPictureMapper feedBackPictureMapper;

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;

    /**
     * 提交反馈
     * @param feedBackDTO
     * @return
     */
    public void saveFeedBack(FeedBackDTO feedBackDTO) {
        //提交反馈信息
        FeedBack feedBack = new FeedBack();
        BeanUtils.copyProperties(feedBackDTO, feedBack);
        feedBack.setUserId(BaseContext.getCurrentId());
        feedBack.setCreateTime(LocalDateTime.now());
        feedBackMapper.save(feedBack);

        //插入反馈图片
        List<FeedBackPicture> feedBackPictures = feedBackDTO.getFeedBackPictures();
        if (feedBackPictures != null && feedBackPictures.size() > 0){
            feedBackPictures.forEach(feedBackPicture -> feedBackPicture.setFeedBackId(feedBack.getId()));
            feedBackPictureMapper.saveBatch(feedBackPictures);
        }
    }

    /**
     * 获取单个用户的反馈信息
     * @param userId
     * @return
     */
    public List<FeedBackVO> listFeedBack(Long userId) {
        List<FeedBackVO> feedBackVOS = new ArrayList<>();
        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(userId);
        List<FeedBack> feedBacks = feedBackMapper.list(feedBack);
        for (FeedBack feedBack1 : feedBacks){
            FeedBackVO feedBackVO = new FeedBackVO();
            BeanUtils.copyProperties(feedBack1, feedBackVO);
            //查询反馈图片信息
            List<FeedBackPicture> feedBackPictures = feedBackPictureMapper.list(feedBack1.getId());
            feedBackVO.setFeedBackPictures(feedBackPictures);
            feedBackVOS.add(feedBackVO);
        }
        return feedBackVOS;
    }

    /**
     * 获取所有用户的反馈信息
     * @param
     * @return
     */
    public ListFeedBackVO listAllFeedBack() {
        ListFeedBackVO listFeedBackVO = new ListFeedBackVO();
        List<List<FeedBackVO>> feedBackVOList = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();

        //获取所有用户的id
        List<Long> userIdList = userAndRoleMapper.listUserIdByRole("普通用户");
        for (Long userId : userIdList){
            userIds.add(userId);
            List<FeedBackVO> feedBackVOS = listFeedBack(userId);
            feedBackVOList.add(feedBackVOS);
        }
        listFeedBackVO.setUserIds(userIds);
        listFeedBackVO.setFeedBackVOList(feedBackVOList);
        return listFeedBackVO;
    }

    /**
     * 删除反馈记录
     * @return
     */
    public void deleteFeedBack(Long id) {
        //删除反馈信息
        feedBackMapper.delete(id);

        //删除反馈信息对应的图片
        feedBackPictureMapper.delete(id);
    }


}
