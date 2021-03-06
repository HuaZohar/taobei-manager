package com.taobei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.pojo.TbItem;
import com.taobei.service.ItemService;

/**
 * 商品查询
 * @author Administrator
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	
	@ResponseBody
	@RequestMapping("/item/{itemId}")
	private TbItem getItemById(@PathVariable Long itemId) throws Exception {
		return itemService.getItemById(itemId);
	}
	
	@ResponseBody
	@RequestMapping("/item/list")
	public EasyUIDataGridResult getItemList(int page,int rows) throws Exception {
		return itemService.getItemList(page, rows);
	}
	
	
	@RequestMapping("/item/save")
	@ResponseBody
	public TaobeiResult createItem(TbItem tbItem,String desc,String itemParams) throws Exception{
		return itemService.CreateItem(tbItem, desc, itemParams);
	}
	
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable Long itemId,Model model) throws Exception{
		String shtml = itemService.getItemParamHtml(itemId);
		model.addAttribute("shtml", shtml);
		
		return "itemParam";
	}
	
	
}
