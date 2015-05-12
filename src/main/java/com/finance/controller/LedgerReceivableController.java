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

import com.finance.dao.LedgerReceivableDao;
import com.finance.model.LedgerReceivable;
import com.wms.model.Pagination;

@Controller
@RequestMapping("/ledgerReceivable")
public class LedgerReceivableController {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    private LedgerReceivableDao ledgerReceivableDao;
    
    @RequestMapping(value={"", "search"})
    public ModelAndView search(@RequestParam(value="startDate", defaultValue="") String startDate, 
            @RequestParam(value="endDate", defaultValue="") String endDate,
            @RequestParam(value="payCompany", defaultValue="") String payCompany,
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
        
        ModelAndView modelView = new ModelAndView("/ledgerre/view");
        Pagination<LedgerReceivable> pagination = ledgerReceivableDao.findPagination(startDate, endDate, payCompany, currentPage, numPerPage);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("startDate", startDate);
        modelView.addObject("endDate", endDate);
        modelView.addObject("payCompany", payCompany);
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("ledgerReceivable") LedgerReceivable ledgerReceivable) {
        
        logger.info(ledgerReceivable.toString());
        
        if (ledgerReceivableDao.save(ledgerReceivable)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("ledgerReceivable") LedgerReceivable ledgerReceivable) {
        
        logger.info(ledgerReceivable.toString());
        
        if (ledgerReceivableDao.update(ledgerReceivable)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request, 
            @ModelAttribute("lrId") Long lrId) {
        
        logger.info("lrId :" + lrId);
        
        if (ledgerReceivableDao.delete(lrId)) {
            return "true";
        }
        
        return "false";
    }


}
