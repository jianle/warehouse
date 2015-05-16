package com.finance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.finance.dao.ConsumerDao;
import com.finance.dao.InvoiceIncomeDao;
import com.finance.dao.ProducerDao;
import com.finance.model.InvoiceIncome;
import com.wms.model.Pagination;

@Controller
@RequestMapping("/invoiceIncome")
public class InvoiceIncomeController {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceIncomeController.class);
    
    @Autowired
    private BillReceivableDao billReceivableDao;
    
    @Autowired
    private InvoiceIncomeDao invoiceIncomeDao;
    
    @Autowired
    private ConsumerDao consumerDao;
    @Autowired
    private ProducerDao producerDao;

    @RequestMapping(value={"","search"})
    public ModelAndView search(@RequestParam(value="lrId", defaultValue="1") Long lrId,
            @RequestParam(value="currentPage", defaultValue="1") Integer currentPage,
            @RequestParam(value="numPerPage", defaultValue="20") Integer numPerPage
            ) {
        ModelAndView modelView = new ModelAndView("/invoiceinc/view");
        logger.info("brId:" + lrId + " currentPage:"+currentPage+" numPerPage:"+numPerPage);
        
        System.out.println("brId:" + lrId + " currentPage:"+currentPage+" numPerPage:"+numPerPage);
        
        Pagination<InvoiceIncome> pagination = invoiceIncomeDao.findPagination(lrId, currentPage, numPerPage);
        Map<Long, String> consumerMap = consumerDao.findAllMapIdAndName(null);
        Map<Long, String> producerMap = producerDao.findAllMapIdAndName(null);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("consumerMap", consumerMap);
        modelView.addObject("producerMap", producerMap);
        modelView.addObject("lrId", lrId);
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("invoiceIncome") InvoiceIncome invoiceIncome) {
        
        logger.info("init:"+invoiceIncome.toString());
        
        List<InvoiceIncome> invoiceIncomes = new ArrayList<InvoiceIncome>();
        invoiceIncomes.add(invoiceIncome);
        Long top = invoiceIncome.getInvId();
        if (invoiceIncomeDao.get(top) != null) {
			return "false";
		}
        for (int i = 1; i < invoiceIncome.getNumber(); i++) {
            try {
            	InvoiceIncome tmp = (InvoiceIncome) invoiceIncome.clone();
                tmp.setInvId(top + i);
                invoiceIncomes.add(tmp);
                if (invoiceIncomeDao.get(tmp.getInvId()) != null) {
					return "false";
				}
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        }
        
        logger.info("list:"+invoiceIncomes.toString());
        
        if (invoiceIncomeDao.saveBatch(invoiceIncomes)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("invoiceIncome") InvoiceIncome invoiceIncome) {
        
        logger.info(invoiceIncome.toString());
        
        if (invoiceIncomeDao.update(invoiceIncome)) {
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
        
        if (invoiceIncomeDao.delete(invId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        
        return jsonTuple;
    }

}
