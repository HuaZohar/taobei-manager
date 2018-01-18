package com.taobei.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.ExceptionUtil;
import com.taobei.sso.service.LoginService;

@Controller
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public TaobeiResult login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		try {
			TaobeiResult result = loginService.login(username, password, request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback){
		try {
			TaobeiResult result = loginService.getUserByToken(token);
			if(callback!=null&&!"".equals(callback)){
				//支持jsonp
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
}
