package com.jiaoyu.dao;

import com.jiaoyu.entity.PersonReceive;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PersonReceiveMapper extends Mapper<PersonReceive>{

    List<PersonReceive> soliderPage(@Param("soliderName") String soliderName);

    List<PersonReceive> socialPage(@Param("socialName") String socialName);

    int selectReceiveSolider();

    int selectReceiveSocial();
}