package com.wms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.util.UtilsDes;
import com.wms.dao.UserDao;
import com.wms.model.User;


@Controller
@RequestMapping("/users")
public class UserController {
    
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    public static Map<Integer, String> roleMaps = new HashMap<Integer, String>() ;
    static {
        roleMaps.put(User.ROLE_ADMIN, "管理员");
        roleMaps.put(User.ROLE_BOSS, "老 板");
        roleMaps.put(User.ROLE_DIRECTOR, "主 管");
        roleMaps.put(User.ROLE_NORMAL, "员 工");
        roleMaps.put(User.ROLE_GUEST, "访 客");
    }
    
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = { "", "search" })
    public ModelAndView search(
            HttpServletRequest request,
            @RequestParam(value = "truename", defaultValue = "") String truename) {
        
        ModelAndView modelAndView = new ModelAndView("/users/view");
        List<User> users = new ArrayList<User>();
        
        logger.info("@RequestMapping: /users");
        if (truename == null || "".equals(truename)) {
            users = userDao.findAll();
        } else {
            users = userDao.findByTruename(truename.trim());
        }
        
        modelAndView.addObject("users", users);
        modelAndView.addObject("truename", truename);
        modelAndView.addObject("roleMaps", roleMaps);
        
        return modelAndView;
    }
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("user") User user) {
        
        logger.info(user.toString());
        
        String pwd;
        try {
            pwd = UtilsDes.encrypt(user.getPassword());
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
        
        user.setPassword(pwd);
        user.setCreated(new Date());
        
        logger.info(user.toString());
        
        if (userDao.save(user)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
            @ModelAttribute("user") User user) {
        
        String pwd = null;
        try {
            pwd = UtilsDes.encrypt(user.getPassword());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
        
        if(user.getId() == user.getParentId()) {
            return "false";
        }
        
        user.setPassword(pwd);
        logger.info(user.toString());
        
        if (userDao.update(user)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, 
            @ModelAttribute("id") Long id) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("id :" + id);
        
        if (userDao.delete(id)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    @RequestMapping(value="getInfo")
    @ResponseBody
    public JSONObject getInfo(HttpServletRequest request, 
            @ModelAttribute("id") Long id) {
        JSONObject jsonTuple = new JSONObject();
        
        logger.info("id :" + id);
        
        jsonTuple = JSONObject.fromObject(userDao.get(id));
        
        return jsonTuple;
    }
    
    @RequestMapping("gettree")
    @ResponseBody
    public JSONArray gettree() {
        List<User> users = userDao.findByParentId((long) 0);
        JSONArray jsonArray = new JSONArray();
        
        jsonArray = toJsonArrayByParentId(users);
        
        return jsonArray;
    }
    
    
    private JSONArray toJsonArrayByParentId(List<User> users) {
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObjectTuple = null;
        for (User user : users) {
            
            jsonObjectTuple = new JSONObject();
            jsonObjectTuple.put("id", user.getId());
            jsonObjectTuple.put("text", user.getTruename());
            List<User> usersTuple = userDao.findByParentId(user.getId());
            if (usersTuple != null && usersTuple.size() > 0) {
                jsonObjectTuple.put("children", toJsonArrayByParentId(usersTuple));
            }
            jsonArray.add(jsonObjectTuple);
        }
        return jsonArray;
    }

}
