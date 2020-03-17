package com.jiaoyu.dao;

import com.jiaoyu.entity.Manager;
import tk.mybatis.mapper.common.Mapper;

public interface ManagerMapper extends Mapper<Manager>{

    Manager getManagerByName(String managerName);
}