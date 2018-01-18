package com.taobei.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

	@RequestMapping("/login")
	public String showLogin(String redirectURL,Model model){
		model.addAttribute("redirect", redirectURL);
		return "login";
	}
	
	@RequestMapping("/register")
	public String showRegister(){
		return "register";
	}
}
