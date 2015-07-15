package com.wms.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.util.UserDenied;
import com.wms.dao.SupplierDao;
import com.wms.dao.UserDao;
import com.wms.form.model.SupplierSearchForm;
import com.wms.model.Pagination;
import com.wms.model.Supplier;
import com.wms.model.User;

@Controller
@RequestMapping("/supplier")
@SessionAttributes({"user", "userIds"})
public class SupplierController {
    
    private Logger logger = LoggerFactory.getLogger(SupplierController.class);
    
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private UserDao userDao;
    
    @RequestMapping(value={"","search"},method=RequestMethod.GET)
    public ModelAndView list(@ModelAttribute User user, HttpServletRequest request) {
        
        logger.info(user.toString());
        
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        String userIds = UserDenied.getUserIds(users, user.getRole());
        
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/supplier/list");
        SupplierSearchForm supplierSearchForm = new SupplierSearchForm(10, 1, "A", "shortname", "");
        Pagination<Supplier> pagination = supplierDao.findByColumnValue(supplierSearchForm, userIds);
        
        logger.info("RequestMapping:/supplier .");
        modelView.addObject("pagination", pagination);
        modelView.addObject("supplierSearchForm", supplierSearchForm);
        modelView.addObject("users", users);
        
        return modelView;
    }
    
    @RequestMapping(value="search", method=RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request,
            @ModelAttribute User user,
            @ModelAttribute("supplierSearchForm") SupplierSearchForm supplierSearchForm) {
        //搜索控制器
        
        ModelAndView modelView = new ModelAndView();
        logger.info(supplierSearchForm.toString());
        
        Map<Long, String> users = userDao.findDeniedMapIdAndName(user);
        String userIds = UserDenied.getUserIds(users, user.getRole());
        
        Pagination<Supplier> pagination = supplierDao.findByColumnValue(supplierSearchForm, userIds);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("supplierSearchForm", supplierSearchForm);
        modelView.setViewName("/supplier/list");
        modelView.addObject("users", users);
        
        logger.info(pagination.toString());
        
        return modelView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute User user,
            @ModelAttribute("supplier") Supplier supplier) {
        
        supplier.setInsertDt(new Timestamp(System.currentTimeMillis()));
        supplier.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        supplier.setUserId(user.getId());
        
        logger.info(supplier.toString());
        
        if (supplierDao.save(supplier)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("supplier") Supplier supplier) {
        
        logger.info(supplier.toString());
        
        if (supplierDao.update(supplier)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request, 
            @ModelAttribute("sId") Long sId) {
        
        logger.info("sId :" + sId);
        
        if (supplierDao.delete(sId)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping("getSupplierName")
    @ResponseBody
    public JSONObject getSupplierName(HttpServletRequest request) throws JSONException {
        
        String userIds = (String) request.getSession().getAttribute("userIds");
        List<Supplier> suppliers = supplierDao.findSuggestAll(userIds);

        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        Supplier supplier;
        for (int i = 0; i < suppliers.size(); i++) {
            jsonObject = new JSONObject();
            supplier = suppliers.get(i);
            jsonObject.put("sId", String.valueOf(supplier.getsId()));
            jsonObject.put("name", supplier.getName() == null ? "" : supplier.getName());
            jsonObject.put("shortname", supplier.getShortname()==null ? "" : supplier.getShortname());
            jsonArray.add(jsonObject);
        }
        
        logger.info("GetSupplierSuggest size :" + jsonArray.size());
        
        jsonObject = new JSONObject();
        jsonObject.put("value", jsonArray);

        return jsonObject;
    }
    
    @RequestMapping("getIdMapName")
    @ResponseBody
    public JSONArray getIdMapName(HttpServletRequest request) {
        
        String userIds = (String) request.getSession().getAttribute("userIds");
        
        List<Map<String, Object>> suppliers = supplierDao.findIdListMapName(userIds);
        logger.info("getIdMapName:" + suppliers.toString());
        
        return JSONArray.fromObject(suppliers);
    }
    
    @RequestMapping("getname")
    @ResponseBody
    public List<Map<String, Object>> findAllIdAndName() throws JSONException {
        return supplierDao.findAllName();
    }

}
