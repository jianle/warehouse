package com.wms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.EnterDao;
import com.wms.dao.StorageDao;

@Controller
@RequestMapping("/storage")
public class StorageController {
    
    private Logger logger = LoggerFactory.getLogger(StorageController.class);
    
    @Autowired
    private EnterDao enterDao;
    
    @Autowired
    private StorageDao storageDao;
    
    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/storage/view");
        
        logger.info("RequestMapping :/storage/view");
        
        return modelView;
        
    }

}
