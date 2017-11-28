package com.taobei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.EasyUITreeNode;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.service.ContentCatgoryService;

@RequestMapping("/content/category")
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCatgoryService contentCatgoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0") Long parentId) 
			throws Exception{
		return contentCatgoryService.getContentCatList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaobeiResult insertCategory(Long parentId,String name) throws Exception{
		TaobeiResult result = contentCatgoryService.insertCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaobeiResult updateCategory(Long id,String name) throws Exception{
		TaobeiResult result = contentCatgoryService.updateCategory(id, name);
		return result;
	}
	
	
	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public TaobeiResult deleteCategory(Long id) throws Exception{
		return contentCatgoryService.deleteCategory(id);
	}
	
	
}
