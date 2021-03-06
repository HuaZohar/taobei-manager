package com.taobei.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.ExceptionUtil;
import com.taobei.search.pojo.SearchResult;
import com.taobei.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/q")
	@ResponseBody
	public TaobeiResult search(
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="30")Integer rows){
		try {
			keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
			SearchResult result = searchService.search(keyword, page, rows);
			return TaobeiResult.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}		
	}
}
