package com.taobei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.service.ItemParamService;

@Controller
@RequestMapping("/item/param/")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("list")
	@ResponseBody
	public EasyUIDataGridResult getItemParamList(int page,int rows) throws Exception{
		EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
	
	@RequestMapping("query/itemcatid/{cid}")
	@ResponseBody
	public TaobeiResult getItemParamByCid(@PathVariable Long cid) throws Exception{
		TaobeiResult result = itemParamService.getItemParamByCid(cid);
		return result;
	}
	
	@RequestMapping("save/{cid}")
	@ResponseBody
	public TaobeiResult insertItemParam(@PathVariable Long cid,String paramData) throws Exception{
		TaobeiResult result = itemParamService.insertItemParam(cid, paramData);
		return result;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public TaobeiResult deleteItemParam(String ids) throws NumberFormatException, Exception{
		TaobeiResult result = new TaobeiResult();
		if(ids != null && !"".equals(ids)){
			for(String id :ids.split(",")){
				result = itemParamService.deleteItemParam(Long.parseLong(id));
			}
		}
		return result;
	}
	
}
