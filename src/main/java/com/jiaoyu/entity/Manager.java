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
@Table(name = "t_manager")
public class Manager {
    /** 管理员id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manageId;
    /** 管理员名字*/
    private String managerName;
   /** 密码*/
    private String password;
   /** 管理员角色*/
    private String access;
   /** 管理员状态*/
    private String status;


}