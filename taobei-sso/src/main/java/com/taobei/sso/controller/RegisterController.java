package com.taobei.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.ExceptionUtil;
import com.taobei.pojo.TbUser;
import com.taobei.sso.service.RegisterService;

@Controller
@RequestMapping("/user")
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback) throws Exception{
		
		try {
			TaobeiResult result = registerService.checkData(param, type);
			if(!StringUtils.isEmpty(callback)){
				//请求为jsonp调用，需要支持
				MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
				jacksonValue.setJsonpFunction(callback);
				return jacksonValue;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	@RequestMapping("/register")
	@ResponseBody
	public TaobeiResult register(TbUser user){
		try {
			TaobeiResult result = registerService.register(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
