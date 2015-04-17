package com.wms.controller;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.GoodsDao;
import com.wms.dao.OrderDetailDao;
import com.wms.dao.StorageDao;
import com.wms.dao.SupplierDao;


@Controller
@RequestMapping("/order")
public class OrderController {
    
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private OrderDetailDao orderDetailDao;
    
    @Autowired
    private StorageDao storageDao;
    
    @Autowired
    private SupplierDao supplierDao;
    
    @Autowired
    private GoodsDao goodsDao;
    
    @RequestMapping("")
    public ModelAndView view() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/view");
        
        logger.info("RequestMapping :/enter/view");
        
        return modelView;
        
    }
    
    @RequestMapping("detail")
    public ModelAndView detail() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/detail");
        
        logger.info("RequestMapping :/order/detail");
        
        return modelView;
        
    }
    
    @RequestMapping("detail/get")
    @ResponseBody
    public JSONArray getDetail(@RequestParam(value="o_id", defaultValue="0") Long oId){
        
        JSONArray result = new JSONArray();
        result = JSONArray.fromObject(orderDetailDao.findByOId(oId));
        logger.info(result.toString());
        return result;
    }
    
    

}
