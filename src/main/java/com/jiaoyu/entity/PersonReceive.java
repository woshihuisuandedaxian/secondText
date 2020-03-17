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
@Table(name = "person_receive")
public class PersonReceive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;
  /** 录取的考生的名字*/
    private String personName;
   /**身份证号码 */
    private String shenfenzhengNum;
 /** 考生号*/
    private String kaoshengNum;
   /** 考生的分数*/
    private Double kaoshengScore;
   /** 录取的情况*/
    private String luquOccasion;
   /** 是否是退伍军人*/
    private String isOldSolider;


}