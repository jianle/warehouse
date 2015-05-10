package com.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.finance.dao.BillReceivableDao;
import com.finance.model.BillReceivable;
import com.wms.model.Pagination;

@Controller
@RequestMapping("/billReceivable")
public class BillReceivableController {
    
    private Logger logger = LoggerFactory.getLogger(BillReceivableController.class);
    
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    private BillReceivableDao billReceivableDao;
    
    @RequestMapping(value={"", "search"})
    public ModelAndView search(@RequestParam(value="startDate", defaultValue="") String startDate, 
            @RequestParam(value="endDate", defaultValue="") String endDate,
            @RequestParam(value="customerCompany", defaultValue="") String customerCompany,
            @RequestParam(value="currentPage", defaultValue="1") Integer currentPage,
            @RequestParam(value="numPerPage", defaultValue="20") Integer numPerPage
            ) {
        //搜索控制器
        String curDate = SDF.format(new Date());
        if (startDate == null || startDate.equals("")) {
            startDate = curDate;
        }
        if (endDate == null || endDate.equals("")) {
            endDate = curDate;
        }
        
        ModelAndView modelView = new ModelAndView("/billre/view");
        Pagination<BillReceivable> pagination = billReceivableDao.findPagination(startDate, endDate, customerCompany, currentPage, numPerPage);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("startDate", startDate);
        modelView.addObject("endDate", endDate);
        modelView.addObject("customerCompany", customerCompany);
        logger.info(pagination.toString());
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("billReceivable") BillReceivable billReceivable) {
        
        logger.info(billReceivable.toString());
        
        if (billReceivableDao.save(billReceivable)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("billReceivable") BillReceivable billReceivable) {
        
        logger.info(billReceivable.toString());
        
        if (billReceivableDao.update(billReceivable)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request, 
            @ModelAttribute("brId") Long brId) {
        
        logger.info("brId :" + brId);
        
        if (billReceivableDao.delete(brId)) {
            return "true";
        }
        
        return "false";
    }

}
