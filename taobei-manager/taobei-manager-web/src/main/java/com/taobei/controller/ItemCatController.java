package com.taobei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.EasyUITreeNode;
import com.taobei.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@ResponseBody
	@RequestMapping("/list")
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0") long parentId) throws Exception{
		return itemCatService.getItemCatList(parentId);
	}
}
