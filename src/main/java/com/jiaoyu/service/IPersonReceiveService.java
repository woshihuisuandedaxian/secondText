package com.jiaoyu.service;

import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.service.IBaseService;
import com.jiaoyu.entity.PersonReceive;

/**
 * @author ZouXianTao
 * @Date2020/1/31
 */
public interface IPersonReceiveService extends IBaseService<PersonReceive> {
    PageInfo<PersonReceive> soliderPage(Page page, String soliderName);

    PageInfo<PersonReceive> socialPage(Page page, String socialName);

    int selectReceiveSolider();

    int selectReceiveSocial();
}
