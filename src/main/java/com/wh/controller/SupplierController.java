package com.wh.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wh.dao.SupplierDao;
import com.wh.form.model.SupplierSearchForm;
import com.wh.model.Pagination;
import com.wh.model.Supplier;

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
        SupplierSearchForm supplierSearchForm = new SupplierSearchForm(1, "A", "shortname", "");
        Pagination<Supplier> pagination = supplierDao.findByColumnValue(supplierSearchForm);
        
        logger.info("RequestMapping:/supplier .");
        modelView.addObject("pagination", pagination);
        modelView.addObject("supplierSearchForm", supplierSearchForm);
        
        return modelView;
    }
    
    @RequestMapping(value="search", method=RequestMethod.POST)
    public ModelAndView search(@ModelAttribute("supplierSearchForm") SupplierSearchForm supplierSearchForm) {
        //搜索控制器
        
        ModelAndView modelView = new ModelAndView();
        logger.info(supplierSearchForm.toString());
        Pagination<Supplier> pagination = supplierDao.findByColumnValue(supplierSearchForm);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("supplierSearchForm", supplierSearchForm);
        modelView.setViewName("/supplier/list");
        
        logger.info(pagination.toString());
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("supplier") Supplier supplier) {
        
        supplier.setInsertDt(new Timestamp(System.currentTimeMillis()));
        supplier.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        
        logger.info(supplier.toString());
        
        if (supplierDao.save(supplier)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("supplier") Supplier supplier) {
        
        logger.info(supplier.toString());
        
        if (supplierDao.update(supplier)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request, 
            @ModelAttribute("sId") Long sId) {
        
        logger.info("sId :" + sId);
        
        if (supplierDao.delete(sId)) {
            return "true";
        }
        
        return "false";
    }

}
