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
import com.wms.model.User;


@Controller
@RequestMapping("/goods")
@SessionAttributes("user")
public class GoodsController {
    
    private Logger logger = LoggerFactory.getLogger(GoodsController.class);
    
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private UserDao userDao;
    
    @RequestMapping(value={"","search"}, method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute User user) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/goods/list");
        String name="";
        String isDisabled = "A";
        int currentPage = 1;
        int numPerPage = 10;
        String userIds = "(" + user.getId() + ")";
        // 获取分页数据
        Pagination<Goods> paginationGoods = goodsDao.findByNameAndIsDisabled(name, isDisabled, currentPage, numPerPage, userIds);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(paginationGoods.getResultList());   

        modelView.addObject("paginationGoods", paginationGoods);
        modelView.addObject("sName", name);
        modelView.addObject("isDisabled", isDisabled);
        modelView.addObject("currentPage", currentPage);
        modelView.addObject("supplierMap", supplierMap);
        return modelView;
    }
    
    @RequestMapping(value="search", method = RequestMethod.POST)
    public ModelAndView search(@RequestParam(value="sName") String name,
            @RequestParam(value="currentPage") int currentPage,
            @RequestParam(value="numPerPage") int numPerPage,
            @ModelAttribute User user,
            @RequestParam(value="isDisabled") String isDisabled) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/goods/list");
        
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        String usersIds = null;
        if (user.getRole() == User.ROLE_ADMIN || user.getRole() == User.ROLE_BOSS ) {
            usersIds = "";
        } else {
            usersIds = users.keySet().toString().replace("[", "(").replace("]", ")");
        }
        
        logger.info("goods search userids:" + usersIds);
        // 获取分页数据
        Pagination<Goods> paginationGoods = goodsDao.findByNameAndIsDisabled(name, isDisabled, currentPage, numPerPage, usersIds);
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
    public JSONObject save(@ModelAttribute User user,
            @ModelAttribute Goods goods) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        goods.setInsertDt(new Timestamp(System.currentTimeMillis()));
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goods.setUserId(user.getId());
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
    public JSONObject getGoodsName(@ModelAttribute User user) throws JSONException {
        
        List<Map<String, Object>> goods = goodsDao.findSuggestAll(user);

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
    
    @RequestMapping("getname")
    @ResponseBody
    public List<Map<String, Object>> findAllIdAndName(
            @ModelAttribute User user,
            @RequestParam(value="sId", defaultValue="0") Long sId) throws JSONException {
        return goodsDao.findAllIdAndName(sId, user);
    }
    
    @RequestMapping("getNameJson")
    @ResponseBody
    public JSONObject getNameJson(
            @ModelAttribute User user,
            @RequestParam(value="sId", defaultValue="0") Long sId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        
        List<Map<String, Object>> goods = goodsDao.findAllIdAndName(sId, user);
        jsonObject.put("value", goods);
        return jsonObject;
    }
    
    @RequestMapping("getIdMapName")
    @ResponseBody
    public JSONArray getIdMapName(
            @ModelAttribute User user,
            @RequestParam(value="sId", defaultValue="0") Long sId) throws JSONException {
        JSONArray jsonObject = new JSONArray();
        
        List<Map<String, Object>> goods = goodsDao.findAllIdAndName(sId, user);
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
