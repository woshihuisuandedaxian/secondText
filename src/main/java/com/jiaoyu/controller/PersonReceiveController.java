package com.jiaoyu.controller;

import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.entity.ResultBean;
import com.jiaoyu.entity.Candidate;
import com.jiaoyu.entity.PersonReceive;
import com.jiaoyu.service.ICandidateService;
import com.jiaoyu.service.IPersonReceiveService;
import com.jiaoyu.vo.ReceiveProJo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/31
 */
@Controller
@RequestMapping(value = "/person")
public class PersonReceiveController {

    @Autowired
    private IPersonReceiveService personReceiveService;
    @Autowired
    private ICandidateService candidateService;
    /** 录取人数页面加载时显示页面头上的录取相关的信息*/
    @RequestMapping(value = "/receiveKao")
    @ResponseBody
    public ReceiveProJo receiveKao(){
        /**求出总的参加考生的人数,求出退伍军人参考的总人数，求出录取的退伍军人的人数
        求出总的社会人员参考的总数，求出录取的社会人员的人数*/
        //求出总的参加考试的人数
        ReceiveProJo receiveProJo=new ReceiveProJo();
        int count=candidateService.selectSumKao();
      receiveProJo.setSumkao(count);
      //求出总的参加考试的退伍军人的数量
        int count1=candidateService.selectSumSolider();
        receiveProJo.setAllSolider(count1);
      //求出录取的退伍军人的数量
      int count2=personReceiveService.selectReceiveSolider();
      receiveProJo.setReceiveSolider(count2);
      //求出总的参加考试的社会人员的数量
        Candidate candidate=new Candidate();
        candidate.setIsOldSolider("否");
        int count3 = candidateService.selectCount(candidate);
        receiveProJo.setAllSocial(count3);
        int count4=personReceiveService.selectReceiveSocial();
        receiveProJo.setReceiveSocial(count4);
        return receiveProJo;
    }
    /** 查询是退伍军人的录取考生信息(用模糊查询，输入考生姓名)*/
    @RequestMapping(value = "/soliderPage")
    public String soliderPage(String soliderName, Page page, HttpSession session){
        //根据传入的考生的姓名进行模糊并且分页查询
        PageInfo<PersonReceive> pageInfo=personReceiveService.soliderPage(page,soliderName);
        session.setAttribute("page",pageInfo);
        session.setAttribute("soliderName",soliderName);
        session.setAttribute("url","person/soliderPage");
        return "soliderList";
    }
    /** 点击拟录取，给考生拟录取(退伍军人)*/
    @RequestMapping(value = "/batchSolider")
    @ResponseBody
    public ResultBean batchSolider(@RequestParam List<Integer> idsList){
        //根据传入的录取id进行批量的拟录取考生
        //修改考生的录取状态，把录取状况设置为拟录取
        ResultBean resultBean=new ResultBean();
        resultBean.setResult(false);
        for (Integer soliderId : idsList) {
             PersonReceive personReceive=new PersonReceive();
             personReceive.setLuquOccasion("拟录取");
             personReceive.setPersonId(soliderId);
            int result = personReceiveService.udpate(personReceive);
            if(result>0){
                resultBean.setResult(true);
            }

        }
     return resultBean;
    }
    /** 点击录取，给考生录取(退伍军人)*/
    @RequestMapping(value = "/successSolider")
    @ResponseBody
    public ResultBean successSolider(@RequestParam List<Integer> idsList){
        //根据传入的录取id进行批量的拟录取考生
        //修改考生的录取状态，把录取状况设置为拟录取
        ResultBean resultBean=new ResultBean();
        resultBean.setResult(false);
        for (Integer soliderId3 : idsList) {
            PersonReceive personReceive=new PersonReceive();
            personReceive.setLuquOccasion("录取");
            personReceive.setPersonId(soliderId3);
            int result = personReceiveService.udpate(personReceive);
            if(result>0){
                resultBean.setResult(true);
            }

        }
        return resultBean;
    }
    /** 点击未录取，考生变成未录取(退伍军人)*/
    @RequestMapping(value = "/resetSolider")
    @ResponseBody
    public ResultBean resetSolider(@RequestParam List<Integer> idsList){

        ResultBean resultBean=new ResultBean();
        resultBean.setResult(false);
        for (Integer soliderId2 : idsList) {
            PersonReceive personReceive=new PersonReceive();
            personReceive.setLuquOccasion("未录取");
            personReceive.setPersonId(soliderId2);
            int result = personReceiveService.udpate(personReceive);
            if(result>0){
                resultBean.setResult(true);
            }

        }
        return resultBean;
    }
    /** 查询是社会人员的录取考生信息(用模糊查询，输入考生姓名)*/
    @RequestMapping(value = "/socialPage")
    public String socialPage(String socialName, Page page, HttpSession session){
        //根据传入的考生的姓名进行模糊并且分页查询
        PageInfo<PersonReceive> pageInfo=personReceiveService.socialPage(page,socialName);
        session.setAttribute("page",pageInfo);
        session.setAttribute("socialName",socialName);
        session.setAttribute("url","person/socialPage");
        return "socialList";
    }
    /** 点击拟录取，给考生拟录取(社会人员)*/
    @RequestMapping(value = "/batchSocial")
    @ResponseBody
    public ResultBean batchSocial(@RequestParam List<Integer> idsList){
        //根据传入的录取id进行批量的拟录取考生
        //修改考生的录取状态，把录取状况设置为拟录取
        ResultBean resultBean=new ResultBean();
        resultBean.setResult(false);
        for (Integer socialId : idsList) {
            PersonReceive receive=new PersonReceive();
            receive.setLuquOccasion("拟录取");
            receive.setPersonId(socialId);
            int result = personReceiveService.udpate(receive);
            if(result>0){
                resultBean.setResult(true);
            }

        }
        return resultBean;
    }
    /** 点击录取，给考生录取(社会人员)*/
    @RequestMapping(value = "/successSocial")
    @ResponseBody
    public ResultBean successSocial(@RequestParam List<Integer> idsList){
        //根据传入的录取id进行批量的拟录取考生
        //修改考生的录取状态，把录取状况设置为拟录取
        ResultBean resultBean=new ResultBean();
        resultBean.setResult(false);
        for (Integer socialId1 : idsList) {
            PersonReceive personReceive=new PersonReceive();
            personReceive.setLuquOccasion("录取");
            personReceive.setPersonId(socialId1);
            int result = personReceiveService.udpate(personReceive);
            if(result>0){
                resultBean.setResult(true);
            }

        }
        return resultBean;
    }
    /** 点击未录取，考生变成未录取(社会人员)*/
    @RequestMapping(value = "/resetSocial")
    @ResponseBody
    public ResultBean resetSocial(@RequestParam List<Integer> idsList){

        ResultBean resultBean=new ResultBean();
        resultBean.setResult(false);
        for (Integer soliderId2 : idsList) {
            PersonReceive personReceive=new PersonReceive();
            personReceive.setLuquOccasion("未录取");
            personReceive.setPersonId(soliderId2);
            int result = personReceiveService.udpate(personReceive);
            if(result>0){
                resultBean.setResult(true);
            }

        }
        return resultBean;
    }


}
