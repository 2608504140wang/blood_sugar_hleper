package com.itwh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportDetailAndRecord {

    private Long id;

    //运动id
    private Long sportId;

    //运动记录id
    private Long recordId;

    //运动时间 (分钟)
    private Long time;

}
