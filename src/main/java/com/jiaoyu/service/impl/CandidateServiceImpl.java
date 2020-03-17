package com.jiaoyu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.service.impl.BaseServiceImpl;
import com.jiaoyu.dao.CandidateMapper;
import com.jiaoyu.entity.Candidate;
import com.jiaoyu.service.ICandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/13
 */
@Service
public class CandidateServiceImpl extends BaseServiceImpl<Candidate> implements ICandidateService{
    @Autowired
    private CandidateMapper candidateMapper;
    @Override
    protected Mapper<Candidate> getMapper() {
        return candidateMapper;
    }
  /** 根据条件进行查询并进行分页展示*/
    @Override
    public PageInfo<Candidate> getPageByCondition(Page page, String information) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Candidate> list=candidateMapper.getPageByCondition(information);
        return new PageInfo<>(list);
    }
  /** 求出总的参加考试的人数*/
    @Override
    public int selectSumKao() {
        return candidateMapper.selectCount(null);
    }
 /** 求出总的退伍军人的参加考试的人数*/
    @Override
    public int selectSumSolider() {
        Candidate candidate=new Candidate();
        candidate.setIsOldSolider("是");
        return  candidateMapper.selectCount(candidate);

    }

}
