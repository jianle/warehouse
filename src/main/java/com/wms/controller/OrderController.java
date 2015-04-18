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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.OrderDetailDao;
import com.wms.dao.OrderinfoDao;
import com.wms.model.OrderDetail;
import com.wms.model.Orderinfo;
import com.wms.model.Pagination;
import com.wms.model.User;


@Controller
@RequestMapping("/order")
public class OrderController {
    
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private OrderDetailDao orderDetailDao;
    
    @Autowired
    private OrderinfoDao orderinfoDao;
    
    @RequestMapping(value={"", "list"})
    public ModelAndView list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="15") int numPerPage
            ) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/view");
        
        logger.info("RequestMapping :/order/view");
        // 获取分页数据
        Pagination<Orderinfo> paginations = orderinfoDao.findByCurrentPage(currentPage, numPerPage);
        
        modelView.addObject("paginations", paginations);
        modelView.addObject("ordersJson", JSONArray.fromObject(paginations.getResultList()));
        
        return modelView;
        
    }
    
    @RequestMapping("search")
    @ResponseBody
    public JSONArray search(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="15") int numPerPage
            ) {
        
        logger.info("RequestMapping :/order/search");
        // 获取分页数据
        Pagination<Orderinfo> paginations = orderinfoDao.findByCurrentPage(currentPage, numPerPage);
        List<Orderinfo> orderinfos = paginations.getResultList();
        
        JSONArray result = new JSONArray();
        result = JSONArray.fromObject(orderinfos);
        return result;
        
    }
    
    @RequestMapping("save")
    @ResponseBody
    public String save(@ModelAttribute Orderinfo orderinfo, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        orderinfo.setUpdateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        orderinfo.setInsertDt(String.valueOf(new Timestamp(System.currentTimeMillis())));
        orderinfo.setUserId(user.getId());
        orderinfo.setStatus(0);
        
        logger.info(orderinfo.toString());
        
        if (orderinfoDao.save(orderinfo)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return String.valueOf(result);
    }
    
    @RequestMapping("update")
    @ResponseBody
    public String update(@ModelAttribute Orderinfo orderinfo) {
        logger.info(orderinfo.toString());
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (orderinfoDao.update(orderinfo)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return String.valueOf(result);
    }
    
    
    @RequestMapping("delete")
    @ResponseBody
    public String delete(@ModelAttribute("oId") Long oId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("delete by Id:" + oId);
        if (orderinfoDao.delete(oId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return String.valueOf(result);
    }
    
    
    @RequestMapping(value = "detail/{oId}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value="oId") Long oId) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/detail");
        logger.info("RequestMapping :/order/detail");
        
        JSONArray orderDetails = JSONArray.fromObject(orderDetailDao.findByOId(oId));
        modelView.addObject("orderDetails", orderDetails);
        modelView.addObject("curoId", oId);
        
        return modelView;
    }
    
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ModelAndView detailView(@RequestParam(value="oId", defaultValue="1") Long oId) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/detail");
        logger.info("RequestMapping :/order/detail");
        
        JSONArray orderDetails = JSONArray.fromObject(orderDetailDao.findByOId(oId));
        modelView.addObject("orderDetails", orderDetails);
        modelView.addObject("curoId", oId);
        
        return modelView;
    }
    
    @RequestMapping("detail/getAlloId")
    @ResponseBody
    public JSONObject getAlloId(){
        
        JSONArray result = new JSONArray();
        result = JSONArray.fromObject(orderinfoDao.findAlloId());
        logger.info(result.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", result);
        return jsonObject;
    }
    
    @RequestMapping("detail/get")
    @ResponseBody
    public JSONArray getDetail(@RequestParam(value="o_id", defaultValue="0") Long oId){
        
        JSONArray result = new JSONArray();
        result = JSONArray.fromObject(orderDetailDao.findByOId(oId));
        logger.info(result.toString());
        return result;
    }
    
    @RequestMapping("detail/save")
    @ResponseBody
    public JSONObject saveDetail(@ModelAttribute OrderDetail orderDetail) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (orderDetailDao.save(orderDetail)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("detail/update")
    @ResponseBody
    public JSONObject updateDetail(@ModelAttribute OrderDetail orderDetail) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (orderDetailDao.update(orderDetail)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    
    @RequestMapping("detail/delete")
    @ResponseBody
    public JSONObject deleteDetail(@ModelAttribute("odId") Long odId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("delete by Id:" + odId);
        if (orderDetailDao.delete(odId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }

}
