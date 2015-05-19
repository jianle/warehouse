package com.finance.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.finance.dao.IncomepaymentDao;
import com.finance.model.Incomepayment;


@Controller
@RequestMapping("incomepayment")
public class IncomepaymentController {
	
	private Logger logger = LoggerFactory.getLogger(IncomepaymentController.class);
	
	@Autowired
	private IncomepaymentDao incomepaymentDao;
	
	@RequestMapping("")
	public ModelAndView view() {
		ModelAndView modelView = new ModelAndView("/incomepayment/view");
		logger.info("@requestMapping: /incomepayment/view");
		
		List<Map<String, Object>> incomepaymentIds = incomepaymentDao.findAllIds();
		Map<Integer, String> incomePaymentMap = incomepaymentDao.findAllMapIdAndType();
		
		modelView.addObject("incomepaymentIds", incomepaymentIds);
		modelView.addObject("incomePaymentMap", incomePaymentMap);
		
		return modelView;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public JSONObject save(@ModelAttribute("incomepayment") Incomepayment incomepayment) {
		JSONObject jsonObject = new JSONObject();
		logger.info(incomepayment.toString());
		Boolean result = false;
		if (incomepaymentDao.save(incomepayment)) {
			result = true;
		}
		logger.info("@requestMapping: /incomepayment/save -- " + result);
		jsonObject.put("value", result);
		return jsonObject;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public JSONObject delete(@ModelAttribute("incpayid") Integer incpayid) {
		JSONObject jsonObject = new JSONObject();
		logger.info("incpayid:" + incpayid);
		Boolean result = false;
		if (incomepaymentDao.delete(incpayid)) {
			result = true;
		}
		logger.info("@requestMapping: /incomepayment/delete -- " + result);
		jsonObject.put("value", result);
		return jsonObject;
	}
	
	@RequestMapping("getByParentId")
	@ResponseBody
	public JSONArray getIncomepayment(@ModelAttribute("parentId") Integer parentId) {
		
		List<Incomepayment> incomepayments = incomepaymentDao.findByParentId(parentId);
		JSONArray jsonArray = JSONArray.fromObject(incomepayments);
		logger.info(jsonArray.toString());
		
		return jsonArray;
	}
	

}
