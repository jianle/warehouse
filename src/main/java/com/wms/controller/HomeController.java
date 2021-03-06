package com.wms.controller;


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
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        
        logger.info("RequestMapping: / ");
        
        return modelAndView;
    }
    
    @RequestMapping("help")
    public ModelAndView help(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/help");
        
        logger.info("RequestMapping: /help ");
        
        return modelAndView;
    }

}
