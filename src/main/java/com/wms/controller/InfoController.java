package com.wms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/info")
public class InfoController {
    
    private Logger logger = LoggerFactory.getLogger(InfoController.class);
    
    @RequestMapping(value={"", "denied"})
    public ModelAndView denied() {
        ModelAndView modelView = new ModelAndView("/info/denied");
        logger.info("user has no denied.");
        return modelView;
        
    }
    
}
