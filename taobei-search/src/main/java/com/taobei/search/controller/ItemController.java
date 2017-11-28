package com.taobei.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.ExceptionUtil;
import com.taobei.search.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/importall")
	@ResponseBody
	public TaobeiResult importAll(){
		try {
			TaobeiResult result = itemService.importItems();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
}
