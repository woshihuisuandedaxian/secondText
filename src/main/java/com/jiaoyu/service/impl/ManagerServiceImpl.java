package com.jiaoyu.service.impl;

import com.jiaoyu.common.service.impl.BaseServiceImpl;
import com.jiaoyu.dao.ManagerMapper;
import com.jiaoyu.entity.Manager;
import com.jiaoyu.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ZouXianTao
 * @Date2020/1/10
 */
@Service
public class ManagerServiceImpl extends BaseServiceImpl<Manager> implements IManagerService{
    @Autowired
    private ManagerMapper managerMapper;
    @Override
    protected Mapper<Manager> getMapper() {
        return managerMapper;
    }
   /** 根据管理员名字查询管理员*/
    @Override
    public Manager getManagerByName(String managerName) {
        return managerMapper.getManagerByName(managerName);
    }
}
