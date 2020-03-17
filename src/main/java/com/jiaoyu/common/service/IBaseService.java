package com.jiaoyu.common.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/10
 */
public interface IBaseService<T> {
    /** 新增*/
    public int add(T t);
    /** 修改*/
    public int udpate(T t);
    /** 通过id查询对象*/
    public T getById(Integer id);
    /** 根据id进行删除*/
    public int delete(Integer id);
    /** 查询集合*/
    public List<T> getList();
    /** 分页*/
    public PageInfo<T> getPage(Integer pageNum, Integer pageSize);
    /** 根据对象查询数量*/
    int selectCount(T t);
}
