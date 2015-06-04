package com.finance.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;
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
    
    public static Map<Integer, String> invTypesMap = new HashMap<Integer, String>() ;
    static {
        invTypesMap.put(0, "公司");
        invTypesMap.put(2, "其他");
        invTypesMap.put(3, "张峰");
        invTypesMap.put(4, "吕峰");
        invTypesMap.put(5, "陈皓");
    }
    
    @Autowired
    private BillReceivableDao billReceivableDao;
    
    @Autowired
    private InvoiceIncomeDao invoiceIncomeDao;
    
    @Autowired
    private ConsumerDao consumerDao;
    @Autowired
    private ProducerDao producerDao;

    @RequestMapping(value={"","search"})
    public ModelAndView search(
            @RequestParam(value="startDate", defaultValue="") String startDate,
            @RequestParam(value="endDate", defaultValue="") String endDate,
            @RequestParam(value="invType", defaultValue="-1") Integer invType,
            @RequestParam(value="conName", defaultValue="") String conName,
            @RequestParam(value="currentPage", defaultValue="1") Integer currentPage,
            @RequestParam(value="numPerPage", defaultValue="20") Integer numPerPage
            ) {
        ModelAndView modelView = new ModelAndView("/invoiceinc/view");
        logger.info(" currentPage:"+currentPage+" numPerPage:"+numPerPage);
        
        if (startDate == null || "".equals(startDate)) {
            startDate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(new Date(), -30));
        }
        
        if (endDate == null || "".equals(endDate)) {
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        
        String conIds = null;
        if (conName == null || "".equals(conName)) {
            conIds = "";
        } else {
            Map<Long, String> consumerMapFilter = consumerDao.findAllMapIdAndName(conName);
            if (consumerMapFilter.size()>=1) {
                conIds = consumerMapFilter.keySet().toString().replace("[", "(").replace("]", ")");
            }else {
                conIds = "(-1)";
            }
        }
                
        Pagination<InvoiceIncome> pagination = invoiceIncomeDao.findPagination(startDate, endDate, invType, conIds, currentPage, numPerPage);
        Map<Long, String> consumerMap = consumerDao.findAllMapIdAndName(null);
        Map<Long, String> producerMap = producerDao.findAllMapIdAndName(null);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("consumerMap", consumerMap);
        modelView.addObject("producerMap", producerMap);
        modelView.addObject("invTypesMap", invTypesMap);
        modelView.addObject("startDate", startDate);
        modelView.addObject("endDate", endDate);
        modelView.addObject("invType", invType);
        modelView.addObject("conName", conName);

        
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
    
    @RequestMapping(value="get")
    @ResponseBody
    public JSONObject getInvJsonObject(HttpServletRequest request, 
            @ModelAttribute("invId") Long invId) {
        JSONObject jsonTuple = new JSONObject();
        
        logger.info("invId :" + invId);
        
        InvoiceIncome invoice = invoiceIncomeDao.get(invId);
        jsonTuple = JSONObject.fromObject(invoice);
        logger.info(jsonTuple.toString());
        
        return jsonTuple;
    }


}
