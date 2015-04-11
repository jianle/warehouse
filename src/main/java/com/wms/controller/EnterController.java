package com.wms.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.EnterDao;
import com.wms.dao.StorageDao;
import com.wms.model.Enter;
import com.wms.model.Pagination;
import com.wms.model.User;

@Controller
@RequestMapping("/enter")
public class EnterController {
    
    private Logger logger = LoggerFactory.getLogger(EnterController.class);
    
    @Autowired
    private EnterDao enterDao;
    
    @Autowired
    private StorageDao storageDao;
    
    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/enter/view");
        
        logger.info("RequestMapping :/enter/view");
        
        return modelView;
        
    }
    
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="10") int numPerPage
            ) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/enter/list");
        
        logger.info("RequestMapping :/enter/list");
        // 获取分页数据
        Pagination<Enter> paginationEnters = enterDao.findByCurrentPage(currentPage, numPerPage);
        
        modelView.addObject("paginationEnters", paginationEnters);
        modelView.addObject("currentPage", currentPage);
        
        return modelView;
        
    }
    
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(@ModelAttribute Enter enter, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        enter.setInsertDt(new Timestamp(System.currentTimeMillis()));
        enter.setUserId(user.getId());
        enter.setUserName(user.getTruename());
        logger.info(user.toString());
        logger.info(enter.toString());
        
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (enterDao.save(enter)) {
            result = true;
        }
        
        if (storageDao.get(enter.getgId()) != null) {
            if (! storageDao.updateBoxes(enter, "add")) {
                result = false;
            }
        } else {
            if (! storageDao.save(enter)) {
                result=false;
            };
        }
        
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
}
