package com.finance.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.finance.dao.ProducerDao;
import com.finance.model.Producer;

@Controller
@RequestMapping("/producer")
public class ProducerController {

	private Logger logger = LoggerFactory.getLogger(ProducerController.class);

	@Autowired
	private ProducerDao producerDao;

	@RequestMapping(value = { "", "search" })
	public ModelAndView search(
			@RequestParam(value = "proName", defaultValue = "") String proName) {
		
		ModelAndView modelAndView = new ModelAndView("/producer/view");
		List<Producer> producers = new ArrayList<Producer>();

		logger.info("@RequestMapping: /producer");
		if (proName == null || "".equals(proName)) {
			producers = producerDao.findAll();
		} else {
			producers = producerDao.findByName(proName.trim());
		}
		
		modelAndView.addObject("producers", producers);
		modelAndView.addObject("proName", proName);
		
		return modelAndView;
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("producer") Producer producer) {
        
        logger.info(producer.toString());
        
        if (producerDao.save(producer)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
    		@ModelAttribute("producer") Producer producer) {
        
        logger.info(producer.toString());
        
        if (producerDao.update(producer)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, 
            @ModelAttribute("proId") Long proId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("proId :" + proId);
        
        if (producerDao.delete(proId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }

}
