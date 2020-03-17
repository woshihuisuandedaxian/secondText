package com.jiaoyu.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.service.IBaseService;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author ZouXianTao
 * @Date2019/12/17
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {
    protected abstract Mapper<T> getMapper();
    /** 新增方法*/
    @Override
    public int add(T t) {
        return getMapper().insert(t);
    }
  /** 修改方法*/
    @Override
    public int udpate(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }
  /** 根据id查询对象*/
    @Override
    public T getById(Integer id) {
        return getMapper().selectByPrimaryKey(id);
    }
  /** 根据id删除对象*/
    @Override
    public int delete(Integer id) {
        return getMapper().deleteByPrimaryKey(id);
    }
   /** 查询集合*/
    @Override
    public List<T> getList() {
        return getMapper().select(null);
    }
   /** 进行分页*/
    @Override
    public PageInfo<T> getPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<T> list=getMapper().select(null);
        return new PageInfo<>(list);
    }
    /** 根据对象查询数量*/
    @Override
    public int selectCount(T t) {
        return getMapper().selectCount(t);
    }
}
