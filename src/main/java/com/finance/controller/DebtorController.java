package com.finance.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.finance.dao.DebtorDao;

@Controller
@RequestMapping("/debtor")
public class DebtorController {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    
    @Autowired
    private DebtorDao debtorDao;
    
    
    @RequestMapping("")
    public ModelAndView view() {
        ModelAndView modelView = new ModelAndView("/debtor/view");
        logger.info("@RequestMapping:/debtor)");
        return modelView;
    }

}
