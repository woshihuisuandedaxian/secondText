package com.jiaoyu.vo;

/**
 * @author ZouXianTao
 * @Date2020/1/31
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 为了方便录取的相关信息可以展示的实体类*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveProJo {
    private Integer sumkao;
    private  Integer allSolider;
    private Integer receiveSolider;
    private Integer allSocial;
    private Integer receiveSocial;

}
