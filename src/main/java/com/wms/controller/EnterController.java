package com.wms.controller;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.EnterDao;
import com.wms.dao.GoodsDao;
import com.wms.dao.StorageDao;
import com.wms.dao.SupplierDao;
import com.wms.model.Enter;
import com.wms.model.Goods;
import com.wms.model.Pagination;
import com.wms.model.User;

@Controller
@RequestMapping("/enter")
public class EnterController {
    
    private Logger logger = LoggerFactory.getLogger(EnterController.class);
    
    @Autowired
    private EnterDao enterDao;
    
    @Autowired
    private StorageDao storageDao;
    
    @Autowired
    private SupplierDao supplierDao;
    
    @Autowired
    private GoodsDao goodsDao;
    
    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/enter/view");
        
        logger.info("RequestMapping :/enter/view");
        
        return modelView;
        
    }
    
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="10") int numPerPage
            ) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/enter/list");
        
        logger.info("RequestMapping :/enter/list");
        // 获取分页数据
        Pagination<Enter> paginationEnters = enterDao.findByCurrentPage(currentPage, numPerPage);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(paginationEnters.getResultList());
        
        modelView.addObject("supplierMap", supplierMap);
        modelView.addObject("paginationEnters", paginationEnters);
        modelView.addObject("currentPage", currentPage);
        
        return modelView;
        
    }
    
    @RequestMapping("update")
    @ResponseBody
    public JSONObject update(@ModelAttribute Enter enter) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (enterDao.update(enter)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("delete")
    @ResponseBody
    public JSONObject delete(@ModelAttribute("eId") Long eId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("delete enter by Id:" + eId);
        if (enterDao.delete(eId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(@ModelAttribute Enter enter, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        enter.setInsertDt(new Timestamp(System.currentTimeMillis()));
        enter.setUserId(user.getId());
        enter.setUserName(user.getTruename());
        logger.info(user.toString());
        logger.info(enter.toString());
        
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (enterDao.save(enter)) {
            result = true;
        }else {
            jsonTuple.put("value", false);
            return jsonTuple;
        }
        
        Goods goods = goodsDao.get(enter.getgId());
        
        int amount = enter.getChests() * goods.getBoxes() * goods.getAmount()
                + enter.getBoxes() * goods.getAmount()
                + enter.getAmount();
        enter.setAmount(amount);
        
        if (storageDao.get(enter.getgId()) != null) {
            if (! storageDao.updateBoxes(enter, "add")) {
                result = false;
                jsonTuple.put("value", result);
                return jsonTuple;
            }
        } else {
            if (! storageDao.save(enter)) {
                result=false;
            };
        }
        
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    private Map<Long, String> getSupplierMap(List<Enter> Enter) {
        try {
            Set<Long> sIds = new HashSet<Long>();
            if (Enter!=null) {
                for (int i = 0; i < Enter.size(); i++) {
                    sIds.add(Enter.get(i).getsId());
                }
            }
            return supplierDao.findBySIdList(sIds);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("getSupplierMap failed." + e);
            return null;
        }
    }
    
}
