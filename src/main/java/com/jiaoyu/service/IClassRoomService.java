package com.jiaoyu.service;

import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.service.IBaseService;
import com.jiaoyu.entity.ManageRoom;

import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/14
 */
public interface IClassRoomService extends IBaseService<ManageRoom> {
    int selectStartCount();

    int selectLeftCount();

    List<ManageRoom> selectStartList();

    List<ManageRoom> selectLeftList();

    ManageRoom getClassRoomByName(String candidateOccasion);

    PageInfo<ManageRoom> selectOccassion(Page page, String kaoName);
}
