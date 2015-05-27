package com.finance.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.finance.dao.ConsumerDao;
import com.finance.dao.LedgerReceivableDao;
import com.finance.dao.ProducerDao;
import com.finance.model.LedgerReceivable;
import com.finance.util.Utils;

@Controller
@RequestMapping("/debtor")
public class DebtorController {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    
    @Autowired
    private LedgerReceivableDao ledgerReceivableDao;
    
    @Autowired
    private ConsumerDao consumerDao;
    
    @Autowired
    private ProducerDao producerDao;
    
    
    @RequestMapping(value={"", "search"})
    public ModelAndView view(@RequestParam(value="", defaultValue="") String startDate
            , @RequestParam(value="endDate", defaultValue="") String endDate
            , @RequestParam(value="conName", defaultValue="") String conName
            ) {
        Calendar cale = Calendar.getInstance();   
        cale.setTime(new Date());
        cale.set(Calendar.DAY_OF_MONTH,1);
        
        String startMonth = null;
        String endMonth = null;
        
        if ("".equals(startDate)) {
            startDate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addMonths(cale.getTime(), -3));
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addMonths(cale.getTime(), 3));
            startMonth = Utils.getMonthId(startDate);
            endMonth = Utils.getMonthId(endDate);
        } else {
            startMonth = Utils.getMonthId(startDate);
            endMonth = Utils.getMonthId(endDate);
        }
        
        logger.info("startMonth:" + startMonth + ", endMonth:" + endMonth);
        
        List<String> monthIdList = generateMonthIdList(startMonth, endMonth);
        
        logger.info(monthIdList.toString());
        
        String conIds = "";
        
        Map<Long, String> mapConIdsMap = consumerDao.findAllMapIdAndName(conName);
        if (mapConIdsMap != null) {
            conIds = mapConIdsMap.keySet().toString().replace("[", "(").replace("]", ")");
        }
        
        List<LedgerReceivable> ledgerReceivables = ledgerReceivableDao.findToDebtor(startMonth, endMonth, conIds);
        
        List<Map<String, Object>> tableList =  generateTableList(ledgerReceivables);
        
        logger.info(tableList.toString());
        
        ModelAndView modelView = new ModelAndView("/debtor/view");
        logger.info("@RequestMapping:/debtor)");
        
        Map<Long, String> producerMap = producerDao.findAllMapIdAndName(null);
        Map<Long, String> consumerMap = consumerDao.findAllMapIdAndName(null);
        
        modelView.addObject("monthIdList", monthIdList);
        modelView.addObject("tableList", tableList);
        modelView.addObject("startDate", startDate);
        modelView.addObject("endDate", endDate);
        modelView.addObject("conName", conName);
        modelView.addObject("producerMap", producerMap);
        modelView.addObject("consumerMap", consumerMap);
        
        return modelView;
    }
    
    
    private List<Map<String, Object>> generateTableList(List<LedgerReceivable> ledgerReceivables) {
        List<Map<String, Object>> resuList = new ArrayList<Map<String,Object>>();
        Map<String, Object> monthValueMap = new HashMap<String, Object>();
        
        String flagMap = null;
        
        if (ledgerReceivables == null || ledgerReceivables.size()<=0) {
            return null;
        } else {
            flagMap = ledgerReceivables.get(0).getConcat();
        }
        
        for (LedgerReceivable ledgerReceivable : ledgerReceivables) {
            String tmpFlag = ledgerReceivable.getConcat();
            if (!tmpFlag.equals(flagMap)) {
                resuList.add(monthValueMap);
                monthValueMap = new HashMap<String, Object>();
            }
            
            monthValueMap.put(ledgerReceivable.getMonthId(), ledgerReceivable.getAmount());
            monthValueMap.put("conId", ledgerReceivable.getConId());
            monthValueMap.put("proId", ledgerReceivable.getProId());
        }
        
        resuList.add(monthValueMap);
        
        return resuList;
    }
    
    /*
     *参数 Month  必须为：日期（2010-01-01）或者是年份月份（2010年1）
     */
    private List<String> generateMonthIdList(String startMonthId, String endMonthId) {
        List<String> resultList = new ArrayList<String>();
        
        if (startMonthId.compareTo(endMonthId) >=0) {
            resultList.add(Utils.getMonthId(new Date()));
            return resultList;
        }
        
        int startYear  = Integer.valueOf(startMonthId.split("M")[0]);
        int startMon = Integer.valueOf(startMonthId.split("M")[1]);
        
        int endYear  = Integer.valueOf(endMonthId.split("M")[0]);
        int endMon = Integer.valueOf(endMonthId.split("M")[1]);
        
        if (startYear != endYear) {
            for (int i = startMon; i <= 12; i++) {
                resultList.add(String.format("%dM%02d", startYear, i));
            }
            
            for (int i = 1; i <= endMon; i++) {
                resultList.add(String.format("%dM%02d", endYear, i));
            }
        } else {
            for (int i = startMon; i <= endMon; i++) {
                resultList.add(String.format("%dM%02d", startYear, i));
            }
        }
        
        return resultList;
        
    }
    
}
