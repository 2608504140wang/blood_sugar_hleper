package com.itwh.pojo.vo;

import lombok.Data;

import java.util.List;


@Data
public class ListFeedBackVO {

    //用户id
    private List<Long> userIds;

    //用户所有的反馈
    private List<List<FeedBackVO>> feedBackVOList;

}
