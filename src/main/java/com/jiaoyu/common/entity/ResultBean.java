package com.jiaoyu.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZouXianTao
 * @Date2019/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean {
    private boolean result;
    private String data;
}
