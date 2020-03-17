package com.jiaoyu.controller;


import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;

import com.jiaoyu.common.entity.ResultBean;
import com.jiaoyu.entity.Candidate;
import com.jiaoyu.entity.ManageRoom;
import com.jiaoyu.service.ICandidateService;
import com.jiaoyu.service.IClassRoomService;
import com.sun.scenario.animation.AnimationPulseMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/13
 */
@Controller
@RequestMapping(value = "/candidate")
public class CandidateController {
    @Autowired
    private ICandidateService candidateService;
    @Autowired
    private IClassRoomService classRoomService;
    /** 点击考生所有信息，查询考生的所有信息集合并且分页展示*/
   /* @RequestMapping(value = "/candidatePage")
    public String getPage(HttpSession session, Page page){
        PageInfo<Candidate> pageInfo = candidateService.getPage(page.getPageNum(), page.getPageSize());
       session.setAttribute("url","candidate/candidatePage");
       session.setAttribute("page",pageInfo);
        return "candidateList";

    }*/

    /** 点击考生所有信息，查询考生的所有信息集合并且分页展示+模糊查询*/
    @RequestMapping(value = "/candidatePage")
    public String getPage(HttpSession session, Page page,String information){
        //PageInfo<Candidate> pageInfo = candidateService.getPage(page.getPageNum(), page.getPageSize());
      PageInfo<Candidate> pageInfo=  candidateService.getPageByCondition(page,information);
        session.setAttribute("url","candidate/candidatePage");
        session.setAttribute("information",information);
        session.setAttribute("page",pageInfo);
        systemout("----");
        return "candidateList";

    }

    /** 根据考生id查询考生信息*/
    @RequestMapping(value = "/selectById")
    public String selectById(Integer id,ModelMap map){
        Candidate candidate = candidateService.getById(id);
           map.put("candidate",candidate);
        return "candidate_single";
    }
    /** 根据传过来的要分配的考生的id集合，然后进行考生考场分配*/
    @RequestMapping(value = "/batchCandidate")
    @ResponseBody
    public ResultBean batchCandidate(@RequestParam List<Integer> idsList){

        //若传过来的id的信息中考场，教室号，座位号中只要有一个值不是未分配，则说明该考生已经有考场，不能进行分配
            //根据考生的id查找这个考生对象，然后查看它的考场的状态，若不是未分配，则返回该考生已经有考场
        ResultBean resultBean=new ResultBean();
        resultBean.setResult(true);
        for (Integer candidateId : idsList) {
            Candidate candidate = candidateService.getById(candidateId);
            if(!candidate.getClassroom().equals("未分配")){
                resultBean.setResult(false);
                return resultBean;
            }
        }


        //根据传过来的考生id给他分配考场，教室号，座位号，从剩余的座位号中的相应考场，然后相应进行减剩余座位号
            //查找考场剩余座位号不为0且考场的状态为启动的考场的集合，然后根据传过来的考场id查找相应的考生对象，然后修改该考生的考场，教室，座位号(在查找出的考场的对象进行相应的赋值)
        //没有考场的座位号后面跟一个数字确定还没有分配的考场号

         List<ManageRoom> manageRoomList=classRoomService.selectLeftList();

        for (ManageRoom room : manageRoomList) {
          //有座位剩余的考场的座位数量
            Integer leftSeat = room.getLeftSeat();
            int i=leftSeat;
            for (Integer candiId : idsList) {
                //根据传过来的考生id查找到相应的考生对象
                //截取未分配后面的未分配考场号

                Candidate candidate = candidateService.getById(candiId);
                String seatNum = candidate.getSeatNum();
                if(seatNum.contains("未分配")){
                    String realSeatNum = seatNum.substring(seatNum.lastIndexOf("配")+1);
                    //把考生的对象赋相应的值
                    candidate.setCandidateOccasion(room.getTName());
                    candidate.setClassroom(room.getTClassroom());
                    candidate.setSeatNum(realSeatNum+"号");
                    candidateService.udpate(candidate);
                    //考场号肯定是在这个考场最大考场号和最小考场号之间，而且要是没有被其他人使用过的考场号
                    //怎么求出这个有座位剩余的考场的其它的被使用的考场号
                    //遍历考生集合，求出考场和教室是这个剩余考场的考生集合，然后求出这个考场已经使用过的考场号
                    i--;
                    if(i==0){
                        room.setLeftSeat(0);
                        classRoomService.udpate(room);
                        break;
                    }
                }

            }
        }
        return resultBean;
    }
    /** 取消考生的考场*/
    @RequestMapping(value = "/resetCandidate")
    @ResponseBody
    public ResultBean resetCandidate(@RequestParam List<Integer> idsList){
        //根据考生id查找考生的对象，然后获取考生的座位号，然后截取座位号的相应数字和考场
        //遍历考生号的集合，对考生号的相应的对象进行修改，把考场，教室都设为未分配，把座位号设置为未分配座位号的数字
        //考生对应的考场的剩余座位要+1
        ResultBean resultBean=new ResultBean();
        resultBean.setResult(false);
        for (Integer candidateId : idsList) {
            Candidate candidate = candidateService.getById(candidateId);
            String seatNum = candidate.getSeatNum();
            seatNum=new StringBuffer(seatNum).reverse().toString();
            //获取数字的座位号
            String seatNum1 = seatNum.substring(seatNum.lastIndexOf("号") + 1);
            //获取考场
            String candidateOccasion = candidate.getCandidateOccasion();
            ManageRoom manageRoom=classRoomService.getClassRoomByName(candidateOccasion);
            manageRoom.setLeftSeat(manageRoom.getLeftSeat()+1);
            int result = classRoomService.udpate(manageRoom);
            if(result>0){
                resultBean.setResult(true);
            }
            //把考场，教室设置为未分配，把座位号设置为未分配座位号数字
            candidate.setCandidateOccasion("未分配");
            candidate.setClassroom("未分配");
            candidate.setSeatNum("未分配"+seatNum1);
            candidateService.udpate(candidate);
            //根据考场名查找到对应的考场对象


        }

        return resultBean;
    }
}
