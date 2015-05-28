package com.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.finance.dao.ConsumerDao;
import com.finance.dao.InvoiceDao;
import com.finance.dao.LedgerReceivableDao;
import com.finance.dao.ProducerDao;
import com.finance.model.Consumer;
import com.finance.model.Invoice;
import com.finance.model.LedgerReceivable;
import com.finance.util.Utils;
import com.wms.model.Pagination;

@Controller
@RequestMapping("/ledgerReceivable")
public class LedgerReceivableController {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    private LedgerReceivableDao ledgerReceivableDao;
    @Autowired
    private ConsumerDao consumerDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private ProducerDao producerDao;
    
    @RequestMapping(value={"", "search"})
    public ModelAndView search(@RequestParam(value="startDate", defaultValue="") String startDate, 
            @RequestParam(value="endDate", defaultValue="") String endDate,
            @RequestParam(value="conName", defaultValue="") String conName,
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
        
        String conIds = null;
        Map<Long, String> consumerMap = consumerDao.findAllMapIdAndName(null);
        
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
        
        ModelAndView modelView = new ModelAndView("/ledgerre/view");
        Pagination<LedgerReceivable> pagination = ledgerReceivableDao.findPagination(Utils.getMonthId(startDate), Utils.getMonthId(endDate), conIds, currentPage, numPerPage);
        Map<Long, String> producerMap = producerDao.findAllMapIdAndName(null);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("startDate", startDate);
        modelView.addObject("endDate", endDate);
        modelView.addObject("conName", conName);
        modelView.addObject("consumerMap", consumerMap);
        modelView.addObject("producerMap", producerMap);
        
        logger.info(pagination.getResultList().toString());
        
        return modelView;
    }
    
    @RequestMapping(value="verification")
    public ModelAndView verification(@RequestParam(value="conId", defaultValue="1") Long conId
            ,@RequestParam(value="lrId", defaultValue="1") Long lrId) {
        ModelAndView modelAndView = new ModelAndView("/ledgerre/ver");
        List<Invoice> invoices = invoiceDao.findAllByConId(conId);
        Consumer consumer = consumerDao.get(conId);
        Map<Long, String> producersMap = producerDao.findAllMapIdAndName(null);
        LedgerReceivable ledgerReceivable = ledgerReceivableDao.get(lrId);
        
        modelAndView.addObject("invoices", invoices);
        modelAndView.addObject("consumer", consumer);
        modelAndView.addObject("producersMap", producersMap);
        modelAndView.addObject("ledgerReceivable", ledgerReceivable);
        
        return modelAndView;
    }
    
    @RequestMapping("verification/confirm")
    @ResponseBody
    public JSONObject verificationConfirm(HttpServletRequest request, 
            @ModelAttribute("invId") String invIds) {
        
        //Double amountValid = ledgerReceivable.getAmount() - ledgerReceivable.getVerification();
        String[] invIdstemp = invIds.replaceAll("\"", "").split(",");
        Invoice invoice = null;
        Double verifi = (double) 0;
        Boolean result = false;
        
        for (String invId : invIdstemp) {
            logger.info(invId);
            invoice = invoiceDao.get(Long.valueOf(invId));
            verifi = verifi + invoice.getAmount();
            if (invoiceDao.updateVerification(Long.valueOf(invId), null)) {
                result = true;
            }
        }
        
        if (result) {
            result = ledgerReceivableDao.deleteByInvIdAndMonthId(invoice.getConId()
                    , invoice.getProId(), Utils.getMonthId(invoice.getInvDate()));
        }
        
        if (result) {
            result = ledgerReceivableDao.saveByInvoice(invoice.getConId()
                    , invoice.getProId(), Utils.getMonthId(invoice.getInvDate()));
        }
        
        JSONObject jsonTuple = new JSONObject();
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("verification/confirmone")
    @ResponseBody
    public JSONObject verificationConfirmone(HttpServletRequest request, 
            @ModelAttribute("invId") Long invId,
            @RequestParam(value="verification", defaultValue="0") Double verification) {
        
        Boolean result = false;
        Invoice invoice = invoiceDao.get(invId);
        
        logger.info("invId:" + invId + ", verification:" + verification);
        
        if (invoiceDao.updateVerification(invId, verification)) {
            result = true;
        }
        
        if (result) {
            result = ledgerReceivableDao.deleteByInvIdAndMonthId(invoice.getConId()
                    , invoice.getProId(), Utils.getMonthId(invoice.getInvDate()));
        }
        
        if (result) {
            result = ledgerReceivableDao.saveByInvoice(invoice.getConId()
                    , invoice.getProId(), Utils.getMonthId(invoice.getInvDate()));
        }
        
        JSONObject jsonTuple = new JSONObject();
        jsonTuple.put("value", result);
        return jsonTuple;
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
    public JSONObject delete(HttpServletRequest request, 
            @ModelAttribute("lrId") Long lrId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("lrId :" + lrId);
        
        if (ledgerReceivableDao.delete(lrId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }

}
