package com.wms.controller;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
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

import com.wms.dao.GoodsDao;
import com.wms.dao.SupplierDao;
import com.wms.model.Goods;
import com.wms.model.Pagination;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);
    
    @Autowired
    private GoodsDao goodsDao;
    
    @Autowired
    private SupplierDao supplierDao;
    
    @RequestMapping("")
    public ModelAndView list() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/goods/list");
        String name="";
        String isDisabled = "A";
        int currentPage = 1;
        // 获取分页数据
        Pagination<Goods> paginationGoods = goodsDao.findByNameAndIsDisabled(name, isDisabled, currentPage);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(paginationGoods.getResultList());   

        modelView.addObject("paginationGoods", paginationGoods);
        modelView.addObject("sName", name);
        modelView.addObject("isDisabled", isDisabled);
        modelView.addObject("currentPage", currentPage);
        modelView.addObject("supplierMap", supplierMap);
        return modelView;
    }
    
    @RequestMapping("search")
    public ModelAndView search(@RequestParam(value="sName") String name,
            @RequestParam(value="currentPage") int currentPage,
            @RequestParam(value="isDisabled") String isDisabled) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/goods/list");
        
        // 获取分页数据
        Pagination<Goods> paginationGoods = goodsDao.findByNameAndIsDisabled(name, isDisabled, currentPage);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(paginationGoods.getResultList());
        
        modelView.addObject("paginationGoods", paginationGoods);
        modelView.addObject("sName", name);
        modelView.addObject("isDisabled", isDisabled);
        modelView.addObject("currentPage", currentPage);
        modelView.addObject("supplierMap", supplierMap);
        
        return modelView;
    }
    
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(@ModelAttribute Goods goods) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        goods.setInsertDt(new Timestamp(System.currentTimeMillis()));
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        
        if (goodsDao.save(goods)) {
            logger.info("save goods success return true");
            result = true;
        }
        logger.info("goods save " + result);
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("update")
    @ResponseBody
    public JSONObject update(@ModelAttribute Goods goods) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (goodsDao.update(goods)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("delete")
    @ResponseBody
    public JSONObject delete(@ModelAttribute("gId") Long gId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("delete goods by Id:" + gId);
        if (goodsDao.delete(gId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping("getGoodsName")
    @ResponseBody
    public JSONObject getGoodsName() throws JSONException {
        
        List<Map<String, Object>> goods = goodsDao.findSuggestAll();

        JSONObject jsonObject;
        
        JSONArray jsonArray = new JSONArray();
        Map<String, Object> goodsTmp;
        for (int i = 0; i < goods.size(); i++) {
            jsonObject = new JSONObject();
            goodsTmp = goods.get(i);
            jsonObject.put("gId", String.valueOf(goodsTmp.get("gid")));
            jsonObject.put("gname", String.valueOf(goodsTmp.get("gname")));
            jsonObject.put("sname", String.valueOf(goodsTmp.get("sname")));
            jsonArray.add(jsonObject);
        }
        
        logger.info("GetSupplierSuggest size :" + jsonArray.size());
        
        jsonObject = new JSONObject();
        jsonObject.put("value", jsonArray);

        return jsonObject;
    }
    
    private Map<Long, String> getSupplierMap(List<Goods> goods) {
        try {
            Set<Long> sIds = new HashSet<Long>();
            if (goods!=null) {
                for (int i = 0; i < goods.size(); i++) {
                    sIds.add(goods.get(i).getsId());
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
