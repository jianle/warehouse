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

import com.finance.dao.ConsumerDao;
import com.finance.model.Consumer;

@Controller
@RequestMapping("/consumer")
public class ConsumerController {
	
	private Logger logger = LoggerFactory.getLogger(ConsumerController.class);
	
	@Autowired
	private ConsumerDao consumerDao;

	@RequestMapping(value = { "", "search" })
	public ModelAndView search(
			@RequestParam(value = "conName", defaultValue = "") String conName) {
		
		ModelAndView modelAndView = new ModelAndView("/consumer/view");
		List<Consumer> consumers = new ArrayList<Consumer>();

		logger.info("@RequestMapping: /consumer");
		if (conName == null || "".equals(conName)) {
			consumers = consumerDao.findAll();
		} else {
			consumers = consumerDao.findByName(conName.trim());
		}
		modelAndView.addObject("consumers", consumers);
		modelAndView.addObject("conName", conName);

		return modelAndView;
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request, 
            @ModelAttribute("consumer") Consumer consumer) {
        
        logger.info(consumer.toString());
        
        if (consumerDao.save(consumer)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, 
    		@ModelAttribute("consumer") Consumer consumer) {
        
        logger.info(consumer.toString());
        
        if (consumerDao.update(consumer)) {
            return "true";
        }
        
        return "false";
    }
    
    @RequestMapping(value="delete", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, 
            @ModelAttribute("conId") Long conId) {
        JSONObject jsonTuple = new JSONObject();
        boolean result = false;
        
        logger.info("conId :" + conId);
        
        if (consumerDao.delete(conId)) {
            result = true;
        }
        jsonTuple.put("value", result);
        return jsonTuple;
    }
	

}
