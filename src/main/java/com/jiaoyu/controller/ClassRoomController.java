package com.jiaoyu.controller;

import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.common.entity.ResultBean;
import com.jiaoyu.entity.Candidate;
import com.jiaoyu.entity.ManageRoom;
import com.jiaoyu.service.ICandidateService;
import com.jiaoyu.service.IClassRoomService;
import com.jiaoyu.vo.ProJo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "/classRoom")
public class ClassRoomController {
    @Autowired
    private IClassRoomService classRoomService;
    @Autowired
    private ICandidateService candidateService;
   /* @RequestMapping(value = "/selectClassRoom")
    public String selectClassRoom(HttpSession session, Page page){
        PageInfo<ManageRoom> pageInfo = classRoomService.getPage(page.getPageNum(), page.getPageSize());
        session.setAttribute("page",pageInfo);
        session.setAttribute("url","classRoom/selectClassRoom");

        return "classRoomList";
    }*/
   /** 输入考场名字搜索或者没有考场名字全搜索*/
   @RequestMapping(value = "/selectOccassion")
   public String selectOccassion(HttpSession session, Page page,String kaoName){
       //根据考场名字进行模糊查询，并进行分页操作
       //分页要在impl中进行，参数要传入进去

       PageInfo<ManageRoom> pageInfo = classRoomService.selectOccassion(page,kaoName);
       session.setAttribute("page",pageInfo);
       session.setAttribute("kaoName",kaoName);
       session.setAttribute("url","classRoom/selectOccassion");

       return "classRoomList";
   }
    /** 页面加载时显示开启.剩余考场数量，总座位数量，剩余座位数量等*/
    @RequestMapping(value = "/selectKaoAndSeat")
    @ResponseBody
    public ProJo selectKaoAndSeat(){
       //根据考场的状态是开启的为判断，求出开启考场的数量
        int startKao=classRoomService.selectStartCount();
        //根据考场的状态是停用的为判断，求出剩余的考场的数量
        int leftKao=classRoomService.selectLeftCount();
        /**开启的每一个考场的座位数量相加，就是总的开启考场的座位数量*/
           //求出开启的考场的集合，遍历集合求出每个考场的作为数量相加的值
          List<ManageRoom> manageRoomList= classRoomService.selectStartList();
          int sumSeat=0;
        for (ManageRoom manageRoom : manageRoomList) {
           sumSeat+=manageRoom.getTSeat();
        }
        /**开启的考场的作为剩余数量相加，就是总的开启考场的座位的剩余数量*/
           //求出开启的考场的集合，遍历求出每个考场的集合，然后把每个考场的集合的剩余座位数量相加
        int leftSeat=0;
        for (ManageRoom room : manageRoomList) {
            leftSeat+=room.getLeftSeat();
        }
        return new ProJo(startKao,leftKao,sumSeat,leftSeat);
    }
    /** 点击禁用，关闭这个考场*/
    @RequestMapping(value = "/stopClassRoom")
    @ResponseBody
    public ResultBean stopClassRoom(Integer id){
        ResultBean resultBean=new ResultBean();
    //根据传递过来的考场的id查询考场对象，然后修改考场对象的状态为禁用
        //如果考场存在着考生则不能禁用,根据这个考场的名字作为条件查询考生的数量，若不为0则说明有考生，不能禁用
        ManageRoom room = classRoomService.getById(id);
        Candidate candidate=new Candidate();
        candidate.setCandidateOccasion(room.getTName());
        int count = candidateService.selectCount(candidate);
        if(count>0){
           resultBean.setData("400");
           return resultBean;
        }else{

            room.setStatus("禁用");
            int result = classRoomService.udpate(room);

            if(result>0){
                resultBean.setResult(true);
                resultBean.setData("200");
                return resultBean;
            }
            return resultBean;
        }
        }

    /** 点击启用，启用这个考场*/
    @RequestMapping(value = "/startClassRoom")
    @ResponseBody
    public ResultBean startClassRoom(Integer id){
       //根据考场id查询考场对象，然后修改它的状态为启用
        ResultBean resultBean=new ResultBean();
        ManageRoom room = classRoomService.getById(id);
        room.setStatus("启用");
        int result = classRoomService.udpate(room);
        if(result>0){
            resultBean.setResult(true);
            return resultBean;

        }
        return resultBean;
    }
    /** 点击启用按钮时批量给选中的考场进行启用*/
    @RequestMapping(value = "/startSelectClassRoom")
    @ResponseBody
    public ResultBean startSelectClassRoom(@RequestParam List<Integer> idsList){
       //判断是不是选择了状态已经为开启的考场，如果状态是开启的考场则返回该考场已经是开启状态
        //根据传递过来的考场的id找到相应的考场，修改它的状态为开启状态
        ResultBean resultBean=new ResultBean();
        /** 如果它勾选的复选框中有是开启状态的则返回已经是开启状态*/
        for (Integer classRoomId : idsList) {
            ManageRoom room = classRoomService.getById(classRoomId);
            if(room.getStatus().equals("启用")){
                 resultBean.setData("400");
                 return resultBean;
            }
        }
     /** 勾选的都是禁用状态的复选框*/
        for (Integer roomId : idsList) {
            ManageRoom manageRoom = classRoomService.getById(roomId);
            manageRoom.setStatus("启用");
            int result = classRoomService.udpate(manageRoom);
            if(result>0){
                resultBean.setData("200");
                resultBean.setResult(true);

            }

        }

        return resultBean;

    }
    /** 点击启用按钮时批量给选中的考场进行启用*/
    @RequestMapping(value = "/closeSelectClassRoom")
    @ResponseBody
    public ResultBean closeSelectClassRoom(@RequestParam List<Integer> idsList){
        //判断这个传过来的考场是不是有考生存在的，如果有考生存在的则不能禁用
        //根据传过来的id获取考场对象，修改考场状态为禁用
        ResultBean resultBean=new ResultBean();
        /** 选择禁用的考场存在考生的情况则返回400*/
        for (Integer closeRoomId : idsList) {
            ManageRoom room = classRoomService.getById(closeRoomId);
            Candidate candidate=new Candidate();
            candidate.setCandidateOccasion(room.getTName());
            int count = candidateService.selectCount(candidate);
            if(count>0) {
                resultBean.setData("400");
                return resultBean;
            }
        }

       /** 选择禁用的考场都是没有考生存在的考场*/
        for (Integer singleRoomId : idsList) {
            ManageRoom manageRoom = classRoomService.getById(singleRoomId);
            //把考场对象的状态设置为禁用返回data未200，result为true
            manageRoom.setStatus("禁用");
            int result = classRoomService.udpate(manageRoom);
            resultBean.setData("200");
            if(result>0){
                resultBean.setResult(true);
            }
        }
        return resultBean;
    }
}
