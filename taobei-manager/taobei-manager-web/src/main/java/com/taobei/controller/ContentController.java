package com.taobei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.pojo.TbContent;
import com.taobei.service.ContentService;

@RequestMapping("/content")
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long categoryId,int page,int rows)
			throws Exception{
		return contentService.getContentList(categoryId, page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaobeiResult insertContent(TbContent content) 
			throws Exception{
		return contentService.insertContent(content);
	}
	
	
}
