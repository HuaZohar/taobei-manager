package com.taobei.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.portal.service.ContentService;

/**
 * 首页访问
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model){
		
		String json = contentService.getAd1List();
		
		model.addAttribute("ad1", json);
		
		return "index";
	}
	
	
	
	
	
	
	@RequestMapping(value="/posttest.html",method=RequestMethod.POST)
	@ResponseBody
	public String postTest(String name,String pass){
		System.out.println(name);
		System.out.println(pass);
		return "ok";
	}
}
