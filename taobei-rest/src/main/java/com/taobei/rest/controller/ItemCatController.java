package com.taobei.rest.controller;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.utils.JsonUtils;
import com.taobei.rest.pojo.ItemCatResult;
import com.taobei.rest.service.ItemCatService;

@RequestMapping("/item/cat")
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
//	方法一
//	@RequestMapping(value="/list",produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public String getItemCatList(String callback) throws Exception{
//		ItemCatResult result = itemCatService.getItemCatList();
//		if(StringUtils.isBlank(callback)){
//			//需要把result转为字符串
//			String json = JsonUtils.objectToJson(result);
//			return json;
//		}
//		String json = JsonUtils.objectToJson(result);
//		return callback + "("+json+");";
//	}
	
	
//	方法二 使用spring
	@RequestMapping(value="/list")
	@ResponseBody
	public Object getItemCatList(String callback) throws Exception{
		ItemCatResult result = itemCatService.getItemCatList();
		if(StringUtils.isBlank(callback)){
			//需要把result转为字符串
			return result;
		}
		//如果字符串不为空，需要支持jsonp
		MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}
