package com.jiaoyu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "manage_classroom")
public class ManageRoom {
    /** 考场的id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classroomId;
    /** 考场名字*/
    private String tName;
  /**考场地点*/
    private String tClassroom;
  /** 座位数量*/
    private Integer tSeat;
  /** 考场批次*/
    private String tOrder;
   /** 座位剩余数量*/
    private Integer leftSeat;
 /** 考场开启状态*/
    private String status;


}