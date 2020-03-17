package com.jiaoyu.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author ZouXianTao
 * @Date2020/1/9
 */
@Controller
@RequestMapping(value = "/code")
public class CodeController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    /** 点击图片或者看不清的文字返回一个验证码给前端*/
    @RequestMapping(value = "/getCode")
    public void getCode(HttpServletResponse response, HttpSession session){
        //获得验证码的文本
        String text = defaultKaptcha.createText();
        //存到session中方便跟前端传入的验证码进行对比
        session.setAttribute("codeText",text);
        //根据验证码的文本生成图片
        BufferedImage image = defaultKaptcha.createImage(text);
        //将二维码图片设置到浏览器端
        try {
            ImageIO.write(image,"jpg",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
