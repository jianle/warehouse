package com.wms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.GoodsDao;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);
    
    @Autowired
    private GoodsDao goodsDao;
    
    @RequestMapping("")
    public ModelAndView list() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/goods/list");
        logger.info("RequestMapping:/goods .");
        return modelView;
    }
    
    

}
