package com.wms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.wms.dao.GoodsDao;
import com.wms.dao.SupplierDao;
import com.wms.dao.UserDao;
import com.wms.model.Goods;
import com.wms.model.Pagination;
import com.wms.model.Supplier;
import com.wms.model.User;


@Controller
@RequestMapping("/goods")
@SessionAttributes({"user", "userIds"})
public class GoodsController {
    
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);
    
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private UserDao userDao;
    
    @RequestMapping(value={"","search"}, method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute User user,
            HttpServletRequest request) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/goods/list");
        String name="";
        String isDisabled = "A";
        int currentPage = 1;
        int numPerPage = 10;
        
        String userIds = (String) request.getSession().getAttribute("userIds");
        Map<Long, String> users = userDao.findAllMapIdAndName((long) -1);
        
        // 获取分页数据
        Pagination<Goods> paginationGoods = goodsDao.findByNameAndIsDisabled(name, isDisabled, currentPage, numPerPage, userIds);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(paginationGoods.getResultList());   

        modelView.addObject("paginationGoods", paginationGoods);
        modelView.addObject("sName", name);
        modelView.addObject("isDisabled", isDisabled);
        modelView.addObject("currentPage", currentPage);
        modelView.addObject("supplierMap", supplierMap);
        modelView.addObject("users", users);
        return modelView;
    }
    
    @RequestMapping(value="search", method = RequestMethod.POST)
    public ModelAndView search(@RequestParam(value="sName") String name,
            @RequestParam(value="currentPage") int currentPage,
            @RequestParam(value="numPerPage") int numPerPage,
            @ModelAttribute User user,
            HttpServletRequest request,
            @RequestParam(value="isDisabled") String isDisabled) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/goods/list");
        
        String userIds = (String) request.getSession().getAttribute("userIds");
        Map<Long, String> users = userDao.findAllMapIdAndName((long) -1);
        
        logger.info("goods search userids:" + userIds);
        // 获取分页数据
        Pagination<Goods> paginationGoods = goodsDao.findByNameAndIsDisabled(name, isDisabled, currentPage, numPerPage, userIds);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(paginationGoods.getResultList());
        
        modelView.addObject("paginationGoods", paginationGoods);
        modelView.addObject("sName", name);
        modelView.addObject("isDisabled", isDisabled);
        modelView.addObject("currentPage", currentPage);
        modelView.addObject("supplierMap", supplierMap);
        modelView.addObject("users", users);
        
        return modelView;
    }
    
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(@ModelAttribute User user,
            @ModelAttribute Goods goods) {
        Supplier supplier = supplierDao.get(goods.getsId());
        
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        goods.setInsertDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        goods.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        goods.setUserId(supplier.getUserId());
        goods.setOperatorId(user.getId());
        
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
    public JSONObject getGoodsName(HttpServletRequest request) throws JSONException {
        
        String userIds = (String) request.getSession().getAttribute("userIds");
        List<Map<String, Object>> goods = goodsDao.findSuggestAll(userIds);

        JSONObject jsonObject;
        
        JSONArray jsonArray = new JSONArray();
        Map<String, Object> goodsTmp;
        for (int i = 0; i < goods.size(); i++) {
            jsonObject = new JSONObject();
            goodsTmp = goods.get(i);
            jsonObject.put("gId", String.valueOf(goodsTmp.get("gid")));
            jsonObject.put("sId", String.valueOf(goodsTmp.get("sid")));
            jsonObject.put("gname", String.valueOf(goodsTmp.get("gname")));
            jsonObject.put("sname", String.valueOf(goodsTmp.get("sname")));
            jsonObject.put("boxes", String.valueOf(goodsTmp.get("boxes")));
            jsonObject.put("amount", String.valueOf(goodsTmp.get("amount")));
            jsonArray.add(jsonObject);
        }
        
        logger.info("GetSupplierSuggest size :" + jsonArray.size());
        
        jsonObject = new JSONObject();
        jsonObject.put("value", jsonArray);

        return jsonObject;
    }
    
    @RequestMapping("getGoods")
    @ResponseBody
    public JSONObject getGoods(@RequestParam(value="g_id", defaultValue="0") Long gId){
        
        JSONObject result = new JSONObject();
        Goods goods = goodsDao.get(gId);
        
        result = JSONObject.fromObject(goods);
        logger.info("RequestMapping:goods/getGoods?gId=" + gId);
        return result;
    }
    
    @RequestMapping("getname")
    @ResponseBody
    public List<Map<String, Object>> findAllIdAndName(
            HttpServletRequest request,
            @RequestParam(value="sId", defaultValue="0") Long sId) throws JSONException {
        String userIds = (String) request.getSession().getAttribute("userIds");
        return goodsDao.findAllIdAndName(sId, userIds);
    }
    
    @RequestMapping("getNameJson")
    @ResponseBody
    public JSONObject getNameJson(
            HttpServletRequest request,
            @RequestParam(value="sId", defaultValue="0") Long sId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String userIds = (String) request.getSession().getAttribute("userIds");
        
        List<Map<String, Object>> goods = goodsDao.findAllIdAndName(sId, userIds);
        jsonObject.put("value", goods);
        return jsonObject;
    }
    
    @RequestMapping("getIdMapName")
    @ResponseBody
    public JSONArray getIdMapName(
            HttpServletRequest request,
            @RequestParam(value="sId", defaultValue="0") Long sId) throws JSONException {
        String userIds = (String) request.getSession().getAttribute("userIds");
        
        JSONArray jsonObject = new JSONArray();
        List<Map<String, Object>> goods = goodsDao.findAllIdAndName(sId, userIds);
        jsonObject = JSONArray.fromObject(goods);
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
