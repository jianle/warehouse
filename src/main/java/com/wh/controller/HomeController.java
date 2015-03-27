package com.wh.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
    
    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    /*
     * 首页跳转
     */
    
    @RequestMapping("")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        
        logger.info("RequestMapping:/ ");
        
        return modelAndView;
    }

}
