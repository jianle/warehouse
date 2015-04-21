package com.wms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    
    private Logger logger = LoggerFactory.getLogger(DeliveryController.class);
    
    @RequestMapping("")
    public ModelAndView view() {
        ModelAndView modelView = new ModelAndView("/delivery/view");
        logger.info("RequestMapping:/delivery/vie ");
        
        return modelView;
    }

}
