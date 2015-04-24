package com.wms.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
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

import com.wms.dao.DeliveryDao;
import com.wms.dao.DeliveryDetailDao;
import com.wms.dao.OrderinfoDao;
import com.wms.model.Delivery;
import com.wms.model.DeliveryDetail;
import com.wms.task.GrabTask;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    
    private Logger logger = LoggerFactory.getLogger(DeliveryController.class);
    
    @Autowired
    private DeliveryDao deliveryDao;
    
    @Autowired
    private DeliveryDetailDao deliveryDetailDao;
    
    @Autowired
    private OrderinfoDao orderinfoDao;
    
    @RequestMapping("")
    public ModelAndView view(@RequestParam(value="oId",defaultValue="0") Long oId) {
        ModelAndView modelView = new ModelAndView("/delivery/view");
        logger.info("RequestMapping:/delivery/view");
        
        if (oId == 0) {
            oId = orderinfoDao.getMiniId();
        }
        
        modelView.addObject("oId", oId);
        return modelView;
    }
    
    @RequestMapping("getByoId")
    @ResponseBody
    public JSONArray getDeliverysByoId(@RequestParam(value="oId",defaultValue="0") Long oId) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = JSONArray.fromObject(deliveryDao.findByoId(oId));
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("faield ." + e);
        }
        
        logger.info("get delivery by oId:" + oId);
        return jsonArray;
    }
    
    @RequestMapping("save")
    @ResponseBody
    public String save(@ModelAttribute Delivery delivery, HttpServletRequest request) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        delivery.setUpdateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        delivery.setInsertDt(String.valueOf(new Timestamp(System.currentTimeMillis())));
        delivery.setStatus(0);
        
        logger.info(delivery.toString());
        
        if (deliveryDao.save(delivery)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return String.valueOf(result);
    }
    
    @RequestMapping("update")
    @ResponseBody
    public String update(@ModelAttribute Delivery delivery) {
        logger.info(delivery.toString());
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (deliveryDao.update(delivery)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return String.valueOf(result);
    }
    
    
    @RequestMapping("delete")
    @ResponseBody
    public String delete(@ModelAttribute("dId") Long dId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("delete by Id:" + dId);
        if (deliveryDao.delete(dId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return String.valueOf(result);
    }
    
    @RequestMapping(value="detail", method=RequestMethod.GET)
    public ModelAndView detailView(@RequestParam(value="content",defaultValue="") String content) {
        ModelAndView modelView = new ModelAndView("/delivery/detail");
        logger.info("RequestMapping:/delivery/detail");
        List<DeliveryDetail> details = deliveryDetailDao.findByContent(content);
        
        modelView.addObject("details", details);
        modelView.addObject("content", content);
        return modelView;
    }
    
    @RequestMapping(value="detail", method=RequestMethod.POST)
    public ModelAndView detailViewNow(@RequestParam(value="content",defaultValue="") String content) {
        ModelAndView modelView = new ModelAndView("/delivery/detail");
        logger.info("RequestMapping:/delivery/detail POST");
        
        GrabTask grabTask = new GrabTask(deliveryDetailDao, content);
        
        grabTask.execute(content);
        
        try {
            Thread.sleep(1L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        List<DeliveryDetail> details = deliveryDetailDao.findByContent(content);
        
        modelView.addObject("details", details);
        modelView.addObject("content", content);
        return modelView;
    }

}
