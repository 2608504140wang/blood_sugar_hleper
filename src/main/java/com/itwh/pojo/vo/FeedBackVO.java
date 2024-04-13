package com.itwh.pojo.vo;

import com.itwh.pojo.entity.FeedBackPicture;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedBackVO {

    private Long id;

    //内容
    private String word;

    //类型
    private String type;

    //联系方式
    private String mobile;

    //提交时间
    private LocalDateTime createTime;

    //反馈图片
    private List<FeedBackPicture> feedBackPictures;

}
