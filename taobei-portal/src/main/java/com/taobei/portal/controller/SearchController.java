package com.taobei.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.portal.pojo.SearchResult;
import com.taobei.portal.service.SearchService;

/**
 * 实现商品查询
 * @author Administrator
 *
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String keyword,
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="60")Integer rows,
			Model model) 
					throws Exception{
		
		keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
		
		SearchResult searchResult = searchService.search(keyword, page, rows);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getSearchItems());
		model.addAttribute("page", searchResult.getCurPage());
		
		return "search";
		
	}
}
