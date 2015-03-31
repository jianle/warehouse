package com.wms.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        List<Goods> goods = paginationGoods.getResultList();
        logger.info(goods.toString());
        Set<Long> sIds = new HashSet<Long>();
        if (goods!=null) {
            for (int i = 0; i < goods.size(); i++) {
                sIds.add(goods.get(i).getsId());
            }
        }
        // 获取sid对应的name
        Map<Long, String> supplierMap = supplierDao.findBySIdList(sIds);    

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
        List<Goods> goods = paginationGoods.getResultList();
        Set<Long> sIds = new HashSet<Long>();
        if (goods!=null) {
            for (int i = 0; i < goods.size(); i++) {
                sIds.add(goods.get(i).getsId());
            }
        }
        // 获取sid对应的name
        Map<Long, String> supplierMap = supplierDao.findBySIdList(sIds);    
        
        modelView.addObject("paginationGoods", paginationGoods);
        modelView.addObject("sName", name);
        modelView.addObject("isDisabled", isDisabled);
        modelView.addObject("currentPage", currentPage);
        modelView.addObject("supplierMap", supplierMap);
        
        return modelView;
    }
    
    @RequestMapping("save")
    @ResponseBody
    public String save(@ModelAttribute Goods goods) {
        if (goodsDao.save(goods)) {
            return "true";
        }
        return "false";
    }
    
    @RequestMapping("update")
    @ResponseBody
    public String update(@ModelAttribute Goods goods) {
        if (goodsDao.update(goods)) {
            return "true";
        }
        return "false";
    }
    
    @RequestMapping("delete")
    @ResponseBody
    public String delete(@ModelAttribute Long gId) {
        if (goodsDao.delete(gId)) {
            return "true";
        }
        return "false";
    }

}
