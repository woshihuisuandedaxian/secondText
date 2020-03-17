package com.jiaoyu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.service.impl.BaseServiceImpl;
import com.jiaoyu.dao.PersonReceiveMapper;
import com.jiaoyu.entity.PersonReceive;
import com.jiaoyu.service.IPersonReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/31
 */
@Service
public class PersonReveiveServiceImpl extends BaseServiceImpl<PersonReceive> implements IPersonReceiveService {
    @Autowired
    private PersonReceiveMapper personReceiveMapper;
    @Override
    protected Mapper<PersonReceive> getMapper() {
        return personReceiveMapper;
    }
  /** 查询退伍军人的录取的人数的集合(包括模糊查询)*/
    @Override
    public PageInfo<PersonReceive> soliderPage(Page page, String soliderName) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<PersonReceive> list=personReceiveMapper.soliderPage(soliderName);
        return new PageInfo<>(list);
    }
    /** 查询社会人员的录取的人数的集合(包括模糊查询)*/
    @Override
    public PageInfo<PersonReceive> socialPage(Page page, String socialName) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<PersonReceive> list=personReceiveMapper.socialPage(socialName);
        return new PageInfo<>(list);
    }
  /** 查询退伍军人的录取人数*/
    @Override
    public int selectReceiveSolider() {
       return personReceiveMapper.selectReceiveSolider();

    }
/** 查询社会人员的录取人数*/
    @Override
    public int selectReceiveSocial() {
        return personReceiveMapper.selectReceiveSocial();
    }
}
