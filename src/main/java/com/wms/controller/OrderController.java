package com.wms.controller;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.util.UserDenied;
import com.wms.dao.GoodsDao;
import com.wms.dao.OrderDetailDao;
import com.wms.dao.OrderinfoDao;
import com.wms.dao.StorageDao;
import com.wms.dao.SupplierDao;
import com.wms.dao.UserDao;
import com.wms.model.OrderDetail;
import com.wms.model.Orderinfo;
import com.wms.model.Pagination;
import com.wms.model.Supplier;
import com.wms.model.User;


@Controller
@RequestMapping("/order")
@SessionAttributes({"user", "userIds"})
public class OrderController {
    
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private OrderDetailDao orderDetailDao;
    
    @Autowired
    private OrderinfoDao orderinfoDao;
    
    @Autowired
    private StorageDao storageDao;
    
    @Autowired
    private UserDao userDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private GoodsDao goodDao;
    
    @RequestMapping(value={"", "list"})
    public ModelAndView list(
            @ModelAttribute User user,
            @RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="15") int numPerPage
            ) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/view");
        
        logger.info("RequestMapping :/order/view");
        
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        String userIds = UserDenied.getUserIds(users, user.getRole());
        
        // 获取分页数据
        Pagination<Orderinfo> paginations = orderinfoDao.findByCurrentPage(currentPage, numPerPage, userIds);
        
        modelView.addObject("paginations", paginations);
        modelView.addObject("ordersJson", JSONArray.fromObject(paginations.getResultList()));
        Map<Long, String> suppliers = supplierDao.findIdMapName(userIds);
        logger.info(suppliers.toString());
        modelView.addObject("suppliers", suppliers);
        
        modelView.addObject("users", users);
        
        return modelView;
        
    }
    
    @RequestMapping("search")
    public ModelAndView search(
            @ModelAttribute User user,
            @RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="15") int numPerPage
            ) {
        
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/view");
        
        logger.info("RequestMapping :/order/search");
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        String userIds = UserDenied.getUserIds(users, user.getRole());
        // 获取分页数据
        Pagination<Orderinfo> paginations = orderinfoDao.findByCurrentPage(currentPage, numPerPage, userIds);
        
        modelView.addObject("paginations", paginations);
        modelView.addObject("ordersJson", JSONArray.fromObject(paginations.getResultList()));
        Map<Long, String> suppliers = supplierDao.findIdMapName(userIds);
        logger.info(suppliers.toString());
        modelView.addObject("suppliers", suppliers);
        
        modelView.addObject("users", users);
        
        return modelView;
        
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
        jsonTuple.put("result", result);
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
    public ModelAndView detailView(
            @ModelAttribute User user,
            @RequestParam(value="oId", defaultValue="1") Long oId,
            @RequestParam(value="sId", defaultValue="0") Long sId) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/detail");
        logger.info("RequestMapping :/order/detail");
        modelView.addObject("curoId", oId);
        
        Orderinfo orderInfo = orderinfoDao.get(oId);
        modelView.addObject("orderInfo", orderInfo);
        logger.info("sId:" + orderInfo.getCustomerCode());
        Supplier supplier = supplierDao.get(orderInfo.getCustomerCode());
        modelView.addObject("cursId", supplier.getsId());
        modelView.addObject("supplier", supplier);
        
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        modelView.addObject("users", users);
        
        return modelView;
    }
    
    @RequestMapping("detail/getAlloId")
    @ResponseBody
    public JSONObject getAlloId(@ModelAttribute User user){
        
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        String userIds = UserDenied.getUserIds(users, user.getRole());
        
        JSONArray result = new JSONArray();
        result = JSONArray.fromObject(orderinfoDao.findAlloId(userIds));
        logger.info(result.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", result);
        return jsonObject;
    }
    
    @RequestMapping("detail/getCurAlloId")
    @ResponseBody
    public JSONArray getCurAlloId(@ModelAttribute String userIds){
        
        JSONArray result = new JSONArray();
        result = JSONArray.fromObject(orderinfoDao.findCurAlloId(userIds));
        logger.info(result.toString());
        return result;
    }
    
    @RequestMapping("detail/get")
    @ResponseBody
    public JSONArray getDetail(@RequestParam(value="o_id", defaultValue="0") Long oId){
        
        JSONArray result = new JSONArray();
        result = JSONArray.fromObject(orderDetailDao.findByOId(oId));
        logger.info("RequestMapping:order/detail/get?oId=" + oId);
        return result;
    }
    
    @RequestMapping("getOrderInfo")
    @ResponseBody
    public JSONObject getOidDetail(@RequestParam(value="o_id", defaultValue="0") Long oId){
        
        JSONObject result = new JSONObject();
        result = JSONObject.fromObject(orderinfoDao.get(oId));
        logger.info("RequestMapping:order/getOrderInfo?oId=" + oId);
        return result;
    }
    
    @RequestMapping("detail/save")
    @ResponseBody
    public JSONObject saveDetail(@ModelAttribute OrderDetail orderDetail) {
        orderDetail.setInsertDt(String.valueOf(new Timestamp(System.currentTimeMillis())));
        orderDetail.setUpdateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        String msg = "";
        logger.info(orderDetail.toString());
        
        //判断库存是否够了
        if (orderDetail.getAmount()>storageDao.findByGId(orderDetail.getgId())) {
            msg = "操作失败，库存不够，请仔细确认";
            jsonTuple.put("value", result);
            jsonTuple.put("msg", msg);
            return jsonTuple;
        }
        
        if (orderDetail.getOdId() != null) {
            logger.info("记录存在，更新");
            if (orderDetailDao.update(orderDetail)) {
                result = true;
                msg = "修改成功";
            }else {
                msg = "修改失败";
            }
        }else {
            logger.info("记录不存在，添加");
            if (orderDetailDao.save(orderDetail)) {
                result = true;
                msg = "添加成功";
            }else {
                msg = "保存失败";
            }
        }
        jsonTuple.put("msg", msg);
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("detail/update")
    @ResponseBody
    public JSONObject updateDetail(@ModelAttribute OrderDetail orderDetail) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        //判断库存是否够了
        if (orderDetail.getAmount()>storageDao.findByGId(orderDetail.getgId())) {
            jsonTuple.put("value", result);
            return jsonTuple;
        }
        
        if (orderDetailDao.update(orderDetail)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    
    @RequestMapping("detail/delete")
    @ResponseBody
    public String deleteDetail(@ModelAttribute("odId") Long odId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("delete by Id:" + odId);
        if (orderDetailDao.delete(odId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return String.valueOf(result);
    }
    
    //验货功能块
    
    @RequestMapping(value = "detailCheck", method = RequestMethod.GET)
    public ModelAndView detailCheck(
            @ModelAttribute User user,
            @ModelAttribute String userIds,
            @RequestParam(value="oId", defaultValue="1") Long oId,
            @RequestParam(value="sId", defaultValue="0") Long sId) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/order/detailCheck");
        logger.info("RequestMapping :/order/detailCheck");
        
        Orderinfo orderInfo = orderinfoDao.get(oId);
        
        if (orderInfo == null) {
            oId = orderinfoDao.getMinId(userIds);
        }
        orderInfo = orderinfoDao.get(oId);
        
        modelView.addObject("curoId", oId);
        modelView.addObject("orderInfo", orderInfo);
        
        List<Map<String, Object>> orderDetails = orderDetailDao.findByOId(oId);
        modelView.addObject("orderDetails", orderDetails);
        modelView.addObject("cursId", sId);
        if (orderInfo == null) {
            return modelView;
        }
        if (sId == 0) {
            sId = orderInfo.getCustomerCode();
        }
        logger.info("orderInfo:" + orderInfo.toString());
        Supplier supplier = supplierDao.get(orderInfo.getCustomerCode());
        modelView.addObject("supplier", supplier);
        
        Map<Long, String> goodCodeMap = goodDao.findAllIdAndCode(orderInfo.getCustomerCode(), userIds);
        logger.info(goodCodeMap.toString());
        
        modelView.addObject("goodCodeMap", goodCodeMap);
        
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        modelView.addObject("users", users);
        
        return modelView;
    }
    
    @RequestMapping("updateStatus")
    @ResponseBody
    public JSONObject updateStatus(
            @RequestParam(value="status") Integer status,
            @RequestParam(value="oId") Long oId) {
        logger.info("update oId:" + oId + " status to:" + status);
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        try {
            if (orderinfoDao.update(status, oId)) {//Integer.valueOf(status), Long.valueOf(oId)
                result = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        jsonTuple.put("value", result);
        return jsonTuple;
    }

}
