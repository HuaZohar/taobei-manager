package com.taobei.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.ExceptionUtil;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemDesc;
import com.taobei.pojo.TbItemParamItem;
import com.taobei.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/base/{itemId}")
	@ResponseBody
	public TaobeiResult getItemById(@PathVariable Long itemId){
		try {
			TbItem tbItem = itemService.getItemById(itemId);
			return TaobeiResult.ok(tbItem);
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaobeiResult getItemDescById(@PathVariable Long itemId){
		try {
			TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
			return TaobeiResult.ok(tbItemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaobeiResult getItemParamById(@PathVariable Long itemId){
		try {
			TbItemParamItem itemParamItem = itemService.getItemParamById(itemId);
			return TaobeiResult.ok(itemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
}
