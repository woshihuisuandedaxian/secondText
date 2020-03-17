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
@Table(name = "t_candidate")
public class Candidate {
    /** 考生id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateId;
   /** 考生名字*/
    private String candidateName;
   /** 考生性别*/
    private String sex;
  /** 身份证号*/
    private String cardNum;
  /** 考生号*/
    private String candidateNum;
   /** 考场*/
    private String candidateOccasion;
    /** 所在教室*/
    private String classroom;
   /** 座位号*/
    private String seatNum;
   /** 是否是退伍军人*/
    private String isOldSolider;


}