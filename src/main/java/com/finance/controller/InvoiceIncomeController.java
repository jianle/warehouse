package com.finance.controller;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.finance.dao.BillReceivableDao;
import com.finance.dao.InvoiceDao;

public class InvoiceIncomeController {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    private BillReceivableDao billReceivableDao;
    
    @Autowired
    private InvoiceDao invoiceDao;
    
    @RequestMapping("")
    public ModelAndView view() {
        ModelAndView modelView = new ModelAndView("/invoice/view");
        logger.info("@RequestMapping:/invoice)");
        return modelView;
    }

}
