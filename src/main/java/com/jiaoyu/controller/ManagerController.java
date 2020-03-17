package com.jiaoyu.controller;

import com.github.pagehelper.PageInfo;
import com.jiaoyu.common.entity.Page;
import com.jiaoyu.entity.Candidate;
import com.jiaoyu.entity.Manager;
import com.jiaoyu.service.ICandidateService;
import com.jiaoyu.service.IManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ZouXianTao
 * @Date2020/1/10
 */
@Controller
@RequestMapping(value = "/manager")
public class ManagerController {
    @Autowired
    private ICandidateService candidateService;
    @RequestMapping(value = "/login")
   public String login(String code, Manager manager, HttpSession session, Page page){
        String codeText = (String) session.getAttribute("codeText");
        if(code!=null&&code.equalsIgnoreCase(codeText)){
            /**用shiro搞定登录认证*/
            //得到当前用户对象
            Subject currentManager = SecurityUtils.getSubject();
            if(!currentManager.isAuthenticated()){//没有登录
                UsernamePasswordToken token=new UsernamePasswordToken(manager.getManagerName(),manager.getPassword());

                try {
                    //登录认证
                     currentManager.login(token);

                }catch (AuthenticationException e) {
                    System.out.println("UserController.login,用户密码或者账号错误");
                    try {
                        return "login";
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            //得到realm中认证方法中这个对象的第一个参数,SimpleAuthenticationInfo(user,user.getUserPassword(),byteSource,this.getName())
           /* User user1= (User) currentManager.getPrincipal();
            session.setAttribute("LOGIN_USER",user1);

            //跳转到首页前，根据用户id查询到用户有的菜单集合，然后在首页中展示出来
            List<Menu> menuList=menuService.selectMenuByUserId(user1.getUserId());


            map.put("menuList",menuList);*/
           //查询考生信息集合
            PageInfo<Candidate> pageInfo=candidateService.getPage(page.getPageNum(),page.getPageSize());
           //得到登录用户，保存到session中

           Manager manager1 = (Manager) currentManager.getPrincipal();
            session.setAttribute("LOGIN_MANAGER",manager);
           session.setAttribute("url","candidate/candidatePage");
           session.setAttribute("page",pageInfo);



            return "index";

        }else{
            return "login";
        }

    }
}
