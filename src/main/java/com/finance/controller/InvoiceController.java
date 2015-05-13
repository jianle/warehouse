package com.finance.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
import com.finance.dao.InvoiceDao;
import com.finance.model.Invoice;
import com.wms.model.Pagination;


@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    
    @Autowired
    private BillReceivableDao billReceivableDao;
    
    @Autowired
    private InvoiceDao invoiceDao;
    
    @RequestMapping(value={"","search"})
    public ModelAndView search(@RequestParam(value="brId") Long brId,
            @RequestParam(value="currentPage", defaultValue="1") Integer currentPage,
            @RequestParam(value="numPerPage", defaultValue="20") Integer numPerPage
            ) {
        ModelAndView modelView = new ModelAndView("/invoice/view");
        logger.info("brId:" + brId + " currentPage:"+currentPage+" numPerPage:"+numPerPage);
        
        System.out.println("brId:" + brId + " currentPage:"+currentPage+" numPerPage:"+numPerPage);
        
        Pagination<Invoice> pagination = invoiceDao.findPagination(brId, currentPage, numPerPage);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("brId", brId);
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("invoice") Invoice invoice) {
        
        logger.info("init:"+invoice.toString());
        
        List<Invoice> invoices = new ArrayList<Invoice>();
        invoices.add(invoice);
        Long top = invoice.getInvId();
        for (int i = 1; i < invoice.getNumber(); i++) {
            try {
                Invoice tmp = (Invoice) invoice.clone();
                tmp.setInvId(top + i);
                invoices.add(tmp);
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        }
        
        logger.info("list:"+invoices.toString());
        
        if (invoiceDao.saveBatch(invoices)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("invoice") Invoice invoice) {
        
        logger.info(invoice.toString());
        
        if (invoiceDao.update(invoice)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, 
            @ModelAttribute("invId") Long invId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("invId :" + invId);
        
        if (invoiceDao.delete(invId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        
        return jsonTuple;
    }


}
