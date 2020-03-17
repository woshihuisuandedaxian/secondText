package com.jiaoyu.config;


import com.jiaoyu.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ZouXianTao
 * @Date2019/12/20
 */

/** 配置shiro的一些核心要素*/
@Configuration
public class ShiroConfig {
    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置Shiro核心 安全管理器 SecurityManager
     * SecurityManager安全管理器：所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；负责与后边介绍的其他组件进行交互。（类似于SpringMVC中的DispatcherServlet控制器）
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("myRealm")MyRealm myRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义的realm交给SecurityManager管理
        securityManager.setRealm(myRealm);
        return securityManager;
    }
    /**
     * 配置Shiro的Web过滤器，拦截浏览器请求并交给SecurityManager处理
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean webFilter(SecurityManager securityManager1){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager1);


        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        Map<String,String> map = new LinkedHashMap<>();

        map.put("/css/**","anon");
        map.put("/images/**","anon");
        map.put("/js/**","anon");
        map.put("/lib/**","anon");
        map.put("/skin/**","anon");

        //配置退出过滤器logout，由shiro实现
        map.put("/logout","logout");
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
        map.put("/code/getCode","anon");
        map.put("/toWelcome","anon");
        map.put("/manager/login","anon");
        map.put("/candidate/candidatePage","anon");
        map.put("/candidate/selectById","anon");
        map.put("/candidate/batchCandidate","anon");
        map.put("/candidate/resetCandidate","anon");
        map.put("/classRoom/selectClassRoom","anon");
        map.put("/classRoom/selectKaoAndSeat","anon");
        map.put("/classRoom/stopClassRoom","anon");
        map.put("/classRoom/startSelectClassRoom","anon");
        map.put("/classRoom/closeSelectClassRoom","anon");
        map.put("/classRoom/selectOccassion","anon");
        map.put("/person/soliderPage","anon");
        map.put("/person/batchSolider","anon");
        map.put("/person/successSolider","anon");
        map.put("/person/resetSolider","anon");
        map.put("/person/receiveKao","anon");
        map.put("/person/socialPage","anon");
        map.put("/person/batchSocial","anon");
        map.put("/person/successSocial","anon");
        map.put("/person/resetSocial","anon");
        map.put("/toLogin","anon");
        map.put("/**","authc");


        //设置默认登录的URL.
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    /** 自定义的Relam要配置*/
    @Bean
    public MyRealm myRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        //把MD5加密配置进自定义的Realm中
           MyRealm myRealm=new MyRealm();
           myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }



    /**
     * 配置密码比较器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        //此处的设置，true加密用的hex编码，false用的base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }



    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 开启aop注解支持
     * 即在controller中使用 @RequiresPermissions("user/userList")
     */


}
