package com.wms.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.wms.dao.EnterDao;
import com.wms.dao.StorageDao;
import com.wms.dao.SupplierDao;
import com.wms.model.Pagination;
import com.wms.model.Storage;

@Controller
@RequestMapping("/storage")
public class StorageController {
    
    private Logger logger = LoggerFactory.getLogger(StorageController.class);
    
    @Autowired
    private EnterDao enterDao;
    
    @Autowired
    private StorageDao storageDao;
    
    @Autowired
    private SupplierDao supplierDao;
    
    @RequestMapping(value={"","list"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/storage/list");
        String gName = "";
        int currentPage = 1;
        int numPerPage = 10;
        
        // 获取分页数据
        Pagination<Storage> pagination = storageDao.findByCurrentPage(gName, currentPage, numPerPage);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(pagination.getResultList());
        
        modelView.addObject("supplierMap", supplierMap);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("gName", gName);
        
        
        logger.info("RequestMapping :/storage/list");
        
        return modelView;
    }
    
    @RequestMapping("list")
    public ModelAndView list(@RequestParam(value="gName", defaultValue="") String gName,
            @RequestParam(value="currentPage", defaultValue="1") int currentPage,
            @RequestParam(value="numPerPage", defaultValue="10") int numPerPage
            ) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/storage/list");
        
        logger.info("RequestMapping :/storage/list");
        // 获取分页数据
        Pagination<Storage> pagination = storageDao.findByCurrentPage(gName, currentPage, numPerPage);
        // 获取sid对应的name
        Map<Long, String> supplierMap = getSupplierMap(pagination.getResultList());
        
        modelView.addObject("supplierMap", supplierMap);
        
        modelView.addObject("pagination", pagination);
        modelView.addObject("gName", gName);
        
        return modelView;
        
    }
    
    @RequestMapping("update")
    @ResponseBody
    public JSONObject update(@ModelAttribute Storage storage) {
        logger.info(storage.toString());
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        if (storageDao.update(storage)) {
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
        
        logger.info("delete enter by Id:" + gId);
        if (storageDao.delete(gId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
    
    private Map<Long, String> getSupplierMap(List<Storage> storage) {
        try {
            Set<Long> sIds = new HashSet<Long>();
            if (storage!=null) {
                for (int i = 0; i < storage.size(); i++) {
                    sIds.add(storage.get(i).getsId());
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
