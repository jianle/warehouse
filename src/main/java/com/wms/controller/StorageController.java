package com.wms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.EnterDao;
import com.wms.dao.StorageDao;
import com.wms.model.Pagination;
import com.wms.model.Storage;

@Controller
@RequestMapping("/storage")
public class StorageController {
    
    private Logger logger = LoggerFactory.getLogger(StorageController.class);
    
    @Autowired
    private EnterDao enterDao;
    
    @Autowired
    private StorageDao storageDao;
    
    @RequestMapping(value={"","list"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/storage/list");
        String gName = "";
        int currentPage = 1;
        int numPerPage = 10;
        
        // 获取分页数据
        Pagination<Storage> paginations = storageDao.findByCurrentPage(gName, currentPage, numPerPage);
        
        modelView.addObject("pagination", paginations);
        modelView.addObject("gName", gName);
        
        
        logger.info("RequestMapping :/storage/list");
        
        return modelView;
    }
    
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value="gName", defaultValue="") String gName,
            @RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="10") int numPerPage
            ) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/storage/list");
        
        logger.info("RequestMapping :/storage/list");
        // 获取分页数据
        Pagination<Storage> pagination = storageDao.findByCurrentPage(gName, currentPage, numPerPage);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("gName", gName);
        
        return modelView;
        
    }

}
