package com.finance.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @RequestMapping(value={"{brId}", ""})
    public ModelAndView search(@PathVariable(value="brId") Long brId,
            @RequestParam(value="currentPage", defaultValue="1") Integer currentPage,
            @RequestParam(value="numPerPage", defaultValue="20") Integer numPerPage
            ) {
        ModelAndView modelView = new ModelAndView("/invoice/view");
        logger.info("brId:" + brId);
        
        Pagination<Invoice> pagination = invoiceDao.findPagination(brId, currentPage, numPerPage);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("brId", brId);
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("invoice") Invoice invoice) {
        
        logger.info(invoice.toString());
        
        List<Invoice> invoices = new ArrayList<Invoice>();
        invoices.add(invoice);
        Invoice tmpInvoice = null;
        for (int i = 0; i < invoice.getNumber(); i++) {
            tmpInvoice = invoice;
            tmpInvoice.setInvId(invoice.getInvId() + i + 1);
            invoices.add(tmpInvoice);
        }
        
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
    public String delete(HttpServletRequest request, 
            @ModelAttribute("brId") Long brId) {
        
        logger.info("brId :" + brId);
        
        if (invoiceDao.delete(brId)) {
            return "true";
        }
        
        return "false";
    }


}
