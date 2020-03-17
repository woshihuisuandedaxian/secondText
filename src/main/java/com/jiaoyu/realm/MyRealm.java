package com.jiaoyu.realm;


import com.jiaoyu.entity.Manager;
import com.jiaoyu.service.IManagerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ZouXianTao
 * @Date2019/12/20
 */

public class MyRealm extends AuthorizingRealm{
  @Autowired
  private IManagerService managerService;
    /** 权限认证*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
      /*  System.out.println("MyRealm.doGetAuthorizationInfo");
        //拿到当前登录的用户
      User user= (User) principal.getPrimaryPrincipal();
      //根据当前用户id查询用户的菜单
        //遍历菜单集合，给用户菜单类型为3的菜单添加那个路径，也就是增删改查员工的跳转地址

        Set<String> stringPersions=new HashSet<String>();


        List<Menu> menuList = roleMenuService.selectMenuByUserId(user.getUserId());
        for (Menu menu : menuList) {
            if(menu.getMenuType()==3){
               stringPersions.add(menu.getMenuPath());
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(stringPersions);
        return simpleAuthorizationInfo;*/
      return null;
    }
   /** 登录认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("MyRealm.doGetAuthenticationInfo,认证登录");
            UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
            //根据用户名查询用户对象
        String managerName = token.getUsername();
        Manager manager=managerService.getManagerByName(managerName);

        if(manager!=null){
            /** 权限中的MD5加密*/
            //判断密码
            //家盐值的，密码进行对比
            ByteSource byteSource=ByteSource.Util.bytes(managerName);
            return new SimpleAuthenticationInfo(manager,manager.getPassword(),byteSource,this.getName());

        }
  return null;

    }
}
