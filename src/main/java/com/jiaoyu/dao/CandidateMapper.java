package com.jiaoyu.dao;

import com.jiaoyu.entity.Candidate;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CandidateMapper extends Mapper<Candidate>{


    List<Candidate> getPageByCondition(@Param("information") String information);
}