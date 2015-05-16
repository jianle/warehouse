package com.finance.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.finance.model.BillReceivable;
import com.wms.model.Pagination;

@Controller
@RequestMapping("/billReceivable")
public class BillReceivableController {
    
    private Logger logger = LoggerFactory.getLogger(BillReceivableController.class);
    
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    private BillReceivableDao billReceivableDao;
    @Autowired
    private ConsumerDao consumerDao;
    
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
        
        logger.info(conIds);
        
        ModelAndView modelView = new ModelAndView("/billre/view");
        Pagination<BillReceivable> pagination = billReceivableDao.findPagination(startDate, endDate, conIds, currentPage, numPerPage);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("startDate", startDate);
        modelView.addObject("endDate", endDate);
        modelView.addObject("conName", conName);

        modelView.addObject("consumerMap", consumerMap);
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("billReceivable") BillReceivable billReceivable) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info(billReceivable.toString());
        
        if (billReceivableDao.save(billReceivable)) {
            return "true";
        }
        jsonTuple.put("value", result);
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("billReceivable") BillReceivable billReceivable) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        logger.info(billReceivable.toString());
        
        if (billReceivableDao.update(billReceivable)) {
            return "true";
        }
        jsonTuple.put("value", result);
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, 
            @ModelAttribute("brId") Long brId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        logger.info("brId :" + brId);
        
        if (billReceivableDao.delete(brId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }

}
