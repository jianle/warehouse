package com.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/config")
public class ConfigController {
	
	@RequestMapping
    public ModelAndView view(){
		ModelAndView modelView = new ModelAndView("/config/view");
		return modelView;
	}
	
}
