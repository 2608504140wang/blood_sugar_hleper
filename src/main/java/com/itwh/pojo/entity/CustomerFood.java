package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerFood {

    //用户id
    private Long userId;

    //食物id
    private Long foodId;

    //食物选择次数
    private Long sum;

    //第一次选择时间
    private LocalDateTime first;

    //最近一次选择时间
    private LocalDateTime last;

}
