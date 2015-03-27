package com.wh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wh.dao.SupplierDao;

@Controller
@Component
@RequestMapping("/supplier")
public class SupplierController {
    
    private Logger logger = LoggerFactory.getLogger(SupplierController.class);
    
    @Autowired
    private SupplierDao supplierDao;
    
    @RequestMapping("")
    public ModelAndView list() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/supplier/list");
        logger.info("RequestMapping:/supplier .");
        return modelView;
    }

}
