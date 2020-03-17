package com.jiaoyu.dao;

import com.jiaoyu.entity.ManageRoom;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ManageRoomMapper extends Mapper<ManageRoom>{

    List<ManageRoom>selectLeftList();

    ManageRoom getClassRoomByName(String candidateOccasion);

    List<ManageRoom> selectOccassion(@Param("kaoName") String kaoName);
}