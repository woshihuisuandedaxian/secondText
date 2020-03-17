package com.jiaoyu.service;

import com.jiaoyu.common.service.IBaseService;
import com.jiaoyu.entity.Manager;

/**
 * @author ZouXianTao
 * @Date2020/1/10
 */
public interface IManagerService extends IBaseService<Manager> {
    Manager getManagerByName(String managerName);
}
