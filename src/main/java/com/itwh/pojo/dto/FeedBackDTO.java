package com.itwh.pojo.dto;

import com.itwh.pojo.entity.FeedBackPicture;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedBackDTO {

    //内容
    private String word;

    //类型
    private String type;

    //联系方式
    private String mobile;

    //反馈图片
    private List<FeedBackPicture> feedBackPictures;
}
