package com.jiaoyu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.service.impl.BaseServiceImpl;
import com.jiaoyu.dao.ManageRoomMapper;
import com.jiaoyu.entity.ManageRoom;
import com.jiaoyu.service.IClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/14
 */
@Service
public class ClassRoomImpl extends BaseServiceImpl<ManageRoom> implements IClassRoomService {
    @Autowired
    private ManageRoomMapper manageRoomMapper;
    @Override
    protected Mapper<ManageRoom> getMapper() {
        return manageRoomMapper;
    }
  /** 根据考场的开启状态为开启求出开启考场的数量*/
    @Override
    public int selectStartCount() {
        ManageRoom manageRoom=new ManageRoom();
        manageRoom.setStatus("启用");
       return manageRoomMapper.selectCount(manageRoom);
    }
    /** 根据考场的开启状态为禁用求出剩余考场的数量*/
    @Override
    public int selectLeftCount() {
        ManageRoom manageRoom=new ManageRoom();
        manageRoom.setStatus("禁用");
        return manageRoomMapper.selectCount(manageRoom);
    }
  /** 根据考场的状态为开启状态，求出考场集合*/
    @Override
    public List<ManageRoom> selectStartList() {
        ManageRoom manageRoom=new ManageRoom();
        manageRoom.setStatus("启用");
         return manageRoomMapper.select(manageRoom);
    }
   /** 查找状态为启用且还有剩余座位的考场的集合*/
    @Override
    public List<ManageRoom> selectLeftList() {
        return manageRoomMapper.selectLeftList();

    }
  /** 根据考场名查找考场对象*/
    @Override
    public ManageRoom getClassRoomByName(String candidateOccasion) {

        return manageRoomMapper.getClassRoomByName(candidateOccasion);
    }
  /** 根据出入的考场的名字进行模糊查询*/
    @Override
    public PageInfo<ManageRoom> selectOccassion(Page page, String kaoName) {
        //先把分页信息在这里设置了，然后把参数传入进去
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<ManageRoom> list=manageRoomMapper.selectOccassion(kaoName);
        return new PageInfo<>(list);
    }
}
