package com.jiaoyu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZouXianTao
 * @Date2020/1/9
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    /** 配置映射路径*/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("toLogin").setViewName("login");
        registry.addViewController("toWelcome").setViewName("welcome");

    }
}
