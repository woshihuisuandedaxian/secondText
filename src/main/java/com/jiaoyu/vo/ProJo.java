package com.jiaoyu.vo;

/**
 * @author ZouXianTao
 * @Date2020/1/15
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 为了方便前端头部显示考场和作为的数量而建立的实体类*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProJo {
    /** 开启考场数量*/
    private Integer startKao;
    /** 剩余考场数量*/
    private Integer leftKao;
    /** 开启的考场的总座位数量*/
    private Integer sumSeat;
    /** 开启的考场的剩余的座位数量*/
    private Integer leftSeat;
}
