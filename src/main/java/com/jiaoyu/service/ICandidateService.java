package com.jiaoyu.service;

import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.service.IBaseService;
import com.jiaoyu.entity.Candidate;

/**
 * @author ZouXianTao
 * @Date2020/1/13
 */
public interface ICandidateService extends IBaseService<Candidate>{
    PageInfo<Candidate> getPageByCondition(Page page, String information);

    int selectSumKao();

    int selectSumSolider();
}
